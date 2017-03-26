package de.janheyd.db.routing;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Arrival {

	@JsonProperty("name")
	public String trainName;
	public List<Stop> stops;

	public Arrival() {
	}

	public Arrival(List<Stop> stops) {
		this.stops = stops;
	}

	public Stop getStop(Location location) {
		return stops.stream().filter(stop -> stop.getLocation().equals(location)).findAny().get();
	}

	public String getTrainName() {
		return trainName;
	}
}
