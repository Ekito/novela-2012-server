package models;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;

import java.util.List;

import org.junit.Test;

public class LocationTest {

	@Test
	public void testGetBoundedLocations() throws Exception {

		running(fakeApplication(inMemoryDatabase()), new Runnable() {
			@Override
			public void run() {

				int before = Location.getBoundedLocations(0.0F, 2.0F, 0.0F,
						2.0F).size();

				User user = new User("I'm a test user");
				Location locationIn = new Location(user, 1.0F, 2.0F, true);
				Location locationOut = new Location(user, -1.0F, 0F, false);

				Location.saveLocation(locationIn);
				Location.saveLocation(locationOut);

				List<Location> boundedLocations = Location.getBoundedLocations(
						0.0F, 2.0F, 0.0F, 2.0F);

				int after = boundedLocations.size();

				assertThat(after - before).isEqualTo(1);

			}
		});

	}

}
