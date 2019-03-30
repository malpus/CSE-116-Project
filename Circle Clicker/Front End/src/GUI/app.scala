package GUI

import GameLogic.Game
import javafx.scene.input.{KeyCode, KeyEvent, MouseEvent}
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle
import scalafx.scene.transform.Scale
import scalafx.scene.{Group, Scene}

object app extends JFXApp {
  val game = new Game()

  val windowWidth: Double = game.windowWidth
  val windowHeight: Double = game.windowHeight

  var sceneGraphics: Group = new Group {}

  val playerSpeed: Double = game.playerSpeed
  var circle: Circle = game.player1.circle

  sceneGraphics.children.add(game.player1.circle)

  def keyPressed(keyCode: KeyCode): Unit = {
    keyCode.getName match {
      case "Up" => circle.translateY.value -= playerSpeed
        circle.centerY.value -= circle.radius.value                                            // have to get it to translate its y-center location as well
      case "Left" => circle.translateX.value -= playerSpeed
        circle.centerX.value -= circle.radius.value
      case "Down" => circle.translateY.value += playerSpeed
        circle.centerY.value += circle.radius.value
      case "Right" => circle.translateX.value += playerSpeed
        circle.centerX.value += circle.radius.value
      case _ => println(keyCode.getName + " pressed with no action")
    }
  }

  val EPSILON: Double = 50
  def equals_CenterHit(d1: Double, d2: Double): Boolean = {
    (d1 - d2).abs < EPSILON
  }

  def dialate_circle(centerX: Double, centerY: Double): Unit = {
    if (equals_CenterHit(centerX, circle.centerX()) && equals_CenterHit(centerY, circle.centerY())) {
      circle.radius.value += 5
      game.player1.points += 1
    }
  }

  this.stage = new PrimaryStage{
    this.title = "pong"
    scene = new Scene(windowWidth, windowHeight){
      content = List(sceneGraphics)
      // add an EventHandler[KeyEvent] to control player movement
      addEventHandler(KeyEvent.KEY_PRESSED, (event: KeyEvent) => keyPressed(event.getCode))
      addEventHandler(MouseEvent.MOUSE_CLICKED, (event: MouseEvent) => dialate_circle(event.getX, event.getY))
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

// Start Animations. Calls update 60 times per second (takes update as an argument)



