package Client.GameLogic

import Client.GameLogic.gameStates._
import Client.client._

class Game(var client: Player) {
  var gameState: gameState = new gamePlay(this)

  var playerContainer: Map[String, Player] = Map() //Local Client-Side Container for All Players

  val playerSpeed: Double = 20
  val radiusStart: Double = 10
  val deltaRadius: Double = 10

  var ElapsedTime: Double = 0
  val ElimTime: Double = 10 /** Time until the next player is eliminated *//**WILL SOON BE DEPRECATED*/


  def createPlayer(name: String): Unit = {
    playerContainer += (name -> new Player(name))
  } /** Creates player with given name*/

  def updateScoreBoard(): Unit = {
  }

  def EliminateUser(debug: Boolean = false): Unit = {
    var highestRadius: Double = 0
    var name: String = ""
    for ((i, j) <- playerContainer){
      if (j.circle.radius.value > highestRadius){
        highestRadius = j.circle.radius.value
        name = i
      }
    }
    sceneGraphics.children.remove(game.playerContainer(name).circle)
    if (!debug) {
      playerContainer = playerContainer - name
    }
    Client.client.dummyMethod()
  } /** Eliminates the highest radius player*//**WILL SOON BE DEPRECATED*/

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
