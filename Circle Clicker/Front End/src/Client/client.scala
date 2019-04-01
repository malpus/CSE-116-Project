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

  game.createPlayer("Player 1")
  game.createPlayer("Player 2")

  val windowWidth: Double = game.windowWidth
  val windowHeight: Double = game.windowHeight

  var sceneGraphics: Group = new Group {}

  val playerSpeed: Double = game.playerSpeed
  var circle: Circle = game.playerContainer("Player 1").circle
  var circle2: Circle = game.playerContainer("Player 2").circle

  sceneGraphics.children.add(game.playerContainer("Player 1").circle)
  sceneGraphics.children.add(game.playerContainer("Player 2").circle)

  val deltaRadius: Double = 10

  def keyPressed(keyCode: KeyCode): Unit = {
    keyCode.getName match {
      case "Up" => circle.translateY.value -= playerSpeed
        circle.centerY.value -= circle.radius.value
      case "Left" => circle.translateX.value -= playerSpeed
        circle.centerX.value -= circle.radius.value
      case "Down" => circle.translateY.value += playerSpeed
        circle.centerY.value += circle.radius.value
      case "Right" => circle.translateX.value += playerSpeed
        circle.centerX.value += circle.radius.value
      case _ => println(keyCode.getName + " pressed with no action")
    }
  }

  def dilate_circle(mouseX: Double, mouseY: Double): Unit = {
    for ((i, j) <- game.playerContainer) {
      val x: Double = j.circle.centerX.value
      val y: Double = j.circle.centerY.value
      val radius: Double = j.circle.radius.value
      if (Math.sqrt(Math.pow(mouseX - x, 2) + Math.pow(mouseY - y, 2)) <= radius) {
        game.playerContainer(i).points += 1
        game.playerContainer(i).circle.radius_=(deltaRadius + radius)
      }
    }
  }

  this.stage = new PrimaryStage{
    this.title = "pong"
    scene = new Scene(windowWidth, windowHeight){
      content = List(sceneGraphics)
      // add an EventHandler[KeyEvent] to control player movement
      addEventHandler(KeyEvent.KEY_PRESSED, (event: KeyEvent) => keyPressed(event.getCode))
      addEventHandler(MouseEvent.MOUSE_CLICKED, (event: MouseEvent) => dilate_circle(event.getX, event.getY))
    }
  }

  var lastUpdateTime: Long = System.nanoTime()

  val update: Long => Unit = (time: Long) => {
    val dt: Double = (time - lastUpdateTime) / 1000000000.0
    lastUpdateTime = time
    game.update(dt)

    //player1Sprite.translateX.value = convertX(game.player1.location.x, playerSpriteSize)
  }
  AnimationTimer(update).start()
}
