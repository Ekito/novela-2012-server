package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {

	public static Result index() {
		return redirect(routes.Application.map());
	}

	public static Result map() {
		return ok(views.html.map.render(null, false));
	}

	public static Result focusedMap(final String userId,
			final Boolean hideControls) {
		return ok(views.html.map.render(userId, hideControls));
	}

	public static Result fullscreenMap() {
		return ok(views.html.map.render("a fake id", true));
	}

	public static Result about() {
		return ok(views.html.about.render(false));
	}

	public static Result mobileAbout() {
		return ok(views.html.about.render(true));
	}
}