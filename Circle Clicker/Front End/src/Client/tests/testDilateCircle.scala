package Client.tests

import Client.GameLogic.{Config, Game, Player}
import Client.client._
import org.scalatest._

class testDilateCircle extends FunSuite {

  var playerContainer: Map[String, Player] = Map()

  def createPlayer(name: String): Unit = {
    playerContainer += (name -> new Player(name))
  }


  def EliminateUser(): Unit = {
    var highestRadius: Double = 0
    var name: String = ""
    for ((i, j) <- playerContainer) {
      if (j.circle.radius.value > highestRadius) {
        highestRadius = j.circle.radius.value
        name = i
      }
    }
    sceneGraphics.children.remove(game.playerContainer(name).circle)
    playerContainer = playerContainer - name
  }
  val game = new Game(new Player("Self"))
  def dilate_circle(mouseX: Double, mouseY: Double): Unit = {
    for ((i, j) <- game.playerContainer) {
      val x: Double = j.circle.centerX.value
      val y: Double = j.circle.centerY.value
      val radius: Double = j.circle.radius.value
      val clickDistance: Double = Math.sqrt(Math.pow(mouseX - x, 2) + Math.pow(mouseY - y, 2))
      if (clickDistance <= radius && j.circle != clientPlayerCircle && !Config.selfHarm) {
        game.playerContainer(i).points += 1
        game.playerContainer(i).circle.radius_=(Config.deltaRadius + radius)
      } else if (clickDistance <= radius && Config.selfHarm) {
        game.playerContainer(i).points += 1
        game.playerContainer(i).circle.radius_=(Config.deltaRadius + radius)
      }
    }
  }

      test("test DilateCircle") {


        game.createPlayer("Evan")
        game.createPlayer("Tyler")
        game.createPlayer("Kyle")
        game.playerContainer.apply("Evan").circle.centerX_=(50)
        game.playerContainer.apply("Evan").circle.centerX_=(50)

        dilate_circle(50, 50)

        //DilateCircle increases the radius by 10 each click
        game.playerContainer.apply("Evan").circle.radius.value == 20

        dilate_circle(50,50)
        game.playerContainer.apply("Evan").circle.radius.value == 30
      }
}
