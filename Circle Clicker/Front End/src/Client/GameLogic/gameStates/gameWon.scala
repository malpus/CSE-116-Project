package Client.GameLogic.gameStates

import Client.GameLogic.Game
import javafx.scene.input.KeyCode
import Client.client.sceneGraphics

class gameWon(game: Game) extends gameState {
  override def keyInput(key: KeyCode): Unit = {}

  override def mouseInput(mouseX: Double, mouseY: Double): Unit = {}

  override def update(dt: Double): Unit = {
    game.ElapsedTime += dt
  }

  override def EliminatePlayer(debug: Boolean): Unit = {}

  override def main(): Unit = {
    for ((_, i) <- game.playerContainer) {
      sceneGraphics.children.remove(i.circle)
    }
    game.playerContainer = Map()
    println("You Win!")
  }
}
