package controllers.forms;

import play.data.validation.Constraints.Required;

public class LocationForm {

	@Required
	public Double lat;

	@Required
	public Double lon;

	@Required
	public Boolean isStart;

	@Required
	public String userId;
}
