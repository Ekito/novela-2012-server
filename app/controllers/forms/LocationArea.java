package controllers.forms;

import play.data.validation.Constraints.Required;

public class LocationArea {

	public Float maxLat;
	
	public Float minLat;

	public Float minLon;
	
	public Float maxLon;
	
	public String userId;
	
	@Required
	public Integer zoom;
}
