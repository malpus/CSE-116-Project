package Client.GameLogic.gameStates

import Client.GameLogic.Game
import javafx.scene.input.KeyCode

class gameLoss(game: Game) extends gameState {
  override def keyInput(key: KeyCode): Unit = {}

  override def mouseInput(): Unit = {}
}
