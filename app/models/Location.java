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

	private Float x = 0.0f;
	private Float y = 0.0f;
	private Boolean isStart = false;
	private Long timestamp = 0l;
	private Date serverDate = null;
	private User user = null;

	public Location() {

	}

	public Location(final Float _x, final Float _y, final Boolean _isStart,
			final Long _timestamp) {
		x = _x;
		y = _y;
		isStart = _isStart;
		timestamp = _timestamp;
		serverDate = new Date();
	}

	public Float getX() {
		return x;
	}

	public void setX(final Float x) {
		this.x = x;
	}

	public Float getY() {
		return y;
	}

	public void setY(final Float y) {
		this.y = y;
	}

	public Boolean getIsStart() {
		return isStart;
	}

	public void setIsStart(final Boolean isStart) {
		this.isStart = isStart;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(final Long timestamp) {
		this.timestamp = timestamp;
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

		Logger.info("saveLocation for id " + userId + " x:" + l.getX() + " y:"
				+ l.getY());

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
