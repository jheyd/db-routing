package de.janheyd.db.routing.bahnapi.arrival;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.janheyd.db.routing.bahnapi.JourneyDetailRef;

public class Arrival {

	@JsonProperty("name")
	public String trainName;
	@JsonProperty("JourneyDetailRef")
	public JourneyDetailRef journeyDetailRef;

	public Arrival() {
	}

	public Arrival(JourneyDetailRef journeyDetailRef) {
		this.journeyDetailRef = journeyDetailRef;
	}

	public String getTrainName() {
		return trainName;
	}

	public JourneyDetailRef getJourneyDetailRef() {
		return journeyDetailRef;
	}
}
