package de.janheyd.db.routing.bahnapi.arrival;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.janheyd.db.routing.bahnapi.JourneyDetailRef;
import de.janheyd.db.routing.bahnapi.common.Stop;

import java.util.List;

public class Arrival {

	@JsonProperty("name")
	public String trainName;
	public List<Stop> stops;
	@JsonProperty("JourneyDetailRef")
	public JourneyDetailRef journeyDetailRef;

	public Arrival() {
	}

	public Arrival(List<Stop> stops) {
		this.stops = stops;
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
