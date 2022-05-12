package entity

import entity.ContentType.*

enum Body(val get: String, val contentType: ContentType):

  case html(override val get: String) extends Body(get, HTML)
  case json(override val get: String) extends Body(get, JSON)
  case xml(override val get: String) extends Body(get, XML)
  case csv(override val get: String) extends Body(get, CSV)
  case text(override val get: String) extends Body(get, TEXT)

  def +(other: Body): Body = other match
    case json(body) => json((get concat body).replace("}{", ",\n"))
    case html(body) => html(get concat "\n" concat body)
    case e: Body => e