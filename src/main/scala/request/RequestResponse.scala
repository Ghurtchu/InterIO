package request

import socket.Connection

import java.io.{BufferedInputStream, BufferedOutputStream}
import java.net.Socket
import java.nio.charset.Charset

enum HttpOperation(val connection: Connection):

  case HttpRequest(in: BufferedInputStream, override val connection: Connection)
  case HttpResponse(out: BufferedOutputStream, override val connection: Connection)

  def read(): Unit = println("unimplemented")

  def write(data: Array[Byte])(header: Array[Byte]): Unit =
    out.write(header)
    out.write(data)

  def write(data: String)(header: Array[Byte]): Unit = write(data.getBytes(Charset.forName("US-ASCII")))(header)

  def write(data: Map[String, Matchable])(header: Array[Byte]): Unit = write(data.toString.getBytes(Charset.forName("US-ASCII")))(header)
