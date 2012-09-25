package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import play.Logger;

public class Location implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1973445298197201545L;

	private Float lat = 0.0f;
	private Float lon = 0.0f;
	private Boolean isStart = false;
	private Date serverDate = null;
	private User user = null;

	public Location() {

	}

	public Location(final Float _x, final Float _y, final Boolean _isStart) {
		lat = _x;
		lon = _y;
		isStart = _isStart;
		serverDate = new Date();
	}

	public Float getLat() {
		return lat;
	}

	public void setLat(final Float x) {
		this.lat = x;
	}

	public Float getLon() {
		return lon;
	}

	public void setLon(final Float y) {
		this.lon = y;
	}

	public Boolean getIsStart() {
		return isStart;
	}

	public void setIsStart(final Boolean isStart) {
		this.isStart = isStart;
	}

	public User getUser() {
		return user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public Date getServerDate() {
		return serverDate;
	}

	public void setServerDate(final Date serverDate) {
		this.serverDate = serverDate;
	}

	protected static Map<String, List<Location>> locations = new HashMap<String, List<Location>>();

	public static void saveLocation(final String userId, final Location l) {
		boolean writeList = false;

		Logger.info("saveLocation for id " + userId + " lat:" + l.getLat()
				+ " lon:" + l.getLon());

		List<Location> list = locations.get(userId);
		if (list == null) {
			Logger.info("saveLocation new location list for " + userId);
			list = new ArrayList<Location>();
			writeList = true;
		}
		list.add(l);
		if (writeList) {
			locations.put(userId, list);
		}
	}
}
