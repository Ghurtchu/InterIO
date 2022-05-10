package util

import request.HttpRequestHandler
import util.DefaultParams.*

import scala.annotation.targetName
import scala.util.Try

object Util:

  extension (param: String)

    final def toOption: Option[Int] = Try(param.toInt).toOption

    @targetName("register path ~~> path handler as a tuple")
    final def ~~>[A <: HttpRequestHandler](handler: Class[A]): (String, Class[A]) = (param, handler)

  extension (args: Array[String])

    final def getOrEmpty(index: Int): String = if args.length == 0 || index < 0 || index >= args.length then "" else args(index)
    final def extractHost: String = extractParam(args(0))(DEFAULT_HOST)
    final def extractPort: Int = extractParam(args(1).toInt)(DEFAULT_PORT)

    private def extractParam[A](param: => A)(default: A): A = Try(param).toOption.fold(default)(identity)

  final def log(str: String): Unit = println(str)

object DefaultParams:

  val DEFAULT_PORT = 8080
  val DEFAULT_HOST = "localhost"
