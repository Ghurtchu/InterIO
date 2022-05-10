package request

import entity.StatusCode
import request.{HttpRequest, HttpResponseWriter}
import util.Util.log

import java.net.Socket
import java.nio.charset.Charset
import scala.util.Using

trait RequestHandler:
  def handleRequest(): Unit

abstract class HttpRequestHandler protected(val request: HttpRequest, val response: HttpResponseWriter) extends RequestHandler :

  lazy val requestHandler: String = getClass.getSimpleName
  log(s"$requestHandler received HTTP request")

  final override def handleRequest(): Unit =
    handle()
    response.out.flush()
    request.connection.close()
    log(s"$requestHandler sent back HTTP response")

  def handle(): Unit