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

  log("<~~ server started ~~>")

  given mapping: mutable.Map[String, Class[_]] = mutable.Map()
  private val pool = Executors.newFixedThreadPool(Runtime.getRuntime.availableProcessors())

  def registerPaths(): Unit

  def serve(): Unit = Using(ServerSocket(port))(serverSocket => while true do pool.submit(SocketProcessor(serverSocket.accept)))

  @targetName("Register path and the handler class for the path")
  def ++(pathHandlerPair: (String, Class[_])): Unit = mapping += (pathHandlerPair._1 -> pathHandlerPair._2)

class SocketProcessor(val socket: Socket)(using mapping: mutable.Map[String, Class[_]]) extends Runnable:

  override def run(): Unit = process()

  def process(): Unit =

    val writer = BufferedOutputStream(socket.getOutputStream)
    val httpResponse = HttpResponse(writer, socket)

    val reader = BufferedInputStream(socket.getInputStream)
    val httpRequest = HttpRequest(reader, socket)

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

    val handler = mapping(path.toString).getConstructor(classOf[HttpRequest], classOf[HttpResponse])
      .newInstance(httpRequest, httpResponse)
      .asInstanceOf[RequestHandler]

    handler.handleRequest()



case class HttpRequest(reader: BufferedInputStream, socket: Socket):
  def read(): Unit = println()

case class HttpResponse(writer: BufferedOutputStream, socket: Socket):

  def write(data: Array[Byte])(header: Array[Byte]): Unit = 
    writer.write(header)
    writer.write(data)

  def write(data: String)(header: Array[Byte]): Unit = write(data.getBytes(Charset.forName("US-ASCII")))(header)


