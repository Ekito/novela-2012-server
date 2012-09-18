package models;

import java.util.HashMap;
import java.util.Map;

import play.Logger;

/**
 * 
 * @author Ekito
 * 
 */
public class User {

	private String id = "";

	public User() {

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

	protected static Map<String, User> users = new HashMap<String, User>();

	public static void addUser(final User u) {
		users.put(u.getId(), u);
	}

	public static User findUser(final String id) {
		User user = users.get(id);
		return user;
	}

	public static User findOrCreateUser(final String id) {
		User u = User.findUser(id);
		if (u == null) {
			Logger.warn("user id not found : " + id + " creating it ... ");
			u = new User(id);
			User.addUser(u);
		}
		return u;
	}
}
