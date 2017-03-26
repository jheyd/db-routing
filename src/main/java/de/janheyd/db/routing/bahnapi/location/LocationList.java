package de.janheyd.db.routing.bahnapi.location;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class LocationList {

	@JsonProperty("StopLocation")
	public List<Location> stopLocation;

	public LocationList() {
	}

	public LocationList(List<Location> locations) {
		this.stopLocation = locations;
	}

	public Location getFirstMatch() {
		return stopLocation.get(0);
	}
}
