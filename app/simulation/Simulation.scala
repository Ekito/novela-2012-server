package simulation
import scala.collection.JavaConversions._

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.ReceiveTimeout
import akka.routing.RoundRobinRouter
import models.Location
import play.api.libs.ws.WS
import play.api.Logger
import play.libs.Akka
import play.mvc.Http.Request

/**
 * Yeah, let's write a Scala actor !
 */

class Simulation extends Actor {

  def sendLocation(location: Location, url: String) = {

    // wait a little between two POST requests
    Thread.sleep(500)

    val body = Map(
      "userId" -> Seq(location.getUser.getId),
      "lat" -> Seq("" + location.getLat),
      "lon" -> Seq("" + location.getLon),
      "isStart" -> Seq("" + location.isStart))
    WS.url(url).post(body)

  }

  override def preStart() = {
    Logger.debug("Initializing a new simulation actor")
  }

  def receive = {
    case locationPost: LocationPost => {
      locationPost.locations.foreach(location => sendLocation(location, locationPost.url))

    }
  }

}

case class LocationPost(locations: List[Location], url: String)

object Simulation {

  val simulationActorSystem = Akka.system.actorOf(Props[Simulation].withRouter(new RoundRobinRouter(3)), name = "simulation")

  def startSimulation(userId: String, request: Request): Boolean = {

    def sendLocations(locations: List[Location]): Boolean = {

      simulationActorSystem ! new LocationPost(locations, controllers.routes.LocationController.addLocation.absoluteURL(request))
      true
    }

    val locations = Dataset.findLocations(userId)
    if (locations == null) {
      false
    } else {
      sendLocations(locations.toList)

    }

  }

}