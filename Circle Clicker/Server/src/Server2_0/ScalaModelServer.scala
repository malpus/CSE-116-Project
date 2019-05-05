package Server2_0

import java.net.InetSocketAddress

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.io.{IO, Tcp}
import akka.util.ByteString
import play.api.libs.json.{JsValue, Json}

class ScalaModelServer(gameActor: ActorRef) extends Actor {

  import Tcp._
  import context.system

  IO(Tcp) ! Bind(self, new InetSocketAddress("localhost", 8000))

  var webServers: Set[ActorRef] = Set()
  var buffer: String = ""

  override def receive: Receive = {
    case b: Bound => println("Listening on port: " + b.localAddress.getPort)

    case c: Connected =>
      this.webServers = this.webServers + sender()
      sender() ! Register(self)

    case PeerClosed =>
      this.webServers = this.webServers - sender()

    case r: Received =>
      buffer += r.data.utf8String
      while (buffer.contains("~")) {
        val curr = buffer.substring(0, buffer.indexOf("~"))
        buffer = buffer.substring(buffer.indexOf("~") + 1)
        handleMessageFromWebServer(curr)
      }

    case SendGameState =>
      gameActor ! SendGameState

    case gs: GameState =>
      println(gs.gameState)
      this.webServers.foreach((client: ActorRef) => client ! Write(ByteString(gs.gameState + "~")))
  }


  def handleMessageFromWebServer(messageString:String):Unit = {
    val message: JsValue = Json.parse(messageString)
    val username = (message \ "username").as[String]
    val messageType = (message \ "action").as[String]

    messageType match {
      case "connected" => gameActor ! AddPlayer(username)
      case "disconnected" => gameActor ! RemovePlayer(username)
      case "move" =>
        val x = (message \ "x").as[Double]
        val y = (message \ "y").as[Double]
        gameActor ! MovePlayer(username, x, y)
      case "click" =>
        val x = (message \ "x").as[Double]
        val y = (message \ "y").as[Double]
        gameActor ! Click(username, x, y)
    }
  }

}


object ScalaModelServer {

  def main(args: Array[String]): Unit = {
    val actorSystem = ActorSystem()

    import actorSystem.dispatcher

    import scala.concurrent.duration._

    val gameActor = actorSystem.actorOf(Props(classOf[GameActor]))
    val server = actorSystem.actorOf(Props(classOf[ScalaModelServer], gameActor))

    actorSystem.scheduler.schedule(16.milliseconds, 32.milliseconds, gameActor, UpdateGame)
    actorSystem.scheduler.schedule(32.milliseconds, 32.milliseconds, server, SendGameState)
    actorSystem.scheduler.schedule(30.seconds, 30.seconds, gameActor, EliminatePlayers)
  }

}
