package http

import handler.{HomeHandler, RootHandler}
import http.AbstractHttpServer
import request.{HttpRequest, HttpRequestHandler, HttpResponse}
import util.Util.{log, ~~>}

class HttpServer private(port: Int, host: String) extends AbstractHttpServer(port, host) :

  override final def registerPaths(): Unit =
    ++("/" ~~> classOf[RootHandler])
    ++("/home" ~~> classOf[HomeHandler])

object HttpServer:
  def apply(port: Int, host: String): AbstractHttpServer = new HttpServer(port, host)

