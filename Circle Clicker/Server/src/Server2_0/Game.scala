package Server2_0

import play.api.libs.json.Json

class Game {

  var players: Map[String, player] = Map()

  var lastUpdateTime: Long = System.nanoTime()

  def addPlayer(id: String): Unit = {
    val r = new scala.util.Random
    val x = r.nextInt(1000)
    val y = r.nextInt(500)
    players = players ++ Map(id -> new player(x, y, id))
  }

  def removePlayer(id: String): Unit = {
    players -= id
  }

  def clickPlayer(attacker: String, mouseX: Double, mouseY: Double): Unit = {
    for ((i, j) <- players){
      val distanceClicked: Double = Math.sqrt(Math.pow(mouseX - j.x, 2) + Math.pow(mouseY - j.y, 2))
      if (distanceClicked < j.score && i != attacker){
        players(attacker).score += 1
      }
    }
  }

  def gameState(): String = {
    var product: List[String] = List()
    for ((_, j) <- players){
      product = j.convToJson() :: product
    }
    Json.stringify(Json.toJson(product))
  }

  def update(): Unit = {}

  def EliminatePlayers(): Unit = {
    if (players.size > 3){
      val numberToEliminate: Int = Math.ceil(players.size * .1).toInt
      for (_ <- 0 to numberToEliminate){
        var smallestPlayer: Double = Double.PositiveInfinity
        var playerId: String = ""
        for ((_, i) <- players){
          if (i.score < smallestPlayer){
            smallestPlayer = i.score
            playerId = i.pid
          }
        }
        players -= playerId
      }
    }
  }
}
