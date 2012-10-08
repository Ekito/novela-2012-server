package controllers;

import java.util.List;
import java.util.concurrent.Callable;

import messaging.LocationProducer;
import models.Location;
import models.User;
import play.Logger;
import play.data.Form;
import play.libs.Akka;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import bootstrap.Global;
import controllers.forms.CenterForm;
import controllers.forms.LocationArea;
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
			// TODO send center position message
			Logger.warn("implement send message for center");
			return ok();
		}
	}

	public static Result getBoundedArea() {
		Form<LocationArea> bindFromRequest = form(LocationArea.class)
				.bindFromRequest();
		if (bindFromRequest.hasErrors()) {
			Logger.error("getBoundedArea errors : " + bindFromRequest.errors());
			return badRequest();
		} else {
			List<Location> points = getBoundedLocations(bindFromRequest);
			Logger.info("getBoundedArea returned " + points.size());
			return ok(Json.toJson(points));
		}
	}

	private static List<Location> getBoundedLocations(
			final Form<LocationArea> bindFromRequest) {
		LocationArea locationArea = bindFromRequest.get();
		return Location.getBoundedLocations(locationArea.minLat,
				locationArea.maxLat, locationArea.minLon, locationArea.maxLon);
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
			final LocationForm locationForm = bindFromRequest.get();
			return async(Akka.future(new Callable<Result>() {

				@Override
				public Result call() throws Exception {

					// TODO NDE : save the location using a JMS Listener (CQRS
					// inside !!)
					Location location = extractAndSaveLocation(locationForm);

					sendLocationToConnectedClients(location);

					return created();
				}

			}));

		}
	}

	private static void sendLocationToConnectedClients(final Location location) {
		LocationProducer locationProducer = Global
				.getBean(LocationProducer.class);

		locationProducer.publishLocation(location);
	}

	private static Location extractAndSaveLocation(
			final LocationForm locationForm) {
		Location l = new Location(new User(locationForm.userId),
				locationForm.lat, locationForm.lon, locationForm.isStart);
		Location.saveLocation(l);
		return l;
	}

}
