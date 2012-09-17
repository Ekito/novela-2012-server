package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
  
  public static Result index() {
    return ok(index.render("Your new  is ready."));
  }
  
  public static Result get(Integer i, String s) {
	    return ok(""+i);
	  }
  
  public static Result about() {
	    return TODO;
	  }
}