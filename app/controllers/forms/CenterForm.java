package controllers.forms;

import play.data.validation.Constraints.Required;

public class CenterForm {
	
	@Required
	public Double lat;
	
	@Required
	public Double lon;
	
	@Required
	public String userId;
	
	@Required
	public Integer zoom;
}
