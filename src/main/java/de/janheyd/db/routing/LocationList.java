package de.janheyd.db.routing;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class LocationList {

	@JsonProperty("StopLocation")
	public List<Location> stopLocation;

	public Location getFirstMatch() {
		return stopLocation.get(0);
	}
}
