package de.janheyd.db.routing;

import java.util.List;

import static java.util.Arrays.asList;

public class Route {
	private final List<Stop> stops;

	public Route(Stop... stops) {
		this.stops = asList(stops);
	}

	public List<Stop> getStops() {
		return stops;
	}
}
