package de.janheyd.db.routing.search;

import de.janheyd.db.routing.bahnapi.BahnApi;
import de.janheyd.db.routing.bahnapi.arrival.Arrival;
import de.janheyd.db.routing.bahnapi.common.Stop;
import de.janheyd.db.routing.bahnapi.departure.Departure;
import de.janheyd.db.routing.bahnapi.location.Location;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class TravelSearch {

	private BahnApi bahnApi;

	public TravelSearch(BahnApi bahnApi) {
		this.bahnApi = bahnApi;
	}

	public Optional<Route> findRoute(String startName, String destinationName, LocalDate date) throws IOException {
		/*
		 * TODO: remove "stops" fields on Arrival and Departure and use BahnApi.getStops instead
		 * This would enable on-demand retrieval of JourneyDetails which is highly preferable for finding
		 * routes with more than 1 change.
		 */
		Location start = bahnApi.findLocationByName(startName).getFirstMatch();
		Location destination = bahnApi.findLocationByName(destinationName).getFirstMatch();
		Optional<Route> directTrip = findDirectRoute(date, start, destination);
		if (directTrip.isPresent())
			return directTrip;
		return findOneChangeRoute(date, start, destination);
		/*
		 * TODO: route with 2 and 3 changes.
		 * The current approach (dual breadth-first search from start and destination) should cover these cases,
		 * but wont scale beyond that.
		 */
	}

	private Optional<Route> findDirectRoute(LocalDate date, Location start, Location destination) throws IOException {
		List<Departure> departures = bahnApi.getDepartureSchedule(start, date, LocalTime.of(5, 0)).getDepartures();
		return departures.stream()
				.filter(departure -> departure.stops.stream().anyMatch(stop -> stop.getLocation().equals(destination)))
				.map(departure -> {
					Stop firstStop = departure.stops.stream().filter(stop -> stop.getLocation().equals(start)).findAny().get();
					Stop lastStop = departure.stops.stream().filter(stop -> stop.getLocation().equals(destination)).findAny().get();
					return new Route(firstStop, lastStop);
				})
				.findFirst();
	}

	private Optional<Route> findOneChangeRoute(LocalDate date, Location start, Location destination) throws IOException {
		List<Departure> departures = bahnApi.getDepartureSchedule(start, date, LocalTime.of(5, 0)).getDepartures();
		List<Arrival> arrivals = bahnApi.getArrivalSchedule(destination, date, LocalTime.of(5, 0)).getArrivals();
		// TODO: refactor these loops into something nicer
		for (Departure departure : departures) {
			Stop firstStop = departure.stops.stream().filter(stop -> stop.getLocation().equals(start)).findAny().get();
			for (Stop departureStop : departure.stops) {
				for (Arrival arrival : arrivals) {
					Stop lastStop = bahnApi.getStops(arrival).stream().filter(stop -> stop.getLocation().equals(destination)).findAny().get();
					for (Stop arrivalStop : bahnApi.getStops(arrival)) {
						if (departureStop.getLocation().equals(arrivalStop.getLocation())) {
							Stop changeStop = createChangeStop(departureStop, arrivalStop);
							return Optional.of(new Route(firstStop, changeStop, lastStop));
						}
					}
				}
			}
		}
		return Optional.empty();
	}

	private Stop createChangeStop(Stop departureStop, Stop arrivalStop) {
		Location changeLocation = departureStop.getLocation();
		LocalDateTime changeArrival = departureStop.getArrival();
		LocalDateTime changeDeparture = arrivalStop.getDeparture();
		return new Stop(changeLocation, changeArrival, changeDeparture);
	}

	private List<Stop> getStops(Departure departure) {
		throw new UnsupportedOperationException();
	}
}
