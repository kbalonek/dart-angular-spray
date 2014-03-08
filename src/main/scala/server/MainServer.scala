package server

import spray.http.MediaTypes.{ `application/json` }
import spray.json._
import spray.json.DefaultJsonProtocol._
import spray.routing.Route

import models.{Recipe, Task}
import spray.http.HttpHeaders.RawHeader

trait MainServer extends WebService {

  implicit val taskJsonFormat = jsonFormat3(Task)
  implicit val recipeJsonFormat = jsonFormat7(Recipe)

  val tasks: List[Task] = List(
    new Task(1, "developers", "improve the internet"),
    new Task(2, "Downton Abbey", "keep me addicted to TV"),
    new Task(3, "Linus Torvalds", "secure the future")
  )

  val recipes: List[Recipe] = List(
    new Recipe("123", "Name1", "Category1", List("egg", "butter"), "Mix butter with egg.", 4, "url1")
  )

  val tasksAsJsonString: String = tasks.toJson.toString
  val recipesAsJsonString: String = recipes.toJson.toString

  val indexPageRoute: Route = pathPrefix("") { getFromDirectory("src/main/webapp2") }

  val taskRestRoutes = {
    pathPrefix("api" / "v1") {
      path("tasks") {
        get {
          respondWithMediaType(`application/json`) {
            complete(tasksAsJsonString)
          }
        }
      } ~
      path("recipes") {
        get {
          respondWithMediaType(`application/json`) {
            respondWithHeaders(
              RawHeader("Access-Control-Allow-Origin", "*, "),
              RawHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS"),
              RawHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept")
            ){
              complete(recipesAsJsonString)
            }
          }
        }
      }
    }
  }

  val mainRoutes: Route = indexPageRoute ~ taskRestRoutes
  
}