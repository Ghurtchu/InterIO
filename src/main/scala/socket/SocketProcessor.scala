package socket

import request.{HttpRequest, HttpRequestHandler, HttpResponseWriter, RequestHandler}
import util.Util.log

import java.io.{BufferedInputStream, BufferedOutputStream, InputStream, OutputStream}
import java.net.Socket
import scala.annotation.tailrec
import scala.collection.mutable
import scala.util.Try

class SocketProcessor(connection: Connection)(using mapping: mutable.Map[String, Class[_]]) extends Runnable :

  final override def run(): Unit =

    val writer = BufferedOutputStream(connection.output)
    val reader = BufferedInputStream(connection.input)

    val requestMethod = extractParam(reader)

    val httpRequest = HttpRequest(reader, requestMethod, connection)
    val httpResponse = HttpResponseWriter(writer, connection)

    val path = extractParam(reader)

    val handler = mapping.get(path)
      .fold(mapping("resourceNotFound"))(identity)
      .getConstructor(classOf[HttpRequest], classOf[HttpResponseWriter])
      .newInstance(httpRequest, httpResponse).asInstanceOf[RequestHandler]

    handler.handleRequest()

  private def extractParam(in: InputStream): String =

    @tailrec
    def go(acc: String): String =
      val char = in.read()
      if char == ' ' then acc
      else go(acc concat char.asInstanceOf[Char].toString)

    go("")

