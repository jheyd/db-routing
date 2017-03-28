package de.janheyd.db.routing;

import de.janheyd.db.routing.bahnapi.BahnApi;
import de.janheyd.db.routing.search.TravelSearch;

import java.io.IOException;
import java.time.LocalDate;

public class Main {

	public static void main(String... args) {
		new TravelSearch(new BahnApi()).findRoute("Oldenburg", "Karlsruhe", LocalDate.of(2017, 1, 1));
	}
}
