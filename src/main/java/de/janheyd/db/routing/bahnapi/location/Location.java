package de.janheyd.db.routing.bahnapi.location;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
public class Location {

	private int id;
	private String name;

	public Location(@JsonProperty("id") int id, @JsonProperty("name") String name) {
		this.id = id;
		this.name = name;
	}
}
