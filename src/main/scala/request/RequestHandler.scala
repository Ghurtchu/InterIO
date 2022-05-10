package request

import entity.StatusCode
import request.{HttpRequest, HttpResponseWriter}
import util.Util.log

import java.net.Socket
import java.nio.charset.Charset
import scala.util.Using

trait RequestHandler:
  def handleRequest(): Unit

abstract class HttpRequestHandler protected(val request: HttpRequest, val writer: HttpResponseWriter) extends RequestHandler :

  def requestHandler: String = getClass.getSimpleName

  final override def handleRequest(): Unit =
    log(s"$requestHandler received HTTP request")
    handle()
    writer.out.flush()
    request.connection.close()
    log(s"$requestHandler sent back HTTP response")

  def handle(): Unit