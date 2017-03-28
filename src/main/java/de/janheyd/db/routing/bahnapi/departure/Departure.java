package de.janheyd.db.routing.bahnapi.departure;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.janheyd.db.routing.bahnapi.JourneyDetailRef;

public class Departure {

	@JsonProperty("name")
	public String trainName;
	@JsonProperty
	public JourneyDetailRef journeyDetailRef;

	public Departure() {
	}

	public Departure(JourneyDetailRef journeyDetailRef) {
		this.journeyDetailRef = journeyDetailRef;
	}

	public String getTrainName() {
		return trainName;
	}

	public JourneyDetailRef getJourneyDetailRef() {
		return journeyDetailRef;
	}
}
