package de.janheyd.db.routing;

import java.util.List;

public class ArrivalBoard {
	private List<Arrival> arrivals;

	public ArrivalBoard(List<Arrival> arrivals) {
		this.arrivals = arrivals;
	}

	public List<Arrival> getArrivals() {
		return arrivals;
	}
}
