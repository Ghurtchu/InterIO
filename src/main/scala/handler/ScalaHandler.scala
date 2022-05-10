package handler

import request.{HttpRequest, HttpRequestHandler, HttpResponseWriter, HttpResponse}
import util.Util.log
import entity.StatusCode.*
import entity.ContentType.*
import entity.Body.*


class ScalaHandler(val httpRequest: HttpRequest, override val writer: HttpResponseWriter) extends HttpRequestHandler(httpRequest, writer) :

  override def handle(): Unit =
    val body = json("""{"lang": "Scala", "hasBipolarDisorder": true}""")
    val response = HttpResponse(body)
    writer.write(response)
