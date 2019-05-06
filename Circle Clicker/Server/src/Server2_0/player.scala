package Server2_0

import play.api.libs.json.Json

class player (var x: Double, var y: Double, var pid: String ) {

  var speed: Double = 5

  var score: Double = 0

  def convToJson(): String = {
    val dataMap: Map[String, String] = Map(
      "pid" -> pid,
      "posx" -> x.toString,
      "posy" -> y.toString,
      "score" -> score.toString
    )
    Json.stringify(Json.toJson(dataMap))
  }

  def move(directionX: Double, directionY: Double): Unit = {
    this.x += directionX * speed
    this.y += directionY * speed
  }
}
