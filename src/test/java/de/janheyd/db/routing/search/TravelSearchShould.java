package de.janheyd.db.routing.search;

import de.janheyd.db.routing.bahnapi.BahnApi;
import de.janheyd.db.routing.bahnapi.arrival.Arrival;
import de.janheyd.db.routing.bahnapi.arrival.ArrivalBoard;
import de.janheyd.db.routing.bahnapi.common.Stop;
import de.janheyd.db.routing.bahnapi.departure.Departure;
import de.janheyd.db.routing.bahnapi.departure.DepartureBoard;
import de.janheyd.db.routing.bahnapi.location.Location;
import de.janheyd.db.routing.bahnapi.location.LocationList;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TravelSearchShould {

	public static final LocalDate DATE = LocalDate.of(2017, 1, 1);
	public static final Location OLDENBURG = new Location("008000291", "Oldenburg(Oldb)");
	public static final Location KARLSRUHE = new Location("008000191", "Karlsruhe Hbf");
	private static final Location BERLIN = new Location("123456789", "Berlin Hbf");

	BahnApi bahnApi = mock(BahnApi.class);
	TravelSearch travelSearch = new TravelSearch(bahnApi);

	@Before
	public void setUp() throws Exception {
		when(bahnApi.findLocationByName("Oldenburg")).thenReturn(new LocationList(asList(OLDENBURG)));
		when(bahnApi.findLocationByName("Karlsruhe")).thenReturn(new LocationList(asList(KARLSRUHE)));
	}

	@Test
	public void findDirectRoute() throws Exception {
		when(bahnApi.getDepartureSchedule(OLDENBURG, DATE, LocalTime.of(5, 0))).thenReturn(new DepartureBoard(asList(
				new Departure(asList(createStop(OLDENBURG, 4, 6), createStop(KARLSRUHE, 7, 8)))
		)));

		List<Stop> stops = travelSearch.findRoute("Oldenburg", "Karlsruhe", LocalDate.of(2017, 1, 1)).get().getStops();

		assertThat(stops.get(0).getLocation(), is(OLDENBURG));
		assertThat(stops.get(stops.size() - 1).getLocation(), is(KARLSRUHE));
	}

	@Test
	public void findRouteWithOneChange() throws Exception {
		when(bahnApi.getDepartureSchedule(OLDENBURG, DATE, LocalTime.of(5, 0))).thenReturn(new DepartureBoard(asList(
				new Departure(asList(createStop(OLDENBURG, 4, 6), createStop(BERLIN, 7, 8)))
		)));
		Arrival arrival = new Arrival();
		when(bahnApi.getArrivalSchedule(KARLSRUHE, DATE, LocalTime.of(5, 0))).thenReturn(new ArrivalBoard(asList(
				arrival
		)));
		when(bahnApi.getStops(arrival)).thenReturn(asList(createStop(BERLIN, 9, 10), createStop(KARLSRUHE, 11, 12)));

		List<Stop> stops = travelSearch.findRoute("Oldenburg", "Karlsruhe", LocalDate.of(2017, 1, 1)).get().getStops();

		assertThat(stops.get(0).getLocation(), is(OLDENBURG));
		assertThat(stops.get(stops.size() - 1).getLocation(), is(KARLSRUHE));
	}

	private Stop createStop(Location location, int arrivalHour, int departureHour) {
		LocalDateTime arrival = LocalDateTime.of(DATE, LocalTime.of(arrivalHour, 0));
		LocalDateTime departure = LocalDateTime.of(DATE, LocalTime.of(departureHour, 0));
		return new Stop(location, arrival, departure);
	}
}
