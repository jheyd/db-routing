package de.janheyd.db.routing;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ArrivalBoard {

	@JsonProperty("Arrival")
	public List<Arrival> arrivals;

	public ArrivalBoard() {
	}

	public ArrivalBoard(List<Arrival> arrivals) {
		this.arrivals = arrivals;
	}

	public List<Arrival> getArrivals() {
		return arrivals;
	}
}
