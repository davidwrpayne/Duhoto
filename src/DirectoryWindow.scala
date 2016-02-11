
import java.awt.Color

import scala.swing.FileChooser.{SelectionMode, Result}
import scala.swing._
import scala.swing.event.ButtonClicked

class DirectoryWindow(title: String, width: Int, height: Int) {

  def deleteDirectory(): Unit = ???

  //  var directories: List[String] = List()

  var textBox = newListView(List.empty[String])

    def newListView(data: Seq[String]) = {
      new ListView[String]() {
        listData = data
        preferredSize = new Dimension(width - 40, height - 100)
      }
  }

  val recursiveCheckBox = new CheckBox("Recursively search directories")

  val fileDialog = new FileChooser()
  fileDialog.fileSelectionMode = SelectionMode.DirectoriesOnly
  fileDialog.multiSelectionEnabled = true

  val delDir = new Button("Remove Directory from scan") {
    reactions += {
      case ButtonClicked(_) =>

        if (textBox.selection.items.isEmpty) {
          Dialog.showMessage(message = "You haven't selected a directory to remove from the list")
        } else if (textBox.selection.items.nonEmpty) {
          println(s"delete ${textBox.selection.items}")
          textBox.selection.items.foreach { item =>
            textBox.listData = textBox.listData.filter({
              !_.equals(item)
            })
            textBox = newListView(textBox.listData)

            refreshViews()
          }
        }
    }
  }


  val dirButton = new Button("Add Directory") {
    reactions += {
      case ButtonClicked(_) => {
        fileDialog.showOpenDialog(new Component {})
        // do check if they click cancel on selecting a file.

        if (fileDialog.selectedFiles.nonEmpty) {
          textBox.listData = textBox.listData ++ fileDialog.selectedFiles.map {
            _.getPath()
          }
          refreshViews()
        }
      }
    }
  }


  val scanButton = new Button("Scan Directories") {
    reactions += {
      case ButtonClicked(_) => {
        val recurse = recursiveCheckBox.selected
        val scan = new Scan(textBox.listData, recursively = recurse)
        val matches = scan.execute()
        val matchWindow = new MatchWindow(matches)

      }
    }
  }

  val frame = new MainFrame {
    this.preferredSize = new Dimension(width, height)
    this.title = DirectoryWindow.this.title
  }

  def setup = {
    refreshViews()
  }

  def refreshViews(): Unit = {
    val panel = new FlowPanel() {
      contents += textBox
      contents += dirButton
      contents += delDir
      contents += scanButton
      contents += recursiveCheckBox
    }
    this.frame.contents = panel
  }


}
