package request

import request.{HttpRequest, HttpResponse}
import util.Util.log

import java.net.Socket
import java.nio.charset.Charset
import scala.util.Using

trait RequestHandler extends Runnable:
  final override def run(): Unit = handleRequest()

  def handleRequest(): Unit

abstract class HttpRequestHandler protected(val request: HttpRequest, val response: HttpResponse) extends RequestHandler:

  log(s"handling request for ${getClass.getSimpleName.toLowerCase}")

  final override def handleRequest(): Unit =
    handle()
    response.out.flush()
    request.connection.close()

  def handle(): Unit

  protected def buildHeader(data: String)(contentType: String)(statusCode: Int): Array[Byte] =
    val dataAsBytes = data.getBytes(Charset.forName("US-ASCII"))
    (s"HTTP/1.1 $statusCode OK\r\n"
      + s"Server: InterIO v1.0\r\n"
      + "Content-length: " + dataAsBytes.length + "\r\n"
      + "Content-type: " + contentType + "; charset=" + "UTF-8" + "\r\n\r\n").getBytes(Charset.forName("US-ASCII"))

  protected def buildHeader(data: Map[String, Matchable])(contentType: String)(statusCode: Int): Array[Byte] = buildHeader(data.toString())(contentType)(statusCode)