package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Location;
import models.User;
import controllers.forms.LocationForm;
import play.*;
import play.data.Form;
import play.mvc.*;

import views.html.*;

public class LocationController extends Controller {

	protected static Map<String, List<Location>> locations = new HashMap<String, List<Location>>();

	public static Result addLocation() {
		Form<LocationForm> form = form(LocationForm.class);
		Form<LocationForm> bindFromRequest = form.bindFromRequest();
		boolean hasErrors = bindFromRequest.hasErrors();
		if (hasErrors){
			Logger.error("addLocation errors : "+bindFromRequest.errors());
			return badRequest();
		}
		else{
			LocationForm locationForm = bindFromRequest.get();
			extractAndSaveLocation(locationForm);
			
			return created();
		}
	}

	private static void extractAndSaveLocation(LocationForm locationForm) {
		Location l = new Location(locationForm.x, locationForm.y,
				locationForm.isStart, locationForm.timestamp);
		User u = UserController.findUser(locationForm.userId);
		if (u == null) {
			Logger.warn("user id not found : " + locationForm.userId
					+ " creating it ... ");
			u = new User(locationForm.userId);
			UserController.addUser(u);
		}
		l.setUser(u);
		saveLocation(locationForm.userId, l);
	}

	public static void saveLocation(String userId, Location l) {
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
