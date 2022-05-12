package handler

import entity.Body.html
import request.{HttpRequest, HttpRequestHandler, HttpResponse, HttpResponseWriter}


class ProductHandler(val httpRequest: HttpRequest, override val writer: HttpResponseWriter) extends HttpRequestHandler(httpRequest, writer) :

  lazy val defaultProduct = "Banana"
  lazy val defaultPrice = 1.0

  override def handle(): Unit =
    val prodName = httpRequest.queryParams.get("productName").fold(defaultProduct)(identity)
    val price = httpRequest.queryParams.get("price").fold(defaultPrice)(identity)
    val body = html(
      s"<h1> Product name = $prodName </h1>"
        .concat(s"\nProduct price = $price")
    )
    val response = HttpResponse(body)
    writer.write(response)