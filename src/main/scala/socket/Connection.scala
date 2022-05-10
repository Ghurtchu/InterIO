package socket

import java.io.{InputStream, OutputStream}
import java.net.Socket

class Connection(socket: Socket):

  final def close(): Unit = socket.close()

  final def in: InputStream = socket.getInputStream

  final def out: OutputStream = socket.getOutputStream
