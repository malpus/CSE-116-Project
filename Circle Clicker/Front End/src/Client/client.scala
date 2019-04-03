package Client

import Client.GameLogic.{Game, Player}
import javafx.scene.input.{KeyCode, KeyEvent, MouseEvent}
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.shape.Circle
import scalafx.scene.{Group, Scene}

object client extends JFXApp {

  val game = new Game(new Player("Player 1"))

  val clientPlayerCircle: Circle = game.client.circle

  var sceneGraphics: Group = new Group {}
  val windowWidth: Double = 800
  val windowHeight: Double = 600

  var lastUpdateTime: Long = System.nanoTime()

  sceneGraphics.children.add(game.client.circle) /**Placeholder, should be handled by gamePre (but it's being a bitch)*/

  def keyPressed(keyCode: KeyCode): Unit = {
    game.gameState.keyInput(keyCode)
  } /**Passes key presses to the gameState in game*/

  def dilate_circle(mouseX: Double, mouseY: Double): Unit = {
    game.gameState.mouseInput(mouseX, mouseY)
  } /**Passes click to gameState in game*/

  this.stage = new PrimaryStage{
    this.title = "CSE-116-Project"
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
    game.gameState.update(dt)
  }
  AnimationTimer(update).start()
}
