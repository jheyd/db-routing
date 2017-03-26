package de.janheyd.db.routing;

import java.time.LocalDateTime;

public class Stop {
	private Location location;

	public Stop(Location location, LocalDateTime arrival, LocalDateTime departure) {
		this.location = location;
	}

	public Location getLocation() {
		return location;
	}
}
