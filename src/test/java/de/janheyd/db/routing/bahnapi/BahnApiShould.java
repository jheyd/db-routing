package de.janheyd.db.routing.bahnapi;

import de.janheyd.db.routing.bahnapi.arrival.Arrival;
import de.janheyd.db.routing.bahnapi.arrival.ArrivalBoard;
import de.janheyd.db.routing.bahnapi.departure.Departure;
import de.janheyd.db.routing.bahnapi.journeydetail.Stop;
import de.janheyd.db.routing.bahnapi.location.Location;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/*
 * TODO: decouple tests from live API, replace with mock using locally stored data
 * This was an initial decision to be able to write an API wrapper and get familiar with the live API at the same time.
 * Since familiarity with the live API is no longer an issue but reliance on the live API turns out to be one,
 * this should be changed.
 */
public class BahnApiShould {

	public static final String JOURNEY_DETAIL_URL = "https://open-api.bahn.de/bin/rest.exe/v1.0/journeyDetail?" +
		"ref=798102%2F271193%2F122122%2F204973%2F80%3F" +
		"date%3D2017-01-01%26" +
		"station_evaId%3D8000291%26" +
		"station_type%3Ddep%26" +
		"authKey%3DDBhackFrankfurt0316%26" +
		"lang%3Den" +
		"%26format%3Djson%26";
	public static final Location OLDENBURG = new Location(8000291, "Oldenburg(Oldb)");
	public static final Location KARLSRUHE = new Location(8000191, "Karlsruhe Hbf");
	public static final Location HANNOVER = new Location(8000152, "Hannover Hbf");

	BahnApi bahnApi = new BahnApi();

	@Test
	public void findKarlsruhe() throws Exception {

		Location location = bahnApi.findLocationByName("Karlsruhe").getFirstMatch();

		assertThat(location, is(KARLSRUHE));
	}

	@Test
	public void findOldenburg() throws Exception {

		Location location = bahnApi.findLocationByName("Oldenburg").getFirstMatch();

		assertThat(location, is(OLDENBURG));
	}

	@Test
	public void get20OldenburgDeparturesWithCorrectTrainNames() throws Exception {

		List<Departure> departures = bahnApi.getDepartures(OLDENBURG, LocalDate.of(2017, 1, 1), LocalTime.of(5, 0));

		assertThat(getTrainNames(departures), contains(
			"IC 2035", "IC 2436", "ICE 535", "IC 2037", "IC 2434",
			"ICE 537", "IC 2039", "IC 2432", "IC 2431", "IC 2430",
			"IC 2433", "IC 2038", "IC 2435", "IC 2036", "IC 2437",
			"IC 2034", "IC 1934", "IC 2439", "IC 2032", "ICE 841"));
	}

	@Test
	public void get1KarlsruheArrivalWithCorrectTrainName() throws Exception {

		ArrivalBoard arrivalBoard = bahnApi.getArrivalSchedule(KARLSRUHE,
			LocalDate.of(2017, 1, 1), LocalTime.of(5, 0));

		assertThat(getTrainNames(arrivalBoard).get(0), is("EN 471"));
	}

	@Ignore
	@Test
	public void resolveJourneyDetailRefWithCorrectFirstStopName() throws Exception {
		JourneyDetailRef journeyDetailRef = new JourneyDetailRef(JOURNEY_DETAIL_URL);

		List<Stop> stops = bahnApi.getStops(journeyDetailRef);

		assertThat(stops.get(0).getLocation(), is(HANNOVER));
	}

	@Ignore
	@Test
	public void resolveJourneyDetailRefFromDepartureWithCorrectFirstStopName() throws Exception {
		Departure departure = new Departure(new JourneyDetailRef(JOURNEY_DETAIL_URL));

		List<Stop> stops = bahnApi.getStops(departure);

		assertThat(stops.get(0).getLocation(), is(HANNOVER));
	}

	@Ignore
	@Test
	public void resolveJourneyDetailRefFromArrivalWithCorrectFirstStopName() throws Exception {
		Arrival arrival = new Arrival(new JourneyDetailRef(JOURNEY_DETAIL_URL));

		List<Stop> stops = bahnApi.getStops(arrival);

		assertThat(stops.get(0).getLocation(), is(HANNOVER));
	}

	@Test
	public void getJourneyDetailRef() throws Exception {

		List<Departure> departures = bahnApi.getDepartures(HANNOVER,
			LocalDate.of(2017, 1, 1), LocalTime.of(5, 0));

		assertThat(departures.get(0).getJourneyDetailRef(), is(notNullValue()));
	}

	private List<String> getTrainNames(ArrivalBoard arrivalBoard) {
		return arrivalBoard.getArrivals().stream().map(Arrival::getTrainName).collect(toList());
	}

	private List<String> getTrainNames(List<Departure> departures) {
		return departures.stream().map(Departure::getTrainName).collect(toList());
	}

}

