import handler.{AboutHandler, ProductHandler, RootHandler}
import http.AbstractHttpServer
import util.Util.*


object InterIO:

  def main(args: Array[String]): Unit =
    val (host, port) = (args.extractHost, args.extractPort)
    val httpServer = HttpServer(port, host)
    httpServer.serve()


class HttpServer(port: Int, host: String) extends AbstractHttpServer(port, host) :

  final override def registerPaths(): Unit =
    ++("/" ~~> classOf[RootHandler])
    ++("/about" ~~> classOf[AboutHandler])
    ++("/product" ~~> classOf[ProductHandler])
