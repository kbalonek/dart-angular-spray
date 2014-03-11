package server

import spray.http.MediaTypes.{ `application/json` }
import spray.json._
import spray.json.DefaultJsonProtocol._
import spray.routing.Route

import models.{Category, Recipe}
import actors.DataStore.{LoadCategories, LoadRecipes}
import spray.http.HttpHeaders.RawHeader
import akka.actor.Props
import akka.pattern.ask
import actors.DataStore

trait MainServer extends WebService {

  this: ServerSupervisor =>
    val dataStore = context.system.actorOf(Props[DataStore])

  implicit val recipeJsonFormat = jsonFormat7(Recipe)
  implicit val categoryJsonFormat = jsonFormat2(Category)

  val indexPageRoute: Route = pathPrefix("") { getFromDirectory("src/main/webapp2") }

  val corsHeaders = List(
    RawHeader("Access-Control-Allow-Origin", "*, "),
    RawHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS"),
    RawHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept"))

  val taskRestRoutes = {
    pathPrefix("api" / "v1") {
      path("categories") {
        get {
          respondWithMediaType(`application/json`) {
            respondWithHeaders(corsHeaders){
              val response = (dataStore ? LoadCategories)
                .mapTo[List[Category]]
                .map(result => result.toJson.toString)
                .recover { case e => s"error loading categories: $e" }
              complete(response)
            }
          }
        }
      } ~
      path("recipes") {
        get {
          respondWithMediaType(`application/json`) {
            respondWithHeaders(corsHeaders){
              val response = (dataStore ? LoadRecipes)
                .mapTo[List[Recipe]]
                .map(result => result.toJson.toString)
                .recover { case e => s"error loading recipes: $e" }
              complete(response)
            }
          }
        }
      }
    }
  }

  val mainRoutes: Route = indexPageRoute ~ taskRestRoutes
  
}