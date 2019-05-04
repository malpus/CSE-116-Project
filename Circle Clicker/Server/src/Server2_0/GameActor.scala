package Server2_0

import akka.actor.Actor


class GameActor extends Actor {

  val game: Game = new Game()

  override def receive: Receive = {
    case message: AddPlayer => game.addPlayer(message.username)
    case message: RemovePlayer => game.removePlayer(message.username)
    case message: MovePlayer => game.players(message.username).move(message.x, message.y)
    case message: Click => game.clickPlayer(message.username, message.x, message.y)

    case UpdateGame => game.update()
    case SendGameState => sender() ! GameState(game.gameState())
    case EliminatePlayers => game.EliminatePlayers()
  }
}
