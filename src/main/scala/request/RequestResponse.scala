package request

import socket.Connection

import java.io.{BufferedInputStream, BufferedOutputStream}
import java.net.Socket
import java.nio.charset.Charset

sealed trait HttpOperation(val connection: Connection)

case class HttpRequest(in: BufferedInputStream, override val connection: Connection) extends HttpOperation(connection) :

  def read(): Unit = println("unimplemented")

case class HttpResponse(out: BufferedOutputStream, override val connection: Connection) extends HttpOperation(connection) :

  def write(data: Array[Byte])(header: Array[Byte]): Unit =
    out.write(header)
    out.write(data)

  def write(data: String)(header: Array[Byte]): Unit = write(data.getBytes(Charset.forName("US-ASCII")))(header)
