package socket

import request.{HttpRequest, HttpRequestHandler, HttpResponse, RequestHandler}
import util.Util.log

import java.io.{BufferedInputStream, BufferedOutputStream}
import java.net.Socket
import scala.collection.mutable
import scala.util.Try

class SocketProcessor(val socket: Socket)(using mapping: mutable.Map[String, Class[_]]) extends Runnable:

  override def run(): Unit =

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

    val instance = mapping.get(path.toString).fold(mapping("resourceNotFound"))(identity)
    val handler = instance.getConstructor(classOf[HttpRequest], classOf[HttpResponse]).newInstance(httpRequest, httpResponse).asInstanceOf[RequestHandler]

    handler.handleRequest()
