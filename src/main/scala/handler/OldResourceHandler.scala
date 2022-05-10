package handler

import request.{HttpRequest, HttpRequestHandler, HttpResponse, RequestHandler}
import util.Util.log
import statuscode.StatusCode.*

class OldResourceHandler(val httpRequest: HttpRequest, val httpResponse: HttpResponse) extends HttpRequestHandler(httpRequest, httpResponse) :

  val newLocation: String = "http://localhost:8080/newLocation"

  override def handle(): Unit =
    val data: String =
      s"""
        |<head> <meta http-equiv="redirect" content="0; url='$newLocation'"/> </head>
        |<body>
        |  <h1> Resource moved </h1>
        |  <p>Please follow <a href="$newLocation">this link</a>.</p>
        |</body>
        |""".stripMargin
    val response = buildResponse(data)("text/html")(PermanentRedirect)
    httpResponse.write(response)
