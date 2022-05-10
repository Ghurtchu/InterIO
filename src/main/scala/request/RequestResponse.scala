package request

import socket.Connection

import java.io.{BufferedInputStream, BufferedOutputStream}
import java.net.Socket
import java.nio.charset.Charset

sealed trait HttpOperation(val connection: Connection)

case class HttpRequest(in: BufferedInputStream, requestMethod: String, override val connection: Connection) extends HttpOperation(connection) :

  def read(): Unit = println("unimplemented")

case class HttpResponse(out: BufferedOutputStream, override val connection: Connection) extends HttpOperation(connection) :

  def write(response: Array[Byte]): Unit = out.write(response)
