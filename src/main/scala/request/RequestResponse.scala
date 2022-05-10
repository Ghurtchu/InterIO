package request

import socket.Connection
import header.{ContentType, StatusCode}

import java.io.{BufferedInputStream, BufferedOutputStream}
import java.net.Socket
import java.nio.charset.Charset

sealed trait Http(val connection: Connection)

case class HttpRequest(in: BufferedInputStream, requestMethod: String, override val connection: Connection) extends Http(connection) :

  def read(): Unit = println("unimplemented")

case class HttpResponseWriter(out: BufferedOutputStream, override val connection: Connection) extends Http(connection) :

  def write(response: Array[Byte]): Unit = out.write(response)

case class HttpResponse(data: String, contentType: ContentType, statusCode: StatusCode):

  lazy val response: Array[Byte] =
    val dataAsBytes = data.getBytes(Charset.forName("US-ASCII"))
    (s"HTTP/1.1 ${statusCode.code} ${statusCode.value}\r\n"
      + s"Server: InterIO v1.0\r\n"
      + "Content-length: " + dataAsBytes.length + "\r\n"
      + "Content-type: " + contentType.get + "; charset=" + "UTF-8" + "\r\n\r\n"
      + data).getBytes(Charset.forName("US-ASCII")) 
    
object HttpResponse:
    
  def apply(data: String, contentType: ContentType, statusCode: StatusCode): Array[Byte] = new HttpResponse(data, contentType, statusCode).response
    
    