package handler

import request.{HttpRequest, HttpRequestHandler, HttpResponse, RequestHandler}
import util.Util.log
import statuscode.StatusCode.*

class NewResourceHandler(val httpRequest: HttpRequest, val httpResponse: HttpResponse) extends HttpRequestHandler(httpRequest, httpResponse) :

  val newLocation: String = "http://localhost:8080/newLocation"

  override def handle(): Unit =
    val data: String = """<h1> New Location </h1>"""
    val response = buildResponse(data)("text/html")(OK)
    httpResponse.write(response)
