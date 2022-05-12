package entity

import entity.ContentType.*

enum Body(val get: String, val contentType: ContentType):

  case html(override val get: String) extends Body(get, HTML)
    def +(other: html): html = html(get concat "\n" concat other.get)
      
  case json(override val get: String) extends Body(get, JSON)
  case xml(override val get: String) extends Body(get, XML)
  case csv(override val get: String) extends Body(get, CSV)
  case text(override val get: String) extends Body(get, TEXT)
