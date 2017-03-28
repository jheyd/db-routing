package de.janheyd.db.routing.bahnapi;

import de.janheyd.db.routing.bahnapi.arrival.Arrival;
import de.janheyd.db.routing.bahnapi.arrival.ArrivalBoard;
import de.janheyd.db.routing.bahnapi.common.Stop;
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

/*
 * TODO: decouple tests from live API, replace with mock using locally stored data
 * This was an initial decision to be able to write an API wrapper and get familiar with the live API at the same time.
 * Since familiarity with the live API is no longer an issue but reliance on the live API turns out to be one,
 * this should be changed.
 */
public class BahnApiShould {

	BahnApi bahnApi = new BahnApi();

	@Test
	public void findKarlsruhe() throws Exception {

		Location location = bahnApi.findLocationByName("Karlsruhe").getFirstMatch();

		assertThat(location, is(new Location("008000191", "Karlsruhe Hbf")));
	}

	@Test
	public void findOldenburg() throws Exception {

		Location location = bahnApi.findLocationByName("Oldenburg").getFirstMatch();

		assertThat(location, is(new Location("008000291", "Oldenburg(Oldb)")));
	}

	@Test
	public void get20OldenburgDeparturesWithCorrectTrainNames() throws Exception {

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

		ArrivalBoard arrivalBoard = bahnApi.getArrivalSchedule(new Location("008000191", "Karlsruhe Hbf"),
				LocalDate.of(2017, 1, 1), LocalTime.of(5, 0));

		assertThat(getTrainNames(arrivalBoard).get(0), is("EN 471"));
	}

	@Test
	public void resolveJourneyDetailRefWithCorrectFirstStopName() throws Exception {
		JourneyDetailRef journeyDetailRef = new JourneyDetailRef();
		journeyDetailRef.url = "https://open-api.bahn.de/bin/rest.exe/v1.0/journeyDetail?" +
				"ref=798102%2F271193%2F122122%2F204973%2F80%3F" +
				"date%3D2017-01-01%26" +
				"station_evaId%3D8000291%26" +
				"station_type%3Ddep%26" +
				"authKey%3DDBhackFrankfurt0316%26" +
				"lang%3Den" +
				"%26format%3Djson%26";
		
		List<Stop> stops = bahnApi.getStops(journeyDetailRef);

		assertThat(stops.get(0).getLocation(), is(new Location("8000152", "Hannover Hbf")));
	}

	@Test
	public void resolveJourneyDetailRefFromDepartureWithCorrectFirstStopName() throws Exception {
		JourneyDetailRef journeyDetailRef = new JourneyDetailRef();
		journeyDetailRef.url = "https://open-api.bahn.de/bin/rest.exe/v1.0/journeyDetail?" +
			"ref=798102%2F271193%2F122122%2F204973%2F80%3F" +
			"date%3D2017-01-01%26" +
			"station_evaId%3D8000291%26" +
			"station_type%3Ddep%26" +
			"authKey%3DDBhackFrankfurt0316%26" +
			"lang%3Den" +
			"%26format%3Djson%26";
		Departure departure = new Departure(journeyDetailRef);

		List<Stop> stops = bahnApi.getStops(departure);

		assertThat(stops.get(0).getLocation(), is(new Location("8000152", "Hannover Hbf")));
	}

	private List<String> getTrainNames(ArrivalBoard arrivalBoard) {
		return arrivalBoard.getArrivals().stream().map(Arrival::getTrainName).collect(toList());
	}

	private List<String> getTrainNames(DepartureBoard departureBoard) {
		return departureBoard.getDepartures().stream().map(Departure::getTrainName).collect(toList());
	}

}

