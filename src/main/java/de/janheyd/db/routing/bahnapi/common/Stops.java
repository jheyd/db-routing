package de.janheyd.db.routing.bahnapi.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Stops {
	@JsonProperty("Stop")
	public List<Stop> stops;
}
