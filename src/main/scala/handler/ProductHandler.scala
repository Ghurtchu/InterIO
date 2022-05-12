package handler

import entity.Body.html
import handler.ProductHandler.{defaultPrice, defaultProduct, productStorage}
import request.{HttpRequest, HttpRequestHandler, HttpResponse, HttpResponseWriter}

import scala.collection.mutable
import scala.util.Try


class ProductHandler(val httpRequest: HttpRequest, override val writer: HttpResponseWriter) extends HttpRequestHandler(httpRequest, writer) :

  override def handle(): Unit =
    val requestMethod = httpRequest.requestMethod
    val queryParams = httpRequest.queryParams

    requestMethod match
      case "GET" =>
        val maybeProdName = queryParams get "productName"
        maybeProdName match
          case Some(name) =>
            val prodPrice = productStorage.get(name)
            prodPrice match
              case Some(price) =>
                val body = html(s"<h1> Request has been processed successfully")
                  + html(s"<h2> Result: </h2>")
                  + html(s"<h3> Requested product name => $name </h3>")
                  + html(s"<h3> Requested product price => $price </h3>")
                val response = HttpResponse(body)
                writer write response
              case _ =>
                val response = HttpResponse(html(s"<h1> Product with name $name does not exist"))
                writer write response
          case _ =>
            val body = html(s"<h1> Malformed URL...")
            val response = HttpResponse(body)
            writer write response

      case "POST" =>
        val maybeProdName = queryParams get "productName"
        val maybeProdPrice = queryParams get "productPrice"
        val (prodName, prodPrice) = (maybeProdName, maybeProdPrice).fold(defaultProduct, defaultPrice)((a, b) => (a, b))
        if productStorage.isDefinedAt(prodName) then
          val response = HttpResponse(html(s"<h1> Product with name $prodName already exists"))
          writer write response
        else
          productStorage += (prodName -> prodPrice)
          val body = html(s"<h2> The following product has been saved in product storage: </h2>")
          + html(s"<h3> product name => $prodName </h3>")
          + html(s"<h3> product price => $prodPrice </h3>")
          val response = HttpResponse(body)
          writer.write(response)


object ProductHandler:
  lazy val defaultProduct = "Scala T shirt"
  lazy val defaultPrice = "20"

  lazy val productStorage: mutable.Map[String, String] = mutable.Map(
    defaultPrice -> defaultPrice,
    "Banana" -> "1.0",
    "Chocolate" -> "2.0",
    "Protein shake" -> "5.5"
  )

extension(tupleOfOptionStrings: (Option[String], Option[String]))
  def fold(ifEmpty: => (String, String))(f: (String, String) => (String, String)): (String, String) =
    tupleOfOptionStrings match
      case (Some(first), Some(second)) => f(first, second)
      case _ => ifEmpty
