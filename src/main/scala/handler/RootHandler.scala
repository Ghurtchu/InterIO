package handler

import request.{HttpRequest, HttpRequestHandler, HttpResponse}
import util.Util.log
import statuscode.StatusCode.*

class RootHandler(val httpRequest: HttpRequest, val httpResponse: HttpResponse) extends HttpRequestHandler(httpRequest, httpResponse) :

  override def handle(): Unit =
    val data: String = """<h1> Home page </h1>"""
    val response = buildResponse(data)("text/html")(OK)
    httpResponse.write(response)
