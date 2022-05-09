package handler

import request.{HttpRequest, HttpRequestHandler, HttpResponse}
import util.Util.log

class HomeHandler(val httpRequest: HttpRequest, val httpResponse: HttpResponse) extends HttpRequestHandler(httpRequest, httpResponse) :

  override def handle(): Unit =
    log(s"handling /${this.getClass.getSimpleName.toLowerCase}")
    val data = Map("language" -> "Scala", "isGreat" -> true)
    val header = buildHeader(data)("application/json")(200)
    httpResponse.write(data)(header)
