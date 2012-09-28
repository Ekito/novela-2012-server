package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import simulation.Simulation;

public class KaazingTest extends Controller {

	public static Result startSimulations() {

		for (int i = 0; i <= 8; i++) {
			Simulation.startSimulation(String.valueOf(i), request());
		}

		return ok("Simulations started...");
	}

	public static Result startSimulation(final String userId) {

		if (Simulation.startSimulation(userId, request()) == false) {
			return notFound(String.format("The user %s does not exist", userId));
		}

		return ok("Simulation started...");
	}

}