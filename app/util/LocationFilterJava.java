package util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import models.Location;

public class LocationFilterJava {
	
	private static DecimalFormat decimalFormat;

	public static DecimalFormat getDecimalFormater(){
		if (decimalFormat == null){
			decimalFormat = new DecimalFormat("0.0000");
		}
		return decimalFormat;
	}
	
	public static Double formatDouble(double d){
		 return new Double(getDecimalFormater().format(d).replace(",", "."));
	}
	
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
		for (int i = 0; i < locations.size(); i++) {
			Location current = locations.get(i);
			if (!newLocations.isEmpty()) {
				// is last
				Location last = newLocations.get(newLocations.size() - 1);
				if (!areStartingPoints(current, last)
						&& tooCloseLocations(current, last, delta)) {
				} else {
					newLocations.add(current);
				}
			}
			else{
				newLocations.add(current);
			}
		}
		return newLocations;
	}
}
