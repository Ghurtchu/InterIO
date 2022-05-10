package handler

import request.{HttpRequest, HttpRequestHandler, HttpResponse}
import util.Util.log
import statuscode.StatusCode.*

class ScalaHandler(val httpRequest: HttpRequest, val httpResponse: HttpResponse) extends HttpRequestHandler(httpRequest, httpResponse) :

  override def handle(): Unit =
    val data = """{"lang": "Scala", "hasBipolarDisorder": true}"""
    val header = buildHeader(data)("application/json")(OK)
    httpResponse.write(data)(header)