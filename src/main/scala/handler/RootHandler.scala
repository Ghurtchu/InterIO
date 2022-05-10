package handler

import entity.Body.*
import entity.ContentType.*
import entity.StatusCode.*
import request.{HttpRequest, HttpRequestHandler, HttpResponse, HttpResponseWriter}
import util.Util.log

class RootHandler(val httpRequest: HttpRequest, override val writer: HttpResponseWriter) extends HttpRequestHandler(httpRequest, writer) :

  override def handle(): Unit =
    val body = html("<h1> InterIO 1.0 </h1>")
    val response = HttpResponse(body)
    writer.write(response)
