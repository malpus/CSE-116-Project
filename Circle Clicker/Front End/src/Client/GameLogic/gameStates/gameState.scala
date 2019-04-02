package Client.GameLogic.gameStates

import javafx.scene.input.KeyCode

trait gameState {
  def keyInput(key: KeyCode): Unit

  def mouseInput(): Unit
}
