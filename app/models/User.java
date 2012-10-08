package models;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * @author Ekito
 * 
 */
@Embeddable
public class User {

	protected static Map<String, User> users = new HashMap<String, User>();

	@Column(name = "user_id", length = 100, nullable = false)
	private String id;

	public User() {
		this.id = "";
	}

	public User(final String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

}
