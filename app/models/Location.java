package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import play.Logger;

public class Location implements Serializable {

	protected static Map<String, List<Location>> locations = new HashMap<String, List<Location>>();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1973445298197201545L;

	private Boolean isStart = false;
	private Date serverDate = null;
	private User user = null;

	protected Float lat = 0.0f;

	protected Float lon = 0.0f;

	public Location() {

	}

	public Location(final Float _x, final Float _y, final Boolean _isStart) {
		lat = _x;
		lon = _y;
		isStart = _isStart;
		serverDate = new Date();
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

	/**
	 * tells if the location is contained inside the bounds
	 * 
	 * @param minLat
	 * @param maxLat
	 * @param minLon
	 * @param maxLon
	 * @return
	 */
	protected static boolean isContainedInArea(Location loc, Float minLat, Float maxLat, Float minLon,
			Float maxLon) {
		Logger.info(loc.lat+" ? "+minLat+" "+maxLat+" :: "+loc.lon+" "+minLon+" "+maxLon);
		return (loc.lat >= minLat) && (loc.lat <= maxLat) && (loc.lon >= minLon)
				&& (loc.lon <= maxLon);
	}

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

	public static List<Location> getBoundedLocations(Float minLat,
			Float maxLat, Float minLon, Float maxLon) {
		List<Location> list = new ArrayList<Location>();
		for (String userId : locations.keySet()) {
			List<Location> locationsPerUser = locations.get(userId);
			Logger.info("getBoundedLocations for "+userId+" :: "+locationsPerUser.size());
			for (Location l : locationsPerUser) {
				if (Location.isContainedInArea(l, minLat, maxLat, minLon, maxLon)) {
					list.add(l);
				}
			}
		}
		return list;
	}

}
