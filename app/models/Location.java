package models;

import java.util.Date;

public class Location {
	private Float x = 0.0f;
	private Float y = 0.0f;
	private Boolean isStart = false;
	private Long timestamp = 0l;
	private Date serverDate = null;
	private User user = null;
	
	public Location(){
		
	}
	
	public Location(Float _x, Float _y, Boolean _isStart, Long _timestamp) {
		x = _x;
		y = _y;
		isStart = _isStart;
		timestamp = _timestamp;
		serverDate = new Date();
	}

	public Float getX() {
		return x;
	}
	public void setX(Float x) {
		this.x = x;
	}
	public Float getY() {
		return y;
	}
	public void setY(Float y) {
		this.y = y;
	}
	public Boolean getIsStart() {
		return isStart;
	}
	public void setIsStart(Boolean isStart) {
		this.isStart = isStart;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public Date getServerDate() {
		return serverDate;
	}

	public void setServerDate(Date serverDate) {
		this.serverDate = serverDate;
	}
}
