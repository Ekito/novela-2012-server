package controllers;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import controllers.forms.LoginForm;

public class AdminController extends Controller {

	public static Result login() {
		return ok(views.html.login.render(form(LoginForm.class)));
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
		return ok(views.html.dashboard.render());
	}

}
