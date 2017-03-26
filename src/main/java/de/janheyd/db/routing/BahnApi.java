package de.janheyd.db.routing;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;

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

	private URL buildArrivalScheduleUrl(Location location, LocalDate date, LocalTime time) throws MalformedURLException {
		return new URL(API_BASE
				+ "/arrivalBoard"
				+ "?format=json"
				+ "&authKey=" + API_KEY
				+ "&id=" + location.getId()
				+ "&date=" + date
				+ "&time=" + time);
	}

	public ArrivalBoard getArrivalSchedule(Location location, LocalDate date, LocalTime time) {
		throw new UnsupportedOperationException();
	}
}
