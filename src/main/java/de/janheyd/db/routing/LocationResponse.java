package de.janheyd.db.routing;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationResponse {

	@JsonProperty("LocationList")
	public LocationList locationList;
}
