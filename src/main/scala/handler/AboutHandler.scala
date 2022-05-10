package handler

import request.{HttpRequest, HttpRequestHandler, HttpResponseWriter, RequestHandler, HttpResponse}
import util.Util.log
import entity.StatusCode.*
import entity.ContentType.*

class AboutHandler(val httpRequest: HttpRequest, val writer: HttpResponseWriter) extends HttpRequestHandler(httpRequest, writer) :

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
        val response = HttpResponse(data, HTML, OK)  
        writer.write(response)

      case "POST" =>
      
        val data = """{"path": "/about", "method": "POST", "response": "Scala rocks!"}"""
        val response = HttpResponse(data, JSON, OK)
        writer.write(response)

      case "PUT" => println("unimplemented")

      case "DELETE" => println("unimplemented")
