package de.janheyd.db.routing.bahnapi.location;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationResponse {

	@JsonProperty("LocationList")
	public LocationList locationList;
}
