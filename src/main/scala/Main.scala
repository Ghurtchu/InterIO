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


class HttpServer(port: Int, host: String) extends AbstractHttpServer(port, host) :

  override final def registerPaths(): Unit =
    ++("/" ~~> classOf[Root])
    ++("/home" ~~> classOf[Home])


class Root(val httpRequest: HttpRequest, val httpResponse: HttpResponse) extends HttpRequestHandler(httpRequest, httpResponse) :

  override def handle(): Unit =
    log(s"handling /${this.getClass.getSimpleName.toLowerCase}")
    val data: String = """{"name": "Nika", "age": 22}"""
    val header = buildHeader(data)("application/json")
    httpResponse.write(data)(header)

class Home(val httpRequest: HttpRequest, val httpResponse: HttpResponse) extends HttpRequestHandler(httpRequest, httpResponse) :

  override def handle(): Unit =
    log(s"handling /${this.getClass.getSimpleName.toLowerCase}")
    val data: String = """{"name": "Anzor", "age": 23}"""
    val header = buildHeader(data)("application/json")
    httpResponse.write(data)(header)

