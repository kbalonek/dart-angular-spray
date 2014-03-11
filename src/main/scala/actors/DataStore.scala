package actors

import akka.actor.Actor
import models.{Category, Recipe}
import akka.event.{LoggingReceive, Logging}
import com.mongodb.casbah.Imports._

object DataStore {
  case class Persist(recipe: Recipe)
  case object LoadRecipes
  case object LoadCategories
}

class DataStore extends Actor {

  val dbName = "recipesapp"
  val recipesCollName = "recipes"
  val categoriesCollName = "categories"

  lazy val mongoClient = MongoClient("localhost", 27017)

  import DataStore.{Persist, LoadRecipes, LoadCategories}

  val log = Logging(context.system, this)

  val recipesColl = mongoClient(dbName)(recipesCollName)
  val categoriesColl =  mongoClient(dbName)(categoriesCollName)

  def receive: Receive = LoggingReceive {
    case LoadRecipes => {

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
    case LoadCategories => {
      // load all objects from db and convert them to Category dtos
      val result = for (obj <- categoriesColl) yield convert(obj)
        sender ! result
    }
    case _ => log.info("received unknown message")
  }
  def convert(obj: DBObject) = {
    Category(
      obj.getAs[String]("id").get,
      obj.getAs[String]("name").get
    )
  }
}
