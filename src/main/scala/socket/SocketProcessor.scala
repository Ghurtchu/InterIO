package socket

import request.{HttpRequest, HttpRequestHandler, HttpResponseWriter, RequestHandler}
import util.Util.log

import java.io.{BufferedInputStream, BufferedOutputStream, InputStream, OutputStream}
import java.net.Socket
import scala.collection.mutable
import scala.util.Try

class SocketProcessor(connection: Connection)(using mapping: mutable.Map[String, Class[_]]) extends Runnable :

  final override def run(): Unit =

    val writer = BufferedOutputStream(connection.out)
    val httpResponse = HttpResponseWriter(writer, connection)

    val reader = BufferedInputStream(connection.in)

    val requestMethod = mutable.StringBuilder()
    var reqMethodChar = reader.read

    while reqMethodChar != ' ' do
      requestMethod.append(reqMethodChar.asInstanceOf[Char])
      reqMethodChar = reader.read

    val httpRequest = HttpRequest(reader, requestMethod.toString, connection)

    val path = mutable.StringBuilder()
    var pathChar = reader.read()

    while pathChar != ' ' do
      path.append(pathChar.asInstanceOf[Char])
      pathChar = reader.read()

    val handler = mapping.get(path.toString)
      .fold(mapping("resourceNotFound"))(identity)
      .getConstructor(classOf[HttpRequest], classOf[HttpResponseWriter])
      .newInstance(httpRequest, httpResponse).asInstanceOf[RequestHandler]

    handler.handleRequest()
