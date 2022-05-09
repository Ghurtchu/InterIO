package socket

import request.{HttpRequest, HttpRequestHandler, HttpResponse, RequestHandler}
import util.Util.log

import java.io.{BufferedInputStream, BufferedOutputStream, InputStream, OutputStream}
import java.net.Socket
import scala.collection.mutable
import scala.util.Try

class Connection(socket: Socket):
  def close(): Unit = socket.close()
  def in: InputStream = socket.getInputStream
  def out: OutputStream = socket.getOutputStream

class SocketProcessor(connection: Connection)(using mapping: mutable.Map[String, Class[_]]) extends Runnable:

  override def run(): Unit =

    val writer = BufferedOutputStream(connection.out)
    val httpResponse = HttpResponse(writer, connection)

    val reader = BufferedInputStream(connection.in)
    val httpRequest = HttpRequest(reader, connection)

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

    val instance = mapping.get(path.toString).fold(mapping("resourceNotFound"))(identity)
    val handler = instance.getConstructor(classOf[HttpRequest], classOf[HttpResponse]).newInstance(httpRequest, httpResponse).asInstanceOf[RequestHandler]

    handler.handleRequest()
