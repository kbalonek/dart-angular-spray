package server

import spray.http.MediaTypes.{ `application/json` }
import spray.httpx.encoding._
import spray.io.ServerSSLEngineProvider
import spray.json._
import spray.json.DefaultJsonProtocol._
import spray.routing.Route

import models.Task

trait MainServer extends WebService {

  implicit val taskJsonFormat = jsonFormat3(Task)

  val tasks: List[Task] = List(
    new Task(1, "developers", "improve the internet"),
    new Task(2, "Downton Abbey", "keep me addicted to TV"),
    new Task(3, "Linus Torvalds", "secure the future")
  )

  val tasksAsJsonString: String = tasks.toJson.toString

  val indexPageRoute: Route = pathPrefix("") { getFromDirectory("src/main/webapp") }

  val taskRestRoutes = {
    pathPrefix("api" / "v1") {
      path("tasks") {
        get {
          respondWithMediaType(`application/json`) {
            complete(tasksAsJsonString)
          }
        }
      }
    }
  }

  val mainRoutes: Route = indexPageRoute ~ taskRestRoutes
  
}