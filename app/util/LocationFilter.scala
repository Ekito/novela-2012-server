package util

import scala.collection.JavaConversions._
import models._
import play.api.Logger
import com.google.common.collect.SortedLists
import scala.collection.mutable.Buffer

object LocationFilter {
  def filterNearLocations(locations: java.util.List[Location], delta: Double): java.util.List[Location] = {

    def tooCloseLocations(l1: Location, l2: Location): Boolean = {
      def d = math.acos(math.sin(l1.getLat()) * math.sin(l2.getLat()) + math.cos(l1.getLat()) * math.cos(l2.getLat() * math.cos(l1.getLon() - l2.getLon()))) * 6366
      d < delta
    }
    
    def areStartingPoints(l1: Location, l2: Location) : Boolean = {
      l1.isStart || l2.isStart
    }

    def filterNearLocationsRec(loc: Buffer[Location], result: List[Location]): List[Location] = {
      if (loc.isEmpty) result
      else if (!result.isEmpty && tooCloseLocations(loc.head, result.head) && !areStartingPoints(loc.head, result.head)) {
        if (loc.head.isStart){
          println("eject => "+loc.head)
        }
        filterNearLocationsRec(loc.tail, result)
      }
      else filterNearLocationsRec(loc.tail, loc.head :: result)
    }
    filterNearLocationsRec(locations, List())
  }
}