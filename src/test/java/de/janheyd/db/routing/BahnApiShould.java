package de.janheyd.db.routing;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BahnApiShould {

	@Test
	public void findKarlsruhe() throws Exception {
		BahnApi bahnApi = new BahnApi();

		Location location = bahnApi.findLocationByName("Karlsruhe").getFirstMatch();

		assertThat(location, is(new Location("008000191", "Karlsruhe Hbf")));
	}

}

