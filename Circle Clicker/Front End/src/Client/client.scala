package Client

import Client.GameLogic.{Game, Player}
import Client.GameLogic.gameStates._
import javafx.scene.input.{KeyCode, KeyEvent, MouseEvent}
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.shape.Circle
import scalafx.scene.{Group, Scene}

object client extends JFXApp {

  val game = new Game(new Player("Player 1"))
  val selfHarm: Boolean = true

  game.createPlayer("Player 2")
  game.createPlayer("Player 3")
  game.createPlayer("Player 4")

  val clientPlayerCircle: Circle = game.client.circle

  var sceneGraphics: Group = new Group {}
  val windowWidth: Double = 800
  val windowHeight: Double = 600

  var lastUpdateTime: Long = System.nanoTime()

  sceneGraphics.children.add(game.client.circle)
  sceneGraphics.children.add(game.playerContainer("Player 2").circle)
  sceneGraphics.children.add(game.playerContainer("Player 3").circle)
  sceneGraphics.children.add(game.playerContainer("Player 4").circle)

  def instantiatePlayers(): Unit = {
    for ((_, i) <- game.playerContainer){
      sceneGraphics.children.add(i.circle)
    }
  } //Goes through playerContainer in game:10 and places the players on the board -- NOT YET IMPLEMENTED

  def keyPressed(keyCode: KeyCode): Unit = {
    game.gameState.keyInput(keyCode)
  } /**Passes key presses to the gameState in game*/

  def dilate_circle(mouseX: Double, mouseY: Double): Unit = {
    for ((i, j) <- game.playerContainer) {
      val x: Double = j.circle.centerX.value
      val y: Double = j.circle.centerY.value
      val radius: Double = j.circle.radius.value
      val clickDistance: Double = Math.sqrt(Math.pow(mouseX - x, 2) + Math.pow(mouseY - y, 2))
      if (clickDistance <= radius) {
        game.playerContainer(i).points += 1
        game.playerContainer(i).circle.radius_=(game.deltaRadius + radius)
      }
    }
    if (selfHarm){
      val x: Double = game.client.circle.centerX.value
      val y: Double = game.client.circle.centerY.value
      val radius: Double = game.client.circle.radius.value
      val clickDistance: Double = Math.sqrt(Math.pow(mouseX - x, 2) + Math.pow(mouseY - y, 2))
      if (clickDistance <= radius) {
        game.client.circle.radius_=(game.deltaRadius + game.client.circle.radius.value)
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

  def dummyMethod(): Unit = {}
}
