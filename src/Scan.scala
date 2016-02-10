import java.io.{FilenameFilter, FileFilter, File}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer


class Scan(directories: Seq[String], recursively: Boolean) {



  def execute(): mutable.HashMap[String, ArrayBuffer[File]] = {
    val matches = new mutable.HashMap[String,mutable.ArrayBuffer[File]]()
    val distinctDirectories = directories.distinct
    var allFiles = Array.empty[File]
    distinctDirectories foreach { dir =>
      allFiles = allFiles ++ Scan.getFiles(new File(dir), recursively)
    }

    //starting matching
    for (i <- 0 until allFiles.size - 1) {
      val fileOne = allFiles(i)

      for (j <- i + 1 until allFiles.size) {
        val fileTwo = allFiles(j)
        if (fileOne.getName.equals(fileTwo.getName()) ) {
//          println(s"file: ${fileOne.getPath()} equals file: ${fileTwo.getPath()}")
          var key = fileOne.getName()
          matches.get(key) match {
            case Some(array) => {
              if (!array.contains(fileOne)) array += (fileOne)
              if (!array.contains(fileTwo)) array += (fileTwo)
              matches.put(key,array)
            }
            case None => {
              matches.put(key, mutable.ArrayBuffer(fileOne, fileTwo))
              println("adding array and both matches")
              println(key)
            }
          }
        }

      }
    }
    matches
  }



}


object Scan {

  def listFiles(f: File, filter: FileFilter): Array[File] = {
    val these = f.listFiles(filter)
    these.filter(_.isFile)
  }

  def recursiveFilesList(f: File, filter: FileFilter): Array[File] = {
    val these = f.listFiles(filter)
    these.filter(_.isFile) ++ these.filter(_.isDirectory()).flatMap(recursiveFilesList(_, filter))
  }

  def getFiles(f: File, recursive: Boolean): Array[File] = {
    val filter: FileFilter = new FileFilter {
      override def accept(pathname: File): Boolean = {
        val pattern = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)".r
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