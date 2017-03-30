package de.janheyd.db.routing.search;

import de.janheyd.db.routing.bahnapi.BahnApi;
import de.janheyd.db.routing.bahnapi.arrival.Arrival;
import de.janheyd.db.routing.bahnapi.common.Stop;
import de.janheyd.db.routing.bahnapi.departure.Departure;
import de.janheyd.db.routing.bahnapi.location.Location;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

public class TravelSearch {

	private BahnApi bahnApi;

	public TravelSearch(BahnApi bahnApi) {
		this.bahnApi = bahnApi;
	}

	public Optional<Route> findRoute(String startName, String destinationName, LocalDate date) {
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

	private Optional<Route> findDirectRoute(LocalDate date, Location start, Location destination) {
		List<Departure> departures = bahnApi.getDepartures(start, date, LocalTime.of(5, 0));
		return departures.stream()
				.filter(departure -> containsLocation(destination, departure))
				.map(departure -> {
					Stop firstStop = getStopFor(start, departure);
					Stop lastStop = getStopFor(destination, departure);
					return new Route(firstStop, lastStop);
				})
				.findFirst();
	}

	private boolean containsLocation(Location destination, Departure departure) {
		return bahnApi.getStops(departure).stream().anyMatch(stop -> stop.getLocation().equals(destination));
	}

	private Optional<Route> findOneChangeRoute(LocalDate date, Location start, Location destination) {
		List<Departure> departures = bahnApi.getDepartures(start, date, LocalTime.of(5, 0));
		List<Arrival> arrivals = bahnApi.getArrivalSchedule(destination, date, LocalTime.of(5, 0)).getArrivals();
		// TODO: refactor these loops into something nicer
		for (Departure departure : departures) {
			Stop firstStop = getStopFor(start, departure);
			for (Stop departureStop : bahnApi.getStops(departure)) {
				for (Arrival arrival : arrivals) {
					Stop lastStop = getStopFor(destination, arrival);
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

	private Stop getStopFor(Location destination, Arrival arrival) {
		return findLocation(destination, bahnApi.getStops(arrival))
				.orElseThrow(() -> new RuntimeException(format("%s does not have Stop for %s", arrival, destination)));
	}

	private Stop getStopFor(Location start, Departure departure) {
		return findLocation(start, bahnApi.getStops(departure))
				.orElseThrow(() -> new RuntimeException(format("%s does not have Stop for %s", departure, start)));
	}

	private Optional<Stop> findLocation(Location destination, List<Stop> stops) {
		return stops.stream()
				.filter(stop -> stop.getLocation().equals(destination))
				.findAny();
	}

	private Stop createChangeStop(Stop departureStop, Stop arrivalStop) {
		Location changeLocation = departureStop.getLocation();
		LocalDateTime changeArrival = departureStop.getArrival();
		LocalDateTime changeDeparture = arrivalStop.getDeparture();
		return new Stop(changeLocation, changeArrival, changeDeparture);
	}
}
