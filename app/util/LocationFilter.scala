package util

import scala.collection.JavaConversions._
import models._
import play.api.Logger

object LocationFilter {
  def filterNearLocations(locations: java.util.List[Location], delta: Double): java.util.List[Location] = {

    def tooCloseLocations(l1: Location, l2: Location): Boolean = {
      def d = math.acos(math.sin(l1.getLat()) * math.sin(l2.getLat()) + math.cos(l1.getLat()) * math.cos(l2.getLat() * math.cos(l1.getLon() - l2.getLon()))) * 6366
      def r = d < delta && !(l1.isStart || l2.isStart)
      if (l1.isStart || l2.isStart){
    	  println("r="+r+" l1.isStart =" + l1.isStart + " l2.isStart =" + l2.isStart)
      }
      r
    }

    def filterNearLocationsRec(loc: List[Location], result: List[Location]): List[Location] = {
      if (loc.isEmpty) result
      else if (!result.isEmpty && tooCloseLocations(loc.head, result.head)) filterNearLocationsRec(loc.tail, result)
      else filterNearLocationsRec(loc.tail, loc.head :: result)
    }

    filterNearLocationsRec(locations.toList, List())
  }
}