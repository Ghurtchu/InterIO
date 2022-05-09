package http

import request.{HttpRequest, HttpRequestHandler, HttpResponse}
import util.Util.log
import socket.*

import java.io.*
import java.net.{ServerSocket, Socket}
import java.nio.charset.{Charset, StandardCharsets}
import java.time.{Instant, LocalDate}
import java.util.StringTokenizer
import java.util.concurrent.{ExecutorService, Executors}
import java.util.stream.Collectors
import scala.annotation.targetName
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.jdk.StreamConverters.*
import scala.util.{Try, Using}

abstract class AbstractHttpServer(val port: Int, val host: String):

  log("<~~ server started ~~>")

  given mapping: mutable.Map[String, Class[_]] = mutable.Map(
    "resourceNotFound" -> classOf[ResourceNotFoundHandler]
  )

  private val pool = Executors.newFixedThreadPool(Runtime.getRuntime.availableProcessors())

  def serve(): Unit =
    registerPaths()
    Using(ServerSocket(port))(serverSocket => while true do pool.submit(SocketProcessor(serverSocket.accept)))

  @targetName("Register path and the handler class for the path")
  def ++(pathHandlerPair: (String, Class[_])): Unit = mapping += (pathHandlerPair._1 -> pathHandlerPair._2)

  def registerPaths(): Unit

class ResourceNotFoundHandler(val httpRequest: HttpRequest, val httpResponse: HttpResponse) extends HttpRequestHandler(httpRequest, httpResponse) :

  override def handle(): Unit =
    log(s"handling /${this.getClass.getSimpleName.toLowerCase}")
    val data: String = """<h1> Page not found </h1>"""
    val header = buildHeader(data)("text/html")(404)
    httpResponse.write(data)(header)



