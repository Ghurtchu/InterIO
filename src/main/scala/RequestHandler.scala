import Util.log

import java.nio.charset.Charset
import scala.util.Using

trait RequestHandler extends Runnable:
  final override def run(): Unit = handleRequest()

  def handleRequest(): Unit

abstract class HttpRequestHandler protected(val request: HttpRequest, val response: HttpResponse) extends RequestHandler:

  log(s"handling request for ${getClass.getSimpleName.toLowerCase}")

  final override def handleRequest(): Unit =
    handle()
    response.writer.flush()
    request.socket.close()

  def handle(): Unit

  protected def buildHeader(data: String)(contentType: String): Array[Byte] =
    val dataAsBytes = data.getBytes(Charset.forName("US-ASCII"))
    ("HTTP/1.1 200 OK\r\n"
      + "Server: deliverIO 2.0\r\n"
      + "Content-length: " + dataAsBytes.length + "\r\n"
      + "Content-type: " + contentType + "; charset=" + "UTF-8" + "\r\n\r\n").getBytes(Charset.forName("US-ASCII"))
