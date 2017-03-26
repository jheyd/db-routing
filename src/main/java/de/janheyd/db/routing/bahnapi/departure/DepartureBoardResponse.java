package de.janheyd.db.routing.bahnapi.departure;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DepartureBoardResponse {
	
	@JsonProperty("DepartureBoard")
	public DepartureBoard departureBoard;
}
