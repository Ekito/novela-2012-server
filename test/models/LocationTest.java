package models;

import org.junit.Test;
import org.springframework.util.Assert;

public class LocationTest {

	@Test
	public void testIsContainedInArea() {
		Location l = new Location(1.0f,1.0f,true);
		Assert.isTrue(Location.isContainedInArea(l, 1.0f, 1.0f, 1.0f, 1.0f));
		
		l = new Location(-1.0f,-1.0f,true);
		Assert.isTrue(!Location.isContainedInArea(l, 1.0f, 1.0f, 1.0f, 1.0f));
	}

}
