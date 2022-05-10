package handler

import entity.Body.*
import entity.ContentType.*
import entity.StatusCode.*
import request.{HttpRequest, HttpRequestHandler, HttpResponse, HttpResponseWriter}
import util.Util.log


class ScalaHandler(val httpRequest: HttpRequest, override val writer: HttpResponseWriter) extends HttpRequestHandler(httpRequest, writer) :

  override def handle(): Unit =
    val body = json("""{"lang": "Scala", "hasBipolarDisorder": true}""")
    val response = HttpResponse(body)
    writer.write(response)
