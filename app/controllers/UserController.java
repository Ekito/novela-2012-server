package controllers;

import java.util.HashMap;
import java.util.Map;

import models.User;
import play.*;
import play.mvc.*;

public class UserController extends Controller {

	protected static Map<String,User> users = new HashMap<String,User>();
	
	public static void addUser(User u){
		users.put(u.getId(), u);
	}
	
	public static User findUser(String id){
		User user = users.get(id);
		return user;
	}
}
