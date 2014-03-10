package actors

import akka.actor.Actor
import models.Recipe
import akka.event.Logging
import com.mongodb.casbah.Imports._

object DataStore {
  case class Persist(recipe: Recipe)
  case object Load
}

class DataStore extends Actor {

  val dbName = "recipeapp"
  val recipesCollName = "recipes"
  val categoriesCollName = "categories"

  val mongoClient = MongoClient("localhost", 27017)
  val db = mongoClient(dbName)
  val recipesColl = db(recipesCollName)

  import DataStore.{Persist, Load}

  val log = Logging(context.system, this)


  def receive: Receive = {
    case Persist(recipe: Recipe) =>
      ???
    case Load =>
      // load all objects from db and convert them to Recipe dtos
      val result = for (obj <- recipesColl) yield new Recipe(
            obj.getAs[String]("id").get,
            obj.getAs[String]("name").get,
            obj.getAs[String]("category").get,
            obj.getAs[List[String]]("ingredients").get,
            obj.getAs[String]("directions").get,
            obj.getAs[Int]("rating").get,
            obj.getAs[String]("imgUrl").get
      )
      sender ! result.toList

  }
}
