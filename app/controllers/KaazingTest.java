package controllers;

import messaging.LocationProducer;
import models.Location;
import play.mvc.Controller;
import play.mvc.Result;
import bootstrap.Global;

public class KaazingTest extends Controller {

	public static Result kaazingTest() {

		return ok(views.html.kaazingTest.render());
	}

	public static Result testMessage() {

		LocationProducer locationProducer = Global
				.getBean(LocationProducer.class);

		Location location = new Location(43.601364F, 1.441976F, true);
		locationProducer.publishLocation(location);

		return ok();
	}

}