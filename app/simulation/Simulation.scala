package simulation
import models.Location
import scala.collection.JavaConversions._
import play.api.libs.ws.WS
import play.mvc.Http.Request
import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import play.libs.Akka
import play.api.Logger

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
      "isStart" -> Seq("" + location.getIsStart))
    WS.url(url).post(body)

  }

  def receive = {
    case locationPost: LocationPost => {

      locationPost.locations.foreach(location => sendLocation(location, locationPost.url))

    }
    case _ => Logger.error("Unknow message")
  }

}

case class LocationPost(locations: List[Location], url: String)

object Simulation {

  val simulationActor = Akka.system.actorOf(Props[Simulation], name = "simulationActor")

  def startSimulation(userId: String, request: Request): Boolean = {

    def sendLocations(locations: List[Location]): Boolean = {

      simulationActor ! new LocationPost(locations, controllers.routes.LocationController.addLocation.absoluteURL(request))
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