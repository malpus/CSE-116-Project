package Client.GameLogic

import Client.client._
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle

class Game {

  var playerContainer: Map[String, Player] = Map() //Local Client-Side Container for All Players

  val playerSpeed: Double = 10
  val radiusStart: Double = 10
  val deltaRadius: Double = 10

  var ElapsedTime: Double = 0
  val ElimTime: Double = 10 /** Time until the next player is eliminated */


  def createPlayer(name: String): Unit = {
    playerContainer += (name -> new Player())
  } /** Creates player with given name and optional given color */

  def updateScoreBoard(): Unit = {
  }

  def EliminateUser(): Unit = {
    var highestRadius: Double = 0
    var name: String = ""
    for ((i, j) <- playerContainer){
      if (j.circle.radius.value > highestRadius){
        highestRadius = j.circle.radius.value
        name = i
      }
    }
    sceneGraphics.children.remove(game.playerContainer(name).circle)
    playerContainer = playerContainer - name
  } /** Eliminates the highest radius player. Does not eliminate multiple players with matching radii */

  def eliminateUser(): Unit = {
    var highestRadius: Double = 0
    var name: String = ""
    for ((i, j) <- playerContainer){
      if (j.circle.radius.value > highestRadius){
        highestRadius = j.circle.radius.value
        name = i
      }
    }
    playerContainer = playerContainer - name
  }

  def update(deltaTime: Double): Unit = {

    ElapsedTime += deltaTime
    println(ElapsedTime)

    if (Math.abs(ElapsedTime -ElimTime) <.01) {
      EliminateUser()
      ElapsedTime = 0
    }

    updateScoreBoard()

    //updateBoundariesGame(circle)

    // update location circle
    // update positions?
    //update click totals (total points)
  }/** Currently just runs EliminateUser after ElimTime:40, and regularly runs updateScoreBoard */

  //def updateBoundariesGame(width: Double, height: Double): Unit = {}
}
