import SocketProcessor.pool
import Util.*

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

  given mapping: mutable.Map[String, Class[_]] = mutable.Map()

  def registerPaths(): Unit

  def serve(): Unit =
    log("<~~ server started ~~>")
    Using(ServerSocket(port))(serverSocket => while true do SocketProcessor(serverSocket.accept).process())

  @targetName("Register path and its' handler")
  def ++(pathHandlerPair: (String, Class[_])): Unit = mapping += (pathHandlerPair._1 -> pathHandlerPair._2)

class SocketProcessor(val socket: Socket)(using mapping: mutable.Map[String, Class[_]]):

  given conn: Socket = socket

  def process(): Unit =

    val writer = BufferedOutputStream(conn.getOutputStream)
    val httpResponse = HttpResponse(writer, socket)

    val reader = BufferedInputStream(conn.getInputStream)
    val httpRequest = HttpRequest(reader, socket)

    reader.mark(0)

    val requestMethod = mutable.StringBuilder()
    var reqMethodChar = reader.read

    while reqMethodChar != ' ' do
      requestMethod.append(reqMethodChar.asInstanceOf[Char])
      reqMethodChar = reader.read

    val path = mutable.StringBuilder()
    var pathChar = reader.read()

    while pathChar != ' ' do
      path.append(pathChar.asInstanceOf[Char])
      pathChar = reader.read()

    reader.reset()

    val handler = mapping(path.toString).getConstructor(classOf[HttpRequest], classOf[HttpResponse]).newInstance(httpRequest, httpResponse).asInstanceOf[RequestHandler]
    pool.submit(handler)

object SocketProcessor:
  private val pool: ExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime.availableProcessors())


case class HttpRequest(reader: BufferedInputStream, socket: Socket)
case class HttpResponse(writer: BufferedOutputStream, socket: Socket)

