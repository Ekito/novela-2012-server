import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "novela-2012-server"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "org.springframework" % "spring-context" % "3.1.2.RELEASE",
      "org.apache.activemq" % "activemq-core" % "5.6.0",
      "org.apache.xbean" % "xbean-spring" % "3.11.1"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      // Add your own project settings here      
    )

}
