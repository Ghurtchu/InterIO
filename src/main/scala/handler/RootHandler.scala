package handler

import request.{HttpRequest, HttpRequestHandler, HttpResponseWriter, HttpResponse}
import util.Util.log
import entity.StatusCode.*
import entity.ContentType.*
import entity.Body.*

class RootHandler(val httpRequest: HttpRequest, override val writer: HttpResponseWriter) extends HttpRequestHandler(httpRequest, writer) :

  override def handle(): Unit =
    val body = html("<h1> InterIO 1.0 </h1>")
    val response = HttpResponse(body)
    writer.write(response)
