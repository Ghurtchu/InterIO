import java.io.BufferedOutputStream
import java.net.Socket
import java.util.concurrent.Executors

object Client:

  val pool = Executors.newFixedThreadPool(Runtime.getRuntime.availableProcessors)

  def main(args: Array[String]): Unit =
    while true do
      Thread.sleep(3000)
        for _ <- 1 to 5 do
            val socket = Socket("localhost", 8080)
            val writer = BufferedOutputStream(socket.getOutputStream)


  def randString: String = scala.util.Random().nextString(5)

