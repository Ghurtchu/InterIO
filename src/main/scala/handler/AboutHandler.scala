package handler

import entity.Body.*
import entity.ContentType.*
import entity.StatusCode.*
import request.*
import util.Util.log


class AboutHandler(val httpRequest: HttpRequest, override val writer: HttpResponseWriter) extends HttpRequestHandler(httpRequest, writer) :
  
  override def handle(): Unit =

    val httpMethod = httpRequest.requestMethod

    httpMethod match

      case "GET" =>

        val body = html(
          """
            |<h1> InterIO 1.0 </h1>
            |<p> A simple multithreaded http server </p>
            |""".stripMargin)

        val response = HttpResponse(body)
        
        writer.write(response)

      case "POST" =>

        val body = json("""{"path": "/about", "method": "POST", "response": "Scala rocks!"}""")
        val response = HttpResponse(body)
        writer.write(response)

      case "PUT" => println("unimplemented")

      case "DELETE" => println("unimplemented")
