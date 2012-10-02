package controllers.forms;

import play.data.validation.Constraints.Required;

public class CenterForm {
	
	@Required
	public Float lat;
	
	@Required
	public Float lon;
	
	@Required
	public String userId;
	
	@Required
	public Integer zoom;
}
