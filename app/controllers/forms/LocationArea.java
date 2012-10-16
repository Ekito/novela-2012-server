package controllers.forms;

import play.data.validation.Constraints.Required;

public class LocationArea {

	@Required
	public Float maxLat;
	
	@Required
	public Float minLat;

	@Required
	public Float minLon;
	
	@Required
	public Float maxLon;
	
	public String userId;
}
