package socket

import request.{HttpRequest, HttpRequestHandler, HttpResponseWriter, RequestHandler}
import socket.SocketProcessor.*
import util.Util.*

import java.io.{BufferedInputStream, BufferedOutputStream, InputStream, OutputStream}
import java.net.Socket
import scala.annotation.tailrec
import scala.collection.mutable

class SocketProcessor(connection: Connection)(using pathHandlerMapping: mutable.Map[String, Class[_]]) extends Runnable :

  final override def run(): Unit =

    val (writer, reader) = (BufferedOutputStream(connection.output), BufferedInputStream(connection.input))
    val (requestMethod, path) = (extractParam(reader, " "), extractParam(reader, " ", "?"))

    val queryParamsAsString = extractParam(reader, " ")

    val queryParams =
      if !queryParamsAsString.startsWith("HTTP") then
        val queryParamsSplitted = queryParamsAsString.split("&")
        queryParamsSplitted.map { arr =>
          val keyValuePair = arr.split("=")
          keyValuePair(0) -> keyValuePair(1)
        }.toMap
      else Map()

    val (httpRequest, httpResponseWriter) = (HttpRequest(reader, requestMethod, connection, queryParams), HttpResponseWriter(writer, connection))

    val requestHandler = pathHandlerMapping.get(path)
      .fold(pathHandlerMapping(NOT_FOUND))(identity)
      .getConstructor(classOf[HttpRequest], classOf[HttpResponseWriter])
      .newInstance(httpRequest, httpResponseWriter).asInstanceOf[HttpRequestHandler]

    requestHandler.handleRequest()

  private def extractParam(in: InputStream, stopStrings: String*): String =

    @tailrec
    def go(acc: String): String =
      val char = in.read().asInstanceOf[Char].toString
      if stopStrings.contains(char) then acc
      else go(acc concat char)

    go("")


object SocketProcessor:

  lazy val NOT_FOUND: String = "resourceNotFound"