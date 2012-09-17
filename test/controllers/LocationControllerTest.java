package controllers;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.GET;
import static play.test.Helpers.POST;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.routeAndCall;
import static play.test.Helpers.status;

import java.util.Date;
import java.util.HashMap;

import org.junit.Test;

import play.*;
import play.data.Form;
import play.mvc.*;

import play.test.FakeRequest;
import controllers.forms.LocationForm;

public class LocationControllerTest {

	//TODO pass this test
	
//	@Test
//	public void testAddLocation() {
//		FakeRequest fakeRequest = fakeRequest(POST, "/location");
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
	
	@Test
	public void testCenter() {
		FakeRequest fakeRequest = fakeRequest(GET, "/location/center/AN_ID");
		Result result = routeAndCall(fakeRequest);
		assertThat(status(result)).isEqualTo(OK);
	}
	
	@Test
	public void testBadCenter() {
		FakeRequest fakeRequest = fakeRequest(GET, "/location/center");
		Result result = routeAndCall(fakeRequest);
		assertThat(result).isNull();
	}
}
