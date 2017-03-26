package de.janheyd.db.routing;

import java.time.LocalDateTime;

public class Stop {
	private Location location;
	private final LocalDateTime arrival;
	private final LocalDateTime departure;

	public Stop(Location location, LocalDateTime arrival, LocalDateTime departure) {
		this.location = location;
		this.arrival = arrival;
		this.departure = departure;
	}

	public Location getLocation() {
		return location;
	}

	public LocalDateTime getArrival() {
		return arrival;
	}

	public LocalDateTime getDeparture() {
		return departure;
	}
}
