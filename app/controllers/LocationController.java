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

	public static Result center(String id) {
		Logger.info("ask centering for id : " + id);
		if (id != null) {
			User u = UserController.findUser(id);
			if (u == null) {
				Logger.warn("user id not found : " + id + " creating it ... ");
				u = new User(id);
				UserController.addUser(u);
			}
			// TODO send center position message
			Logger.warn("implement send message for center");
			return ok();
		} else {
			return badRequest();
		}
	}

	public static Result addLocation() {
		Form<LocationForm> form = form(LocationForm.class);
		Form<LocationForm> bindFromRequest = form.bindFromRequest();
		boolean hasErrors = bindFromRequest.hasErrors();
		if (hasErrors) {
			Logger.error("addLocation errors : " + bindFromRequest.errors());
			// TODO add details about failures
			return badRequest();
		} else {
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
