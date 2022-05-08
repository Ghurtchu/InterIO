import Util.*
import sun.net.httpserver.HttpServerImpl

import java.io
import java.io.*
import java.net.{InetSocketAddress, Socket}
import java.nio.charset.Charset
import java.time.LocalDate
import scala.collection.mutable
import scala.util.Using

object Main:

  private val port = 8080
  private val host = "localhost"

  def main(args: Array[String]): Unit =
    val server = HttpServer(port, host)
    server.registerPaths()
    server.serve()

