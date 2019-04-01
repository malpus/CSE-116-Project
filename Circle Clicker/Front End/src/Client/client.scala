package Client

import Client.GameLogic.Game
import javafx.scene.input.{KeyCode, KeyEvent, MouseEvent}
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.shape.Circle
import scalafx.scene.{Group, Scene}

object client extends JFXApp {
  val game = new Game()
  val selfHarm: Boolean = true

  game.createPlayer("Player 1")
  game.createPlayer("Player 2")
  game.createPlayer("Player 3")
  game.createPlayer("Player 4")

  val clientPlayerCircle: Circle = game.playerContainer("Player 1").circle

  var sceneGraphics: Group = new Group {}
  val windowWidth: Double = 800
  val windowHeight: Double = 600

  var lastUpdateTime: Long = System.nanoTime()

  sceneGraphics.children.add(game.playerContainer("Player 1").circle)
  sceneGraphics.children.add(game.playerContainer("Player 2").circle)
  sceneGraphics.children.add(game.playerContainer("Player 3").circle)
  sceneGraphics.children.add(game.playerContainer("Player 4").circle)

  def instantiatePlayers(): Unit = {
    for ((_, i) <- game.playerContainer){
      sceneGraphics.children.add(i.circle)
    }
  } //Goes through playerContainer in game:10 and places the players on the board -- NOT YET IMPLEMENTED

  def keyPressed(keyCode: KeyCode): Unit = {
    keyCode.getName match {
      case "Up" => clientPlayerCircle.translateY.value -= game.playerSpeed
        clientPlayerCircle.centerY.value -= clientPlayerCircle.radius.value
      case "Left" => clientPlayerCircle.translateX.value -= game.playerSpeed
        clientPlayerCircle.centerX.value -= clientPlayerCircle.radius.value
      case "Down" => clientPlayerCircle.translateY.value += game.playerSpeed
        clientPlayerCircle.centerY.value += clientPlayerCircle.radius.value
      case "Right" => clientPlayerCircle.translateX.value += game.playerSpeed
        clientPlayerCircle.centerX.value += clientPlayerCircle.radius.value
    }
  } /**Processes key presses from the client*/

  def dilate_circle(mouseX: Double, mouseY: Double): Unit = {
    for ((i, j) <- game.playerContainer) {
      val x: Double = j.circle.centerX.value
      val y: Double = j.circle.centerY.value
      val radius: Double = j.circle.radius.value
      val clickDistance: Double = Math.sqrt(Math.pow(mouseX - x, 2) + Math.pow(mouseY - y, 2))
      if (clickDistance <= radius && j.circle != clientPlayerCircle && !selfHarm) {
        game.playerContainer(i).points += 1
        game.playerContainer(i).circle.radius_=(game.deltaRadius + radius)
      } else if (clickDistance <= radius && selfHarm){
        game.playerContainer(i).points += 1
        game.playerContainer(i).circle.radius_=(game.deltaRadius + radius)
      }
    }
  } /**Interprets client mouse clicks into attacks on other players*/

  this.stage = new PrimaryStage{
    this.title = "pong"
    scene = new Scene(windowWidth, windowHeight){
      content = List(sceneGraphics)
      // add an EventHandler[KeyEvent] to control player movement
      addEventHandler(KeyEvent.KEY_PRESSED, (event: KeyEvent) => keyPressed(event.getCode))
      addEventHandler(MouseEvent.MOUSE_CLICKED, (event: MouseEvent) => dilate_circle(event.getX, event.getY))
    }
  }

  val update: Long => Unit = (time: Long) => {
    val dt: Double = (time - lastUpdateTime) / 1000000000.0
    lastUpdateTime = time
    game.update(dt)

    //player1Sprite.translateX.value = convertX(game.player1.location.x, playerSpriteSize)
  }
  AnimationTimer(update).start()
}
