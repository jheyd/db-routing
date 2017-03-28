package de.janheyd.db.routing.bahnapi;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.janheyd.db.routing.bahnapi.arrival.Arrival;
import de.janheyd.db.routing.bahnapi.arrival.ArrivalBoard;
import de.janheyd.db.routing.bahnapi.arrival.ArrivalBoardResponse;
import de.janheyd.db.routing.bahnapi.common.JourneyDetail;
import de.janheyd.db.routing.bahnapi.common.JourneyDetailResponse;
import de.janheyd.db.routing.bahnapi.common.Stop;
import de.janheyd.db.routing.bahnapi.departure.Departure;
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
import java.util.HashMap;
import java.util.List;

public class BahnApi {

	// TODO: optimization (caching, multiple async requests (esp for getStops))

	private HashMap<JourneyDetailRef, JourneyDetail> journeyDetailCache = new HashMap<>();

	public static final String API_KEY = "DBhackFrankfurt0316";
	public static final String API_BASE = "https://open-api.bahn.de/bin/rest.exe";

	private ObjectMapper objectMapper;

	public BahnApi() {
		objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public LocationList findLocationByName(String name) {
		URL url = buildLocationNameUrl(name);
		return queryApi(url, LocationResponse.class).locationList;
	}

	private URL buildLocationNameUrl(String name) {
		return buildUrl(API_BASE + "/location.name?format=json"
				+ "&authKey=" + API_KEY
				+ "&input=" + name);
	}

	public List<Stop> getStops(Departure departure) {
		return getStops(departure.getJourneyDetailRef());
	}

	public List<Stop> getStops(Arrival arrival) {
		return getStops(arrival.getJourneyDetailRef());
	}

	public List<Stop> getStops(JourneyDetailRef journeyDetailRef) {
		if (!journeyDetailCache.containsKey(journeyDetailRef)) {
			URL url = buildJourneyDetailUrl(journeyDetailRef);
			JourneyDetail journeyDetail = queryApi(url, JourneyDetailResponse.class).journeyDetail;
			journeyDetailCache.put(journeyDetailRef, journeyDetail);
		}
		return journeyDetailCache.get(journeyDetailRef).stops.stops;
	}

	private URL buildJourneyDetailUrl(JourneyDetailRef journeyDetailRef) {
		return buildUrl(journeyDetailRef.url);
	}

	public DepartureBoard getDepartureSchedule(Location location, LocalDate date, LocalTime time) {
		URL url = buildDepartureScheduleUrl(location, date, time);
		return queryApi(url, DepartureBoardResponse.class).departureBoard;
	}

	private URL buildDepartureScheduleUrl(Location location, LocalDate date, LocalTime time) {
		return buildUrl(API_BASE
				+ "/departureBoard"
				+ "?format=json"
				+ "&authKey=" + API_KEY
				+ "&id=" + location.getId()
				+ "&date=" + date
				+ "&time=" + time);
	}

	public ArrivalBoard getArrivalSchedule(Location location, LocalDate date, LocalTime time) {
		URL url = buildArrivalScheduleUrl(location, date, time);
		return queryApi(url, ArrivalBoardResponse.class).arrivalBoard;
	}

	private URL buildArrivalScheduleUrl(Location location, LocalDate date, LocalTime time) {
		return buildUrl(API_BASE
				+ "/arrivalBoard"
				+ "?format=json"
				+ "&authKey=" + API_KEY
				+ "&id=" + location.getId()
				+ "&date=" + date
				+ "&time=" + time);
	}

	private URL buildUrl(String url) {
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	private <T> T queryApi(URL url, Class<T> clazz) {
		try {
			return objectMapper.readValue(url, clazz);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
