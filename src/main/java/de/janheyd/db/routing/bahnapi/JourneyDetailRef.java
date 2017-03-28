package de.janheyd.db.routing.bahnapi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JourneyDetailRef {
	@JsonProperty("ref")
	String url;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		JourneyDetailRef that = (JourneyDetailRef) o;

		return url != null ? url.equals(that.url) : that.url == null;
	}

	@Override
	public int hashCode() {
		return url != null ? url.hashCode() : 0;
	}
}
