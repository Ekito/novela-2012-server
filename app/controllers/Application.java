package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.map;

public class Application extends Controller {

	public static Result index() {
		return redirect(routes.Application.fullScreenMap());
	}

	public static Result fullScreenMap() {
		return ok(map.render());
	}
}