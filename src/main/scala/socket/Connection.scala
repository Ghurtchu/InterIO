package socket

import java.io.{InputStream, OutputStream}
import java.net.Socket

class Connection(socket: Socket):

  final def close(): Unit = socket.close()

  final def input: InputStream = socket.getInputStream

  final def output: OutputStream = socket.getOutputStream
