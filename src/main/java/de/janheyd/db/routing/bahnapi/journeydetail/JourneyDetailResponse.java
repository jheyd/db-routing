package de.janheyd.db.routing.bahnapi.journeydetail;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JourneyDetailResponse {
	@JsonProperty("JourneyDetail")
	public JourneyDetail journeyDetail;
}
