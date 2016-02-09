import java.io.{FilenameFilter, FileFilter, File}


class Scan(directories: Seq[String],recursively: Boolean) {

  def execute() = {
    val distinctDirectories = directories.distinct
    var allFiles = Array.empty[File]
     distinctDirectories foreach { dir =>
       allFiles = allFiles ++ Scan.scan(new File(dir), recursively)

     }

    allFiles.foreach{ file =>
      println(file.getPath())
    }
  }



}


object Scan {

  def listFiles(f: File, filter: FileFilter): Array[File] = {
    val these = f.listFiles(filter)
    these.filter(_.isFile)
  }

  def recursiveFilesList(f: File, filter: FileFilter): Array[File] = {
    val these = f.listFiles(filter)
    these.filter(_.isFile) ++ these.filter(_.isDirectory()).flatMap(recursiveFilesList(_,filter))
  }

  def scan(f: File, recursive: Boolean): Array[File] = {
    val filter: FileFilter = new FileFilter {
      override def accept(pathname: File): Boolean = {
        val pattern =  "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)".r
        pattern.findFirstIn(pathname.getPath) match {
          case Some(_) => true
          case None => false
        }
      }

    }

    recursive match {
      case true => recursiveFilesList(f, filter = filter)
      case false => listFiles(f, filter = filter)
    }
  }

//  case class Filter(types: Array[String])
}