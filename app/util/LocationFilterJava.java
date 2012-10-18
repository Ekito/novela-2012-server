package util;

import java.util.ArrayList;
import java.util.List;

import models.Location;

public class LocationFilterJava {

	private static Boolean tooCloseLocations(Location l1, Location l2,
			Double delta) {
		Double d = Math.acos(Math.sin(l1.getLat()) * Math.sin(l2.getLat())
				+ Math.cos(l1.getLat())
				* Math.cos(l2.getLat() * Math.cos(l1.getLon() - l2.getLon()))) * 6366;
		return d < delta;
	}

	private static Boolean areStartingPoints(Location l1, Location l2) {
		return l1.isStart() || l2.isStart();
	}

	public static List<Location> filterNearLocations(List<Location> locations,
			Double delta) {
		List<Location> newLocations = new ArrayList<Location>();
		for(int i = 0; i< locations.size(); i++){
			// is last
			if (i == locations.size() - 1){
				break;
			}
			else {
				int j = i+1;
				Location current = locations.get(i);
				Location next = locations.get(j);
				if (!areStartingPoints(current, next) && tooCloseLocations(current, next, delta)){
				}
				else{
					newLocations.add(current);
				}
			}
		}
		return newLocations;
	}
}
