import Util.log

import java.nio.charset.Charset

trait RequestHandler extends Runnable:
  override def run(): Unit = handle()

  def handle(): Unit

abstract class HttpRequestHandler protected(val request: HttpRequest, val response: HttpResponse) extends RequestHandler:

  log(s"handling request for ${getClass.getSimpleName.toLowerCase}")

  def handle(): Unit

  protected def buildHeaderByData(data: Array[Byte]): Array[Byte] =
    ("HTTP/1.1 200 OK\r\n"
      + "Server: deliverIO 2.0\r\n"
      + "Content-length: " + data.length + "\r\n"
      + "Content-type: " + "text/html" + "; charset=" + "UTF-8" + "\r\n\r\n").getBytes(Charset.forName("US-ASCII"))
