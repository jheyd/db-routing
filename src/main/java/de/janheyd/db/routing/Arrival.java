package de.janheyd.db.routing;

import java.util.List;

public class Arrival {
	public List<Stop> stops;

	public Arrival(List<Stop> stops) {
		this.stops = stops;
	}

	public Stop getStop(Location location) {
		return stops.stream().filter(stop -> stop.getLocation().equals(location)).findAny().get();
	}
}
