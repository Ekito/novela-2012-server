package controllers.forms;

import play.data.validation.Constraints.Required;

public class CenterForm {
	
	@Required
	public Float x;
	
	@Required
	public Float y;
	
	@Required
	public String userId;
	
	@Required
	public Integer zoom;
}
