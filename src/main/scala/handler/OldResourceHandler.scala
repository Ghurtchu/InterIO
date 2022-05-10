package handler

import request.{HttpRequest, HttpRequestHandler, HttpResponseWriter, RequestHandler, HttpResponse}
import util.Util.log
import header.StatusCode.*
import header.ContentType.*

class OldResourceHandler(val httpRequest: HttpRequest, val writer: HttpResponseWriter) extends HttpRequestHandler(httpRequest, writer) :

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
    val response = HttpResponse(data, HTML, PermanentRedirect)
    writer.write(response)
