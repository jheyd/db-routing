package de.janheyd.db.routing.bahnapi.departure;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.janheyd.db.routing.bahnapi.JourneyDetailRef;
import de.janheyd.db.routing.bahnapi.common.Stop;
import de.janheyd.db.routing.bahnapi.location.Location;

import java.util.List;

public class Departure {

	@JsonProperty("name")
	public String trainName;
	public List<Stop> stops;
	@JsonProperty
	public JourneyDetailRef journeyDetailRef;

	public Departure() {
	}

	public Departure(List<Stop> stops) {
		this.stops = stops;
	}

	public Departure(JourneyDetailRef journeyDetailRef) {
		this.journeyDetailRef = journeyDetailRef;
	}

	public String getTrainName() {
		return trainName;
	}

	public boolean willReach(Location location) {
		return stops.stream().anyMatch(stop -> stop.getLocation().equals(location));
	}

	public Stop getStop(Location location) {
		return stops.stream().filter(stop -> stop.getLocation().equals(location)).findAny().get();
	}

	public JourneyDetailRef getJourneyDetailRef() {
		return journeyDetailRef;
	}
}
