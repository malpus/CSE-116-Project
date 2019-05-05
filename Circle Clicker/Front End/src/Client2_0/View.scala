package Client2_0

import io.socket.client.{IO, Socket}
import io.socket.emitter.Emitter
import javafx.application.Platform
import javafx.scene.input.{KeyEvent, MouseEvent}
import play.api.libs.json.Json
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.{Group, Scene}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle

class HandleMessagesFromPython() extends Emitter.Listener {
  override def call(objects: Object*): Unit = {
    Platform.runLater(() => {
      val gameStateList: List[String] = Json.parse(objects.apply(0).toString).as[List[String]]
      println(gameStateList)
      val circleList: Group = new Group {}
      for (i <- gameStateList){
        val player: Map[String, String] = Json.parse(i).as[Map[String, String]]
        println(player)
        val circle: Circle = new Circle {
          centerX_=(player("posx").toDouble)
          centerY_=(player("posy").toDouble)
          radius_=(10+ 5*player("score").toDouble)
          fill = Color.Red
        }
        println(DesktopGUI.clientID == player("pid"))
        if (DesktopGUI.clientID == player("pid")){
          circle.fill = Color.Green
        }
        DesktopGUI.serverCircleList.children.add(circle)
      }

    })
  }
}



object DesktopGUI extends JFXApp {
  var socket: Socket = IO.socket("http://localhost:8080/")
  socket.on("gameState", new HandleMessagesFromPython)

  socket.connect()
  socket.emit("connect")

  val clientID: String = socket.id()

  var lastUpdateTime: Long = System.nanoTime()

  var serverCircleList: Group = new Group {}

  this.stage = new PrimaryStage {
    title = "Project: Circle Clicker"
    scene = new Scene() {
      content = List(serverCircleList)
      addEventHandler(KeyEvent.KEY_PRESSED, (event: KeyEvent) => socket.emit("GUIkeyStates", event.getCode))
      addEventHandler(MouseEvent.MOUSE_CLICKED, (event: MouseEvent) => socket.emit("click",Map("x" -> event.getX, "y" -> event.getY)
    }
  }
}