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

    val (writer, reader) = (BufferedOutputStream(connection.output), BufferedInputStream(connection.input))
    val (requestMethod, path) = (extractParam(reader), extractParam(reader))
    val (httpRequest, httpResponseWriter) = (HttpRequest(reader, requestMethod, connection), HttpResponseWriter(writer, connection))

    val requestHandler = mapping.get(path)
      .fold(mapping("resourceNotFound"))(identity)
      .getConstructor(classOf[HttpRequest], classOf[HttpResponseWriter])
      .newInstance(httpRequest, httpResponseWriter).asInstanceOf[RequestHandler]

    requestHandler.handleRequest()

  private def extractParam(in: InputStream): String =

    @tailrec
    def go(acc: String): String =
      val char = in.read()
      if char == ' ' then acc
      else go(acc concat char.asInstanceOf[Char].toString)

    go("")

