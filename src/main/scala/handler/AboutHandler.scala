package handler

import request.{HttpRequest, HttpRequestHandler, HttpResponse, RequestHandler}
import util.Util.log

class AboutHandler(val httpRequest: HttpRequest, val httpResponse: HttpResponse) extends HttpRequestHandler(httpRequest, httpResponse) :

  override def handle(): Unit =
    val data =
      """
        |<h1> About page</h1>
        |<p> paragraph1 </p>
        |<p> paragraph2 </p>
        |""".stripMargin
    val header = buildHeader(data)("text/html")(200)
    httpResponse.write(data)(header)
