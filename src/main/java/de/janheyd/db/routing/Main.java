package de.janheyd.db.routing;

import java.io.IOException;
import java.time.LocalDate;

public class Main {

	public static void main(String... args) {
		try {
			new TravelSearch(new BahnApi()).findRoute("Oldenburg", "Karlsruhe", LocalDate.of(2017, 1, 1));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
