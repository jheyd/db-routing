package de.janheyd.db.routing.bahnapi;

import de.janheyd.db.routing.bahnapi.arrival.Arrival;
import de.janheyd.db.routing.bahnapi.arrival.ArrivalBoard;
import de.janheyd.db.routing.bahnapi.departure.Departure;
import de.janheyd.db.routing.bahnapi.departure.DepartureBoard;
import de.janheyd.db.routing.bahnapi.location.Location;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class BahnApiShould {

	@Test
	public void findKarlsruhe() throws Exception {
		BahnApi bahnApi = new BahnApi();

		Location location = bahnApi.findLocationByName("Karlsruhe").getFirstMatch();

		assertThat(location, is(new Location("008000191", "Karlsruhe Hbf")));
	}

	@Test
	public void findOldenburg() throws Exception {
		BahnApi bahnApi = new BahnApi();

		Location location = bahnApi.findLocationByName("Oldenburg").getFirstMatch();

		assertThat(location, is(new Location("008000291", "Oldenburg(Oldb)")));
	}

	@Test
	public void get20OldenburgDeparturesWithCorrectTrainNames() throws Exception {
		BahnApi bahnApi = new BahnApi();

		DepartureBoard departureBoard = bahnApi.getDepartureSchedule(new Location("008000291", "Oldenburg(Oldb)"),
				LocalDate.of(2017, 1, 1), LocalTime.of(5, 0));

		assertThat(getTrainNames(departureBoard), contains(
				"IC 2035", "IC 2436", "ICE 535", "IC 2037", "IC 2434",
				"ICE 537", "IC 2039", "IC 2432", "IC 2431", "IC 2430",
				"IC 2433", "IC 2038", "IC 2435", "IC 2036", "IC 2437",
				"IC 2034", "IC 1934", "IC 2439", "IC 2032", "ICE 841"));
	}

	@Test
	public void get1KarlsruheArrivalWithCorrectTrainName() throws Exception {
		BahnApi bahnApi = new BahnApi();

		ArrivalBoard arrivalBoard = bahnApi.getArrivalSchedule(new Location("008000191", "Karlsruhe Hbf"),
				LocalDate.of(2017, 1, 1), LocalTime.of(5, 0));

		assertThat(getTrainNames(arrivalBoard).get(0), is("EN 471"));
	}

	private List<String> getTrainNames(ArrivalBoard arrivalBoard) {
		return arrivalBoard.getArrivals().stream().map(Arrival::getTrainName).collect(toList());
	}

	private List<String> getTrainNames(DepartureBoard departureBoard) {
		return departureBoard.getDepartures().stream().map(Departure::getTrainName).collect(toList());
	}

}

