import java.awt.Color
import java.io.File

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.swing._

/**
  * Created by david.payne on 2/10/16.
  */
case class MatchWindow(matches: mutable.HashMap[String, ArrayBuffer[File]]) {

  def start = {
    val panel = new FlowPanel() {
      background = Color.RED
    }
//    panel.contents += new Button("hello")
    val top = new Frame() {
      contents = panel
      size = new Dimension(400, 400)

    }
    top.pack()
    top.visible = true
  }
}
