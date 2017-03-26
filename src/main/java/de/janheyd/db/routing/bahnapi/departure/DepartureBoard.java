package de.janheyd.db.routing.bahnapi.departure;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DepartureBoard {

	@JsonProperty("Departure")
	public List<Departure> departures;

	public DepartureBoard() {
	}

	public DepartureBoard(List<Departure> departures) {
		this.departures = departures;
	}

	public List<Departure> getDepartures() {
		return departures;
	}
}
