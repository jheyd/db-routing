package de.janheyd.db.routing.bahnapi.arrival;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ArrivalBoardResponse {

	@JsonProperty("ArrivalBoard")
	public ArrivalBoard arrivalBoard;
}
