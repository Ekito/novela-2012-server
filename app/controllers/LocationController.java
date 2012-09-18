package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import messaging.LocationProducer;
import models.Location;
import models.User;
import play.Logger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import bootstrap.Global;
import controllers.forms.CenterForm;
import controllers.forms.LocationForm;

public class LocationController extends Controller {

	protected static Map<String, List<Location>> locations = new HashMap<String, List<Location>>();

	public static Result centerMap() {
		Form<CenterForm> bindFromRequest = form(CenterForm.class)
				.bindFromRequest();
		boolean hasErrors = bindFromRequest.hasErrors();
		if (hasErrors) {
			Logger.error("centerMap errors : " + bindFromRequest.errors());
			return badRequest();
		} else {
			CenterForm centerForm = bindFromRequest.get();
			Logger.info("ask centering for id : " + centerForm.userId);
			User u = findOrCreateUser(centerForm.userId);
			// TODO send center position message
			Logger.warn("implement send message for center");
			return ok();
		}
	}

	public static Result addLocation() {
		Form<LocationForm> bindFromRequest = form(LocationForm.class)
				.bindFromRequest();
		boolean hasErrors = bindFromRequest.hasErrors();
		if (hasErrors) {
			Logger.error("addLocation errors : " + bindFromRequest.errors());
			// TODO add details about failures
			return badRequest();
		} else {
			LocationForm locationForm = bindFromRequest.get();
			extractAndSaveLocation(locationForm);

			LocationProducer locationProducer = Global
					.getBean(LocationProducer.class);

			return created();
		}
	}

	private static void extractAndSaveLocation(final LocationForm locationForm) {
		Location l = new Location(locationForm.x, locationForm.y,
				locationForm.isStart, locationForm.timestamp);
		User u = findOrCreateUser(locationForm.userId);
		l.setUser(u);
		saveLocation(locationForm.userId, l);
	}

	private static User findOrCreateUser(final String id) {
		User u = UserController.findUser(id);
		if (u == null) {
			Logger.warn("user id not found : " + id + " creating it ... ");
			u = new User(id);
			UserController.addUser(u);
		}
		return u;
	}

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
