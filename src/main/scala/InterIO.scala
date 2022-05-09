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

  def main(args: Array[String]): Unit =
    val (host, port) = (args.extractHost, args.extractPort)
    val server = HttpServer(port, host)
    server.serve()

