import handler.{AboutHandler, HomeHandler, RootHandler}
import http.AbstractHttpServer
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


class HttpServer private(port: Int, host: String) extends AbstractHttpServer(port, host) :

  override final def registerPaths(): Unit =
    ++("/" ~~> classOf[RootHandler])
    ++("/home" ~~> classOf[HomeHandler])
    ++("/about" ~~> classOf[AboutHandler])

object HttpServer:
  def apply(port: Int, host: String): AbstractHttpServer = new HttpServer(port, host)