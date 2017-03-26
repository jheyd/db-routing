package de.janheyd.db.routing;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class TravelSearch {

	private BahnApi bahnApi;

	public TravelSearch(BahnApi bahnApi) {
		this.bahnApi = bahnApi;
	}

	public Optional<Route> findRoute(String startName, String destinationName, LocalDate date) throws IOException {
		Location start = bahnApi.findLocationByName(startName).getFirstMatch();
		Location destination = bahnApi.findLocationByName(destinationName).getFirstMatch();
		List<Departure> departures = bahnApi.getDepartureSchedule(start, date, LocalTime.of(5, 0)).getDepartures();

		Optional<Route> directTrip = departures.stream()
				.filter(departure -> departure.willReach(destination))
				.map(departure -> new Route(departure.getStop(start), departure.getStop(destination)))
				.findFirst();
		if (directTrip.isPresent())
			return directTrip;
		List<Arrival> arrivals = bahnApi.getArrivalSchedule(destination, date, LocalTime.of(5, 0)).getArrivals();
		for (Departure departure : departures) {
			for (Arrival arrival : arrivals) {
				for (Stop departureStop : departure.stops) {
					for (Stop arrivalStop : arrival.stops) {
						if (departureStop.getLocation().equals(arrivalStop.getLocation()))
							return Optional.of(new Route(
									departure.getStop(start),
									new Stop(departureStop.getLocation(), departureStop.getArrival(), arrivalStop.getDeparture()),
									arrival.getStop(destination)));
					}
				}
			}
		}
		return Optional.empty();
	}

	private List<Stop> getStops(Departure departure) {
		throw new UnsupportedOperationException();
	}
}
