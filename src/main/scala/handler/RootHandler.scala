package handler

import request.{HttpRequest, HttpRequestHandler, HttpResponseWriter, HttpResponse}
import util.Util.log
import header.StatusCode.*
import header.ContentType.*

class RootHandler(val httpRequest: HttpRequest, val writer: HttpResponseWriter) extends HttpRequestHandler(httpRequest, writer) :

  override def handle(): Unit =
    val data: String = """<h1> Home page </h1>"""
    val response = HttpResponse(data, HTML, OK)
    writer.write(response)
