package handler

import request.{HttpRequest, HttpRequestHandler, HttpResponseWriter, RequestHandler, HttpResponse}
import util.Util.log
import header.StatusCode
import header.StatusCode.*
import header.ContentType.*

import java.nio.charset.Charset

class NewResourceHandler(val httpRequest: HttpRequest, val writer: HttpResponseWriter) extends HttpRequestHandler(httpRequest, writer) :

  val newLocation: String = "http://localhost:8080/newLocation"

  override def handle(): Unit =
    val data: String = """<h1> New Location </h1>"""
    val httpResponse = HttpResponse(data, HTML, OK)
    writer.write(httpResponse)

