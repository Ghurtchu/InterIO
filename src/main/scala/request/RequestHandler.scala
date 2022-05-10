package request

import request.{HttpRequest, HttpResponse}
import statuscode.StatusCode
import util.Util.log

import java.net.Socket
import java.nio.charset.Charset
import scala.util.Using

trait RequestHandler extends Runnable :
  final override def run(): Unit = handleRequest()

  def handleRequest(): Unit

abstract class HttpRequestHandler protected(val request: HttpRequest, val response: HttpResponse) extends RequestHandler :

  lazy val requestHandler: String = getClass.getSimpleName
  log(s"$requestHandler received HTTP request")

  final override def handleRequest(): Unit =
    handle()
    response.out.flush()
    request.connection.close()
    log(s"$requestHandler sent back HTTP response")

  def handle(): Unit

  protected def buildResponse(data: String)(contentType: String)(statusCode: StatusCode): Array[Byte] =
    val dataAsBytes = data.getBytes(Charset.forName("US-ASCII"))
    (s"HTTP/1.1 ${statusCode.code} ${statusCode.value}\r\n"
      + s"Server: InterIO v1.0\r\n"
      + "Content-length: " + dataAsBytes.length + "\r\n"
      + "Content-type: " + contentType + "; charset=" + "UTF-8" + "\r\n\r\n"
      + data).getBytes(Charset.forName("US-ASCII"))

  protected def buildHeader(data: Map[String, Matchable])(contentType: String)(statusCode: StatusCode): Array[Byte] = buildResponse(data.toString())(contentType)(statusCode)