package http

import entity.Body.*
import entity.ContentType.*
import entity.StatusCode.*
import request.{HttpRequest, HttpRequestHandler, HttpResponse, HttpResponseWriter}
import socket.*
import socket.SocketProcessor.NOT_FOUND
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


abstract class AbstractHttpServer(val port: Int, val host: String):

  log("<~~ server started ~~>")

  given pathToHandlerMapping: mutable.Map[String, Class[_]] = mutable.Map(NOT_FOUND -> classOf[ResourceNotFoundHandler])

  private final val threadPool = Executors.newFixedThreadPool(Runtime.getRuntime.availableProcessors())

  final def serve(): Unit =
    registerPaths()
    Using(ServerSocket(port))(serverSocket => while true do threadPool.submit(SocketProcessor(Connection(serverSocket.accept))))

  @targetName("Register path and the handler class for the path")
  final def ++(pathHandlerPair: (String, Class[_])): Unit = pathToHandlerMapping += (pathHandlerPair._1 -> pathHandlerPair._2)

  def registerPaths(): Unit

class ResourceNotFoundHandler(val httpRequest: HttpRequest, override val writer: HttpResponseWriter) extends HttpRequestHandler(httpRequest, writer) :

  final override def handle(): Unit =
    val body = html("""<h1> Page not found </h1>""")
    val response = HttpResponse(body, NotFound)
    writer.write(response)




