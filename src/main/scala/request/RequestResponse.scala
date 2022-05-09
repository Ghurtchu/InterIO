package request

import java.io.{BufferedInputStream, BufferedOutputStream}
import java.net.Socket
import java.nio.charset.Charset

class HttpRequest(in: BufferedInputStream, connection: Socket):

  private lazy val reader = in
  private lazy val socket = connection

  def getWriter: BufferedInputStream = reader

  def getConnection: Socket = socket

  def read(): Unit = println()

class HttpResponse(out: BufferedOutputStream, connection: Socket):

  private lazy val writer = out
  private lazy val socket = connection
  
  
  def getWriter: BufferedOutputStream = writer
  
  def getConnection: Socket = socket
  
  def write(data: Array[Byte])(header: Array[Byte]): Unit =
    writer.write(header)
    writer.write(data)

  def write(data: String)(header: Array[Byte]): Unit = write(data.getBytes(Charset.forName("US-ASCII")))(header)

  def write(data: Map[String, Matchable])(header: Array[Byte]): Unit = write(data.toString.getBytes(Charset.forName("US-ASCII")))(header)