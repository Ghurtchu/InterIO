import java.io.{BufferedInputStream, BufferedOutputStream}
import java.net.Socket
import java.nio.charset.Charset

case class HttpRequest(reader: BufferedInputStream, socket: Socket):
  def read(): Unit = println()

case class HttpResponse(writer: BufferedOutputStream, socket: Socket):

  def write(data: Array[Byte])(header: Array[Byte]): Unit =
    writer.write(header)
    writer.write(data)

  def write(data: String)(header: Array[Byte]): Unit = write(data.getBytes(Charset.forName("US-ASCII")))(header)