package de.janheyd.db.routing.bahnapi.departure;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.janheyd.db.routing.bahnapi.location.Location;
import de.janheyd.db.routing.bahnapi.common.Stop;

import java.util.List;

public class Departure {

	@JsonProperty("name")
	public String trainName;
	public List<Stop> stops;

	public Departure() {
	}

	public Departure(List<Stop> stops) {
		this.stops = stops;
	}

	public String getTrainName() {
		return trainName;
	}

	public boolean willReach(Location location) {
		return stops.stream().anyMatch(stop -> stop.getLocation().equals(location));
	}

	public Stop getStop(Location location) {
		return stops.stream().filter(stop -> stop.getLocation().equals(location)).findAny().get();
	}
}
