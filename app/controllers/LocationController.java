package controllers;

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
			User u = User.findOrCreateUser(centerForm.userId);
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
			Location location = extractAndSaveLocation(locationForm);

			LocationProducer locationProducer = Global
					.getBean(LocationProducer.class);

			locationProducer.publishLocation(location);

			return created();
		}
	}

	private static Location extractAndSaveLocation(
			final LocationForm locationForm) {
		Location l = new Location(locationForm.x, locationForm.y,
				locationForm.isStart, locationForm.timestamp);
		User u = User.findOrCreateUser(locationForm.userId);
		l.setUser(u);
		Location.saveLocation(locationForm.userId, l);
		return l;
	}

}
