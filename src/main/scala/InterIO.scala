import handler.{AboutHandler, RootHandler, ScalaHandler}
import http.AbstractHttpServer
import sun.net.httpserver.HttpServerImpl
import util.Util.*

import java.io
import java.io.*
import java.net.{InetSocketAddress, Socket}
import java.nio.charset.Charset
import java.time.LocalDate
import scala.util.{Try, Using}

object InterIO:

  def main(args: Array[String]): Unit =
    val (host, port) = (args.extractHost, args.extractPort)
    val httpServer = HttpServer(port, host)
    httpServer.serve()


class HttpServer(port: Int, host: String) extends AbstractHttpServer(port, host) :

  final override def registerPaths(): Unit =
    ++("/" ~~> classOf[RootHandler])
    ++("/scala" ~~> classOf[ScalaHandler])
    ++("/about" ~~> classOf[AboutHandler])
