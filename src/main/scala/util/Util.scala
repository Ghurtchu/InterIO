package util

import scala.annotation.targetName
import scala.util.Try

object Util:

  extension (str: String)

    def toOption: Option[Int] = Try(str.toInt).toOption

    @targetName("register path handler as a tuple")
    def ~~>(other: Class[_]): (String, Class[_]) = (str, other)

  extension (args: Array[String])
    def getOrEmpty(index: Int): String = if args.length == 0 || index < 0 || index >= args.length then "" else args(index)

  def log(str: String): Unit = println(str)
