package controllers.forms;

import models.Admin;
import play.data.validation.Constraints.Required;

public class LoginForm {

	@Required
	public String email;

	@Required
	public String password;

	public LoginForm() {
	}

	public String validate() {
		if (!Admin.authenticate(email, password)) {
			return "Invalid user or password";
		}
		return null;
	}
}
