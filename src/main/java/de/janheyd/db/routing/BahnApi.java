package de.janheyd.db.routing;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class BahnApi {

	public static final String API_KEY = "DBhackFrankfurt0316";
	public static final String API_BASE = "https://open-api.bahn.de/bin/rest.exe";

	private ObjectMapper objectMapper;

	public BahnApi(){
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

}
