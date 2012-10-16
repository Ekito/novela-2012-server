package controllers;

import java.util.List;

import models.Location;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import controllers.forms.LoginForm;

public class AdminController extends Controller {

	public static Result login() {
		if (Secured.isLogged(ctx())) {
			return redirect(routes.AdminController.dashboard());
		} else {
			return ok(views.html.login.render(form(LoginForm.class)));
		}
	}

	public static Result authenticate() {
		Form<LoginForm> loginForm = form(LoginForm.class).bindFromRequest();
		if (loginForm.hasErrors()) {
			return badRequest(views.html.login.render(loginForm));
		} else {
			session("email", loginForm.get().email);
			return redirect(routes.AdminController.dashboard());
		}
	}
	
	public static Result logout() {
		session().remove("email");
		flash("success", "You've been logged out");
		return redirect(routes.AdminController.login());
	}

	@Security.Authenticated(Secured.class)
	public static Result dashboard() {
		List<User> users = Location.getUsers();
		return ok(views.html.dashboard.render(users));
	}

	@Security.Authenticated(Secured.class)
	public static Result deleteUser(String id) {
		Location.removeLocationsForUser(id);
		return redirect(routes.AdminController.dashboard());
	}

}
