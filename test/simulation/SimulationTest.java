package simulation;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import models.Location;

import org.codehaus.jackson.JsonNode;
import org.junit.Test;

public class SimulationTest {

	@Test
	public void testLoadDataset() throws Exception {

		JsonNode json = Dataset.loadDataset();
		assertThat(json).isNotNull();

	}

	@Test
	public void testFindLocations() throws Exception {

		List<Location> locations = Dataset.findLocations("0");
		assertThat(locations).isNotNull();
		assertThat(locations).isNotEmpty();

	}
}
