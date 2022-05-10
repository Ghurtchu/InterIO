package handler

import request.{HttpRequest, HttpRequestHandler, HttpResponse, RequestHandler}
import util.Util.log
import statuscode.StatusCode.*

class AboutHandler(val httpRequest: HttpRequest, val httpResponse: HttpResponse) extends HttpRequestHandler(httpRequest, httpResponse) :

  override def handle(): Unit =

    val httpMethod = httpRequest.requestMethod

    httpMethod match
      case "GET" =>

        val data =
          """
            |<h1> About page GET request </h1>
            |<p> paragraph1 </p>
            |<p> paragraph2 </p>
            |""".stripMargin
        val response = buildResponse(data)("text/html")(OK)
        httpResponse.write(response)

      case "POST" =>

        val data = """{"path": "/about", "method": "POST", "response": "Scala rocks!"}"""

        val response = buildResponse(data)("application/json")(OK)
        httpResponse.write(response)

      case "PUT" => println("unimplemented")

      case "DELETE" => println("unimplemented")
