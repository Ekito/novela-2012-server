package controllers;

import java.util.Date;
import java.util.HashMap;

import org.junit.*;

import controllers.forms.LocationForm;

import play.mvc.*;
import play.test.*;
import play.data.Form;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

public class LocationControllerTest {

//	@Test
//	public void testAddLocation() {
//		FakeRequest fakeRequest = fakeRequest(POST, "/location");
//		Form<LocationForm> form = form(LocationForm.class);
//		
//		HashMap<String, String> args = new HashMap<String, String>();
//		args.put("userId", "_USER_ID_");
//		args.put("x", ""+12.0f);
//		args.put("y", ""+12.0f);
//		args.put("isStart", ""+false);
//		args.put("timestamp", ""+(new Date()).getTime());
//		Result result = routeAndCall(fakeRequest.withFormUrlEncodedBody(args));
//		assertThat(status(result)).isEqualTo(OK);
//	}
	
	@Test
	public void testBadAddLocation() {
		FakeRequest fakeRequest = fakeRequest(POST, "/location");
		Result result = routeAndCall(fakeRequest);
		assertThat(status(result)).isEqualTo(400);
	}
}
