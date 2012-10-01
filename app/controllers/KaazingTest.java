package controllers;

import java.util.concurrent.Callable;

import play.libs.Akka;
import play.mvc.Controller;
import play.mvc.Http.Request;
import play.mvc.Result;
import simulation.Simulation;

public class KaazingTest extends Controller {

	public static Result startSimulations() {

		final Request request = request();

		// async in order to not block the main Play thread
		return async(Akka.future(new Callable<Result>() {

			@Override
			public Result call() throws Exception {
				for (int i = 0; i <= 8; i++) {
					Thread.sleep(1000);
					Simulation.startSimulation(String.valueOf(i), request);
				}

				return ok("Simulations started...");
			}
		}));

	}

	public static Result startSimulation(final String userId) {

		if (Simulation.startSimulation(userId, request()) == false) {
			return notFound(String.format("The user %s does not exist", userId));
		}

		return ok("Simulation started...");
	}

}