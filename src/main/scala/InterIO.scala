import http.HttpServer
import util.Util.*
import sun.net.httpserver.HttpServerImpl

import java.io
import java.io.*
import java.net.{InetSocketAddress, Socket}
import java.nio.charset.Charset
import java.time.LocalDate
import scala.collection.mutable
import scala.util.{Try, Using}

object InterIO:

  private val DEFAULT_PORT = 8080
  private val DEFAULT_HOST = "localhost"

  def main(args: Array[String]): Unit =
    val host = Try(args(0)).toOption.fold(DEFAULT_HOST)(identity)
    val port = Try(args(1).toInt).toOption.fold(DEFAULT_PORT)(identity)
    val server = HttpServer(port, host)
    server.registerPaths()
    server.serve()

