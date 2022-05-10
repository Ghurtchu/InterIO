package http

import request.{HttpRequest, HttpRequestHandler, HttpResponse}
import socket.*
import util.Util.log

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
import statuscode.StatusCode.*

abstract class AbstractHttpServer(val port: Int, val host: String):

  log("<~~ server started ~~>")

  given mapping: mutable.Map[String, Class[_]] = mutable.Map("resourceNotFound" -> classOf[ResourceNotFoundHandler])

  private final val pool = Executors.newFixedThreadPool(Runtime.getRuntime.availableProcessors())

  final def serve(): Unit =
    registerPaths()
    Using(ServerSocket(port))(serverSocket => while true do pool.submit(SocketProcessor(Connection(serverSocket.accept))))

  @targetName("Register path and the handler class for the path")
  final def ++(pathHandlerPair: (String, Class[_])): Unit = mapping += (pathHandlerPair._1 -> pathHandlerPair._2)

  def registerPaths(): Unit

class ResourceNotFoundHandler(val httpRequest: HttpRequest, val httpResponse: HttpResponse) extends HttpRequestHandler(httpRequest, httpResponse) :

  final override def handle(): Unit =
    val data: String = """<h1> Page not found </h1>"""
    val response = buildResponse(data)("text/html")(NotFound)
    httpResponse.write(response)




