package de.janheyd.db.routing;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DepartureBoard {

	@JsonProperty("Departure")
	public List<Departure> departures;

	public List<Departure> getDepartures() {
		return departures;
	}
}
