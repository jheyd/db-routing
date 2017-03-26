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
		return directTrip;
	}

	private List<Stop> getStops(Departure departure) {
		throw new UnsupportedOperationException();
	}
}
