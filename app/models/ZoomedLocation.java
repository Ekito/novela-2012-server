package models;

public class ZoomedLocation extends Location {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4142885948183956529L;

	Integer zoom;

	public ZoomedLocation() {
		super();
	}

	public ZoomedLocation(final User aUser, final Double aLat,
			final Double aLon, final Boolean aIsStart, final Integer aZoom) {
		super(aUser, aLat, aLon, aIsStart);
		zoom = aZoom;
	}

	public Integer getZoom() {
		return zoom;
	}

	public void setZoom(Integer zoom) {
		this.zoom = zoom;
	}
}
