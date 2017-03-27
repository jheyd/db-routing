package de.janheyd.db.routing.bahnapi.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JourneyDetail {
	@JsonProperty("Stops")
	public Stops stops;
}
