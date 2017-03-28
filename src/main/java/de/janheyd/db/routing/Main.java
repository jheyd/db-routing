package de.janheyd.db.routing;

import de.janheyd.db.routing.bahnapi.BahnApi;
import de.janheyd.db.routing.search.Route;
import de.janheyd.db.routing.search.TravelSearch;

import java.time.LocalDate;
import java.util.Optional;

public class Main {

	public static void main(String... args) {
		Optional<Route> result = new TravelSearch(new BahnApi()).findRoute("Oldenburg", "Karlsruhe", LocalDate.of(2017, 1, 1));
		result.ifPresent(route -> {
			route.getStops().forEach(stop -> {
				System.out.println(stop.getArrival() + " -> " + stop.getLocation() + " -> " + stop.getDeparture());
			});
		});
	}
}
