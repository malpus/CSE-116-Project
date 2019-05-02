package Client.GameLogic

import Client.GameLogic.gameStates._
import scalafx.scene.paint.Color
import Client.client.sceneGraphics

class Game(var client: Player) {
  var gameState: gameState = new gamePre(this) /**Where the game state is held, will change depending on the phase of the game*/
  var playerContainer: Map[String, Player] = Map() /**Local Client-Side Container for Enemy Players*/
  var ElapsedTime: Double = 0

  def createPlayer(name: String): Unit = {
    playerContainer += (name -> new Player(name))
    playerContainer(name).circle.fill = Color.Red
  } /** Creates player with given name, default creates red players*/

  def instantiatePlayers(): Unit = {
    for ((_, i) <- playerContainer){
      sceneGraphics.children.add(i.circle)
    }
  }

  def updateScoreBoard(): Unit = {}

  /*createPlayer("Player 2")
  createPlayer("Player 3")
  createPlayer("Player 4")*/

  def start(): Unit = {
    gameState.main()
  }

  //def updateBoundariesGame(width: Double, height: Double): Unit = {}
}
