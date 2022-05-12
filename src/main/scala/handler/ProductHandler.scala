package handler

import entity.Body.html
import handler.ProductHandler.{defaultPrice, defaultProduct}
import request.{HttpRequest, HttpRequestHandler, HttpResponse, HttpResponseWriter}

import scala.util.Try


class ProductHandler(val httpRequest: HttpRequest, override val writer: HttpResponseWriter) extends HttpRequestHandler(httpRequest, writer) :

  override def handle(): Unit =
    val queryParams = httpRequest.queryParams
    val maybeProdName = queryParams get "productName"
    val maybeProdPrice = queryParams get "productPrice"
    val (prodName, prodPrice) = (maybeProdName, maybeProdPrice).fold(defaultProduct, defaultPrice)((a, b) => (a, b))
    val body = html(s"<h1> Request has been processed successfully")
      + html(s"<h2> Result: </h2>")
      + html(s"<h3> Requested product name => $prodName </h3>")
      + html(s"<h3> Requested product price => $prodPrice </h3>")
    val response = HttpResponse(body)
    writer.write(response)

object ProductHandler:
  lazy val defaultProduct = "Scala T shirt"
  lazy val defaultPrice = "20"

extension(tupleOfOptionStrings: (Option[String], Option[String]))
  def fold(ifEmpty: => (String, String))(f: (String, String) => (String, String)): (String, String) =
    tupleOfOptionStrings match
      case (Some(first), Some(second)) => f(first, second)
      case _ => ifEmpty