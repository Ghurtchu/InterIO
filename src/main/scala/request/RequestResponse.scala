package request

import entity.StatusCode.*
import entity.ContentType.*
import entity.{Body, ContentType, StatusCode}
import socket.Connection
import entity.Body.*

import java.io.{BufferedInputStream, BufferedOutputStream}
import java.net.Socket
import java.nio.charset.Charset

sealed trait Http(protected val connection: Connection):
  def close(): Unit = connection.close()

case class HttpRequest(in: BufferedInputStream, requestMethod: String, override protected val connection: Connection) extends Http(connection) :

  def read(): Unit = println("unimplemented")

case class HttpResponseWriter(protected val out: BufferedOutputStream, override protected val connection: Connection) extends Http(connection) :

  final def write(response: Array[Byte]): Unit = out.write(response)
  
  final def flush(): Unit = out.flush()

case class HttpResponse(data: String, contentType: ContentType, statusCode: StatusCode):

  lazy val response: Array[Byte] =
    val dataAsBytes = data.getBytes(Charset.forName("US-ASCII"))
    (s"HTTP/1.1 ${statusCode.code} ${statusCode.value}\r\n"
      + s"Server: InterIO v1.0\r\n"
      + "Content-length: " + dataAsBytes.length + "\r\n"
      + "Content-type: " + contentType.get + "; charset=" + "UTF-8" + "\r\n\r\n"
      + data).getBytes(Charset.forName("US-ASCII"))

object HttpResponse:

  def apply(data: String, contentType: ContentType, statusCode: StatusCode = OK): Array[Byte] = new HttpResponse(data, contentType, statusCode).response

  def apply(body: Body): Array[Byte] = HttpResponse(body.get, body.contentType)

  def apply(body: Body, statusCode: StatusCode): Array[Byte] = HttpResponse(body.get, body.contentType, statusCode)