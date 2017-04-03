package de.janheyd.db.routing.bahnapi.journeydetail;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Stops {
	@JsonProperty("Stop")
	public List<Stop> stops;
}
