package Client.GameLogic.gameStates

import Client.GameLogic.{Config, Game}
import javafx.scene.input.KeyCode
import Client.client.sceneGraphics


class gamePre(game: Game) extends gameState {
  override def keyInput(key: KeyCode): Unit = {}

  override def mouseInput(mouseX: Double, mouseY: Double): Unit = {}

  override def update(dt: Double): Unit = {
    game.ElapsedTime += dt
    if (Math.abs(game.ElapsedTime - Config.warmUpTime) < .01){
      game.gameState = new gamePlay(game)
      game.gameState.main()
    }
  }

  override def EliminatePlayer(debug: Boolean): Unit = {}

  override def main(): Unit = {
    println("Warm Up!")
  }
}
