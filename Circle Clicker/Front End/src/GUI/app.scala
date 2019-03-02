package GUI

import javafx.scene.input.{KeyCode, KeyEvent, MouseEvent}
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle
import scalafx.scene.{Group, Scene}


object app extends JFXApp {

  val windowWidth: Double = 800
  val windowHeight: Double = 600

  val playerSpeed: Double = 10
  var radius1: Double =10

  var sceneGraphics: Group = new Group {}

  var circle: Circle = new Circle {
    centerX = 20.0
    centerY = 50.0
    radius = radius1
    fill = Color.Green
  }
  sceneGraphics.children.add(circle)

  def keyPressed(keyCode: KeyCode): Unit = {
    keyCode.getName match {
      case "Up" => circle.translateY.value -= playerSpeed       // have to get it to translate its y-center location as well
      case "Left" => circle.translateX.value -= playerSpeed
      case "Down" => circle.translateY.value += playerSpeed
      case "Right" => circle.translateX.value += playerSpeed
      case _ => println(keyCode.getName + " pressed with no action")
    }
  }

  val EPSILON: Double = 30
  def equals_CenterHit(d1: Double, d2: Double): Boolean = {
    (d1 - d2).abs < EPSILON
  }

  def dialate_circle(centerX: Double, centerY: Double): Unit = {
    if (equals_CenterHit(centerX, circle.centerX()) && equals_CenterHit(centerY, circle.centerY())) {
      circle.radius.value += radius1
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
}

