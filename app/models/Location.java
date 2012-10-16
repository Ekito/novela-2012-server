package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;
import util.LocationFilter;
import vo.Track;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

@Entity
public class Location extends Model implements Comparable<Location> {

	private static final long serialVersionUID = 1973445298197201545L;

	@Id
	private Long id;

	@Column
	private boolean isStart = false;

	@Column(nullable = false)
	private Date serverDate = null;

	@Embedded
	private User user = null;

	@Column(nullable = false)
	protected Double lat = 0.0d;

	@Column(nullable = false)
	protected Double lon = 0.0d;

	public Location() {

		this.serverDate = new Date();
	}

	public Location(final User aUser, final Double aLat, final Double aLon,
			final Boolean aIsStart) {
		this.user = aUser;
		this.lat = aLat;
		this.lon = aLon;
		this.isStart = aIsStart;
		this.serverDate = new Date();
	}

	public boolean isStart() {
		return isStart;
	}

	public void setStart(final boolean isStart) {
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

	public Double getLat() {
		return lat;
	}

	public void setLat(final Double x) {
		this.lat = x;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(final Double y) {
		this.lon = y;
	}

	private static Finder<Long, Location> finder = new Finder<Long, Location>(
			Long.class, Location.class);

	public static void saveLocation(final Location l) {
		l.save();
	}

	public static int locationsCount() {
		return finder.findRowCount();
	}
	
	public static List<User> getUsers() {
		
		SqlQuery query = Ebean.createSqlQuery("SELECT DISTINCT user_id FROM location;");
		List<SqlRow> locations = query.findList();
		
		return Lists.transform(locations, new Function<SqlRow, User>() {

			@Override
			public User apply(@Nullable SqlRow arg0) {
				return new User(arg0.getString("user_id"));
			}
			
		});
		
	}

	public static List<Location> getBoundedLocations(final Float minLat,
			final Float maxLat, final Float minLon, final Float maxLon) {

		List<Location> result = new ArrayList<Location>();
		
		List<Location> locations = finder.where().orderBy("serverDate").findList();
 
		// results ordered by tracks
		List<Track> resultTracks = new ArrayList<Track>();
		
		// a map containing tracks mapped by user id, ie last track seen for a user
		Map<String,Track> tracks = new HashMap<String,Track>();
		
		// current track user id		
		String userId;
		
		
		
		// current track
		Track track;
		for (Location loc : locations) {
			
			userId = loc.getUser().getId();
			
			// new track
			if (loc.isStart || !tracks.containsKey(userId)) {
				
				// !tracks.containsKey(userId) -->> case if the data is not well formatted (ie not starting with a isStart) 
				
				track = new Track();
				
				// if not the first track for user, store last one if it's in the bounds
				if (tracks.containsKey(userId) && tracks.get(userId).isInBounds()) {
					resultTracks.add(tracks.remove(userId));
				}					
					
				tracks.put(userId,track);
			} else {
				track = tracks.get(userId);
			}
			
			// update the track
			track.addLocation(loc);
			
			// set is in bounds
			if (!track.isInBounds() &&
					loc.lat >= minLat && loc.lat <= maxLat &&
					loc.lon >= minLon && loc.lon <= maxLon) {
				track.setInBounds(true);
			}
		}
		
		// fill resultTracks with remaining tracks
		for (Track tck : tracks.values()) {
			if (tck.isInBounds()) {
				resultTracks.add(tck);
			}
		}
		
		// track to location translation
		for (Track tck : resultTracks) {
			result.addAll(tck.getLocations());
		}
		List<Location> finalList = new ArrayList<Location>();
		finalList.addAll(LocationFilter.filterNearLocations(result, 2.0));
		Collections.sort(finalList);
		return finalList;

	}
	
	@Override
	public String toString(){
		return "["+id+"] lat:"+lat+" lon:"+lon+" start:"+isStart;
	}

	@Override
	public int compareTo(Location o) {
		int cmp = 0;
		if (this.getServerDate().getTime() > o.getServerDate().getTime()){
			cmp = 1;
		}
		else if (this.getServerDate().getTime() < o.getServerDate().getTime()){
			cmp = -1;
		}
		return cmp;
	}
}
