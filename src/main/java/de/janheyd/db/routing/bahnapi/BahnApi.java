package de.janheyd.db.routing.bahnapi;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.janheyd.db.routing.bahnapi.arrival.ArrivalBoard;
import de.janheyd.db.routing.bahnapi.arrival.ArrivalBoardResponse;
import de.janheyd.db.routing.bahnapi.common.JourneyDetailResponse;
import de.janheyd.db.routing.bahnapi.common.Stop;
import de.janheyd.db.routing.bahnapi.departure.DepartureBoard;
import de.janheyd.db.routing.bahnapi.departure.DepartureBoardResponse;
import de.janheyd.db.routing.bahnapi.location.Location;
import de.janheyd.db.routing.bahnapi.location.LocationList;
import de.janheyd.db.routing.bahnapi.location.LocationResponse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class BahnApi {

	public static final String API_KEY = "DBhackFrankfurt0316";
	public static final String API_BASE = "https://open-api.bahn.de/bin/rest.exe";

	private ObjectMapper objectMapper;

	public BahnApi() {
		objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public LocationList findLocationByName(String name) throws IOException {
		URL url = buildLocationNameUrl(name);
		return objectMapper.readValue(url, LocationResponse.class).locationList;
	}

	private URL buildLocationNameUrl(String name) throws MalformedURLException {
		return new URL(API_BASE + "/location.name?format=json"
				+ "&authKey=" + API_KEY
				+ "&input=" + name);
	}

	public DepartureBoard getDepartureSchedule(Location location, LocalDate date, LocalTime time) throws IOException {
		URL url = buildDepartureScheduleUrl(location, date, time);
		System.out.println(url);
		return objectMapper.readValue(url, DepartureBoardResponse.class).departureBoard;
	}

	private URL buildDepartureScheduleUrl(Location location, LocalDate date, LocalTime time) throws MalformedURLException {
		return new URL(API_BASE
				+ "/departureBoard"
				+ "?format=json"
				+ "&authKey=" + API_KEY
				+ "&id=" + location.getId()
				+ "&date=" + date
				+ "&time=" + time);
	}

	public ArrivalBoard getArrivalSchedule(Location location, LocalDate date, LocalTime time) throws IOException {
		URL url = buildArrivalScheduleUrl(location, date, time);
		return objectMapper.readValue(url, ArrivalBoardResponse.class).arrivalBoard;
	}

	private URL buildArrivalScheduleUrl(Location location, LocalDate date, LocalTime time) throws MalformedURLException {
		return new URL(API_BASE
				+ "/arrivalBoard"
				+ "?format=json"
				+ "&authKey=" + API_KEY
				+ "&id=" + location.getId()
				+ "&date=" + date
				+ "&time=" + time);
	}

	public List<Stop> getStops(JourneyDetailRef journeyDetailRef) throws IOException {
		URL url = buildJourneyDetailUrl(journeyDetailRef);
		return objectMapper.readValue(url, JourneyDetailResponse.class).journeyDetail.stops.stops;
	}

	private URL buildJourneyDetailUrl(JourneyDetailRef journeyDetailRef) throws MalformedURLException {
		return new URL(journeyDetailRef.url);
	}

}