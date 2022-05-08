package http

import http.AbstractHttpServer
import request.{HttpRequest, HttpRequestHandler, HttpResponse}
import util.Util.{log, ~~>}

class HttpServer(port: Int, host: String) extends AbstractHttpServer(port, host) :

  override final def registerPaths(): Unit =
    ++("/" ~~> classOf[Root])
    ++("/home" ~~> classOf[Home])


class Root(val httpRequest: HttpRequest, val httpResponse: HttpResponse) extends HttpRequestHandler(httpRequest, httpResponse) :

  override def handle(): Unit =
    log(s"handling /${this.getClass.getSimpleName.toLowerCase}")
    val data: String = """<h1> http.Home page </h1>"""
    val header = buildHeader(data)("text/html")
    httpResponse.write(data)(header)

class Home(val httpRequest: HttpRequest, val httpResponse: HttpResponse) extends HttpRequestHandler(httpRequest, httpResponse) :

  override def handle(): Unit =
    log(s"handling /${this.getClass.getSimpleName.toLowerCase}")
    val data: String = """{"language": "Scala", "isGreat": true"}"""
    val header = buildHeader(data)("application/json")
    httpResponse.write(data)(header)