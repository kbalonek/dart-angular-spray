package models

case class Recipe (
  id: String,
  name:String,
  category:String,
  ingredients:List[String],
  directions:String,
  rating: Int,
  imgUrl: String)