package socket

import java.io.{InputStream, OutputStream}
import java.net.Socket

class Connection(socket: Socket):
  def close(): Unit = socket.close()

  def in: InputStream = socket.getInputStream

  def out: OutputStream = socket.getOutputStream
