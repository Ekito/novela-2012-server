package controllers.forms;

import play.data.validation.Constraints.Required;

public class LocationForm {
	
	@Required
	public Float x;
	
	@Required
	public Float y;
	
	@Required
	public Boolean isStart;
	
	@Required
	public Long timestamp;
	
	@Required
	public String userId;
}
