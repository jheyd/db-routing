package de.janheyd.db.routing;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

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

	@Test
	public void findDirectRoute() throws Exception {
		BahnApi bahnApi = mock(BahnApi.class);
		when(bahnApi.findLocationByName("Oldenburg")).thenReturn(new LocationList(asList(OLDENBURG)));
		when(bahnApi.findLocationByName("Karlsruhe")).thenReturn(new LocationList(asList(KARLSRUHE)));
		Departure departure = new Departure(asList(
				new Stop(OLDENBURG, LocalDateTime.of(DATE, LocalTime.of(4, 0)), LocalDateTime.of(DATE, LocalTime.of(6, 0))),
				new Stop(KARLSRUHE, LocalDateTime.of(DATE, LocalTime.of(7, 0)), LocalDateTime.of(DATE, LocalTime.of(8, 0)))
		));
		when(bahnApi.getDepartureSchedule(OLDENBURG, DATE, LocalTime.of(5, 0))).thenReturn(new DepartureBoard(asList(departure)));
		TravelSearch travelSearch = new TravelSearch(bahnApi);

		Optional<Route> route = travelSearch.findRoute("Oldenburg", "Karlsruhe", LocalDate.of(2017, 1, 1));

		List<Stop> stops = route.get().getStops();
		assertThat(stops.get(0).getLocation(), is(OLDENBURG));
		assertThat(stops.get(stops.size() - 1).getLocation(), is(KARLSRUHE));
	}

	@Test
	public void findRouteWithOneChange() throws Exception {
		BahnApi bahnApi = mock(BahnApi.class);
		when(bahnApi.findLocationByName("Oldenburg")).thenReturn(new LocationList(asList(OLDENBURG)));
		when(bahnApi.findLocationByName("Karlsruhe")).thenReturn(new LocationList(asList(KARLSRUHE)));
		Departure departure = new Departure(asList(
				new Stop(OLDENBURG, LocalDateTime.of(DATE, LocalTime.of(4, 0)), LocalDateTime.of(DATE, LocalTime.of(6, 0))),
				new Stop(BERLIN, LocalDateTime.of(DATE, LocalTime.of(7, 0)), LocalDateTime.of(DATE, LocalTime.of(8, 0)))
		));
		when(bahnApi.getDepartureSchedule(OLDENBURG, DATE, LocalTime.of(5, 0))).thenReturn(new DepartureBoard(asList(departure)));
		Arrival arrival = new Arrival(asList(
				new Stop(BERLIN, LocalDateTime.of(DATE, LocalTime.of(8, 0)), LocalDateTime.of(DATE, LocalTime.of(9, 0))),
				new Stop(KARLSRUHE, LocalDateTime.of(DATE, LocalTime.of(10, 0)), LocalDateTime.of(DATE, LocalTime.of(11, 0)))
		));
		when(bahnApi.getArrivalSchedule(KARLSRUHE, DATE, LocalTime.of(5, 0))).thenReturn(new ArrivalBoard(asList(arrival)));
		TravelSearch travelSearch = new TravelSearch(bahnApi);

		Optional<Route> route = travelSearch.findRoute("Oldenburg", "Karlsruhe", LocalDate.of(2017, 1, 1));

		List<Stop> stops = route.get().getStops();
		assertThat(stops.get(0).getLocation(), is(OLDENBURG));
		assertThat(stops.get(stops.size() - 1).getLocation(), is(KARLSRUHE));
	}
}
