package handler

import request.{HttpRequest, HttpRequestHandler, HttpResponseWriter, HttpResponse}
import util.Util.log
import entity.StatusCode.*
import entity.ContentType.*

class ScalaHandler(val httpRequest: HttpRequest, val writer: HttpResponseWriter) extends HttpRequestHandler(httpRequest, writer) :

  override def handle(): Unit =
    val data: String = """{"lang": "Scala", "hasBipolarDisorder": true}"""
    val response = HttpResponse(data, JSON, OK)
    writer.write(response)
