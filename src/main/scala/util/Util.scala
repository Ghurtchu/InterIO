package util

import scala.annotation.targetName
import scala.util.Try

object Util:

  extension (str: String)

    def toOption: Option[Int] = Try(str.toInt).toOption

    @targetName("register path ~~> path handler as a tuple")
    def ~~>(handler: Class[_]): (String, Class[_]) = (str, handler)

  extension (args: Array[String])
    def getOrEmpty(index: Int): String = if args.length == 0 || index < 0 || index >= args.length then "" else args(index)
    def extractHost: String = extractParam(args(0))(DefaultParams.DEFAULT_HOST)
    def extractPort: Int = extractParam(args(1).toInt)(DefaultParams.DEFAULT_PORT)

    private def extractParam[A](param: => A)(default: A): A = Try(param).toOption.fold(default)(identity)

  def log(str: String): Unit = println(str)

object DefaultParams:

  val DEFAULT_PORT = 8080
  val DEFAULT_HOST = "localhost"
