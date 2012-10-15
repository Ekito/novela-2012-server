package util

import scala.collection.JavaConversions._
import models._
import play.api.Logger

object LocationFilter {
  def filterNearLocations(locations: java.util.List[Location], delta: Double): java.util.List[Location] = {

    def tooCloseLocations(l1: Location, l2: Location): Boolean = {
      l2.getLat() - l1.getLat() >= delta || l2.getLon() - l2.getLon() >= delta
    }

    def filterNearLocationsRec(loc: List[Location], result: List[Location]): List[Location] = {
      if (loc.isEmpty) result
      else if (!result.isEmpty && tooCloseLocations(loc.head, result.head)) filterNearLocationsRec(loc.tail, result)
      else filterNearLocationsRec(loc.tail, loc.head::result)
    }

    filterNearLocationsRec(locations.toList, List())
  }
}