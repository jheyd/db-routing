package de.janheyd.db.routing;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Departure {

	@JsonProperty("name")
	public String trainName;

	public String getTrainName() {
		return trainName;
	}
}
