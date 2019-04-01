package Client.GameLogic

import Client.client._

import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle

class Game {

  var playerContainer: Map[String, Player] = Map()

  val playerSpeed: Double = 10
  var radius1: Double = 10

  val windowWidth: Double = 800
  val windowHeight: Double = 600

  //def updateBoundariesGame(width: Double, height: Double): Unit = {}

  def createPlayer(name: String): Unit = {
    playerContainer += (name -> new Player())
  }

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
  }

  var TotalTime: Double = 0
  val ElimTime: Double = 40

  def update(deltaTime: Double): Unit = {

    TotalTime += deltaTime
    println(TotalTime)

    if (Math.abs(TotalTime -ElimTime) <.01) {
      EliminateUser()
      TotalTime = 0
    }

    updateScoreBoard()

    //updateBoundariesGame(circle)

    // update location circle
    // update positions?
  //update click totals (total points)
  }
}
