package controllers.forms;

import play.data.validation.Constraints.Required;

public class LocationForm {

	@Required
	public Float lat;

	@Required
	public Float lon;

	@Required
	public Boolean isStart;

	@Required
	public String userId;
}
