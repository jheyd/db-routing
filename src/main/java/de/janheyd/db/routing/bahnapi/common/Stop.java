package de.janheyd.db.routing.bahnapi.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.janheyd.db.routing.bahnapi.location.Location;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Stop {

	@JsonProperty("id")
	public String id;
	@JsonProperty("name")
	public String name;
	@JsonProperty("arrTime")
	public String arrivalTime;
	@JsonProperty("arrDate")
	public String arrivalDate;
	@JsonProperty("depTime")
	public String departureTime;
	@JsonProperty("depDate")
	public String departureDate;

	public Stop() {
	}

	public Stop(Location location, LocalDateTime arrival, LocalDateTime departure) {
		this.id = location.getId();
		this.name = location.getName();
		this.arrivalTime = arrival.toLocalTime().toString();
		this.arrivalDate = arrival.toLocalDate().toString();
		this.departureTime = departure.toLocalTime().toString();
		this.departureDate = departure.toLocalDate().toString();
	}

	public Location getLocation() {
		return new Location(id, name);
	}

	public LocalDateTime getArrival() {
		return LocalDateTime.of(LocalDate.parse(arrivalDate), LocalTime.parse(arrivalTime));
	}

	public LocalDateTime getDeparture() {
		return LocalDateTime.of(LocalDate.parse(departureDate), LocalTime.parse(departureTime));
	}
}
