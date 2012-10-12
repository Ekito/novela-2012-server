package vo;

import java.util.List;
import java.util.ArrayList;

import models.Location;

public class Track {

	private List<Location> locations;
	private Boolean inBounds;
	
	public Track() {
		this.locations = new ArrayList<Location>();
		this.setInBounds(false);
	}

	public void addLocation(Location location) {
		this.locations.add(location);
		this.setFirstIsStart();
	}

	public void removeLocation(int i) {
		this.locations.remove(i);
		if (i == 0) {
			this.setFirstIsStart();
		}
	}

	private void setFirstIsStart() {
		if (this.locations.isEmpty()) {
			this.locations.get(0).setStart(true);
		}
	}
	
	public List<Location> getLocations() {
		return this.locations;
	}

	public Boolean isInBounds() {
		return inBounds;
	}

	public void setInBounds(Boolean inBounds) {
		this.inBounds = inBounds;
	}
}