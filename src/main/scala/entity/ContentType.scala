package entity

enum ContentType(val get: String):

  case HTML extends ContentType("text/html")
  case CSV extends ContentType("text/csv")
  case TEXT extends ContentType("text/plain")
  case JSON extends ContentType("application/json")
  case XML extends ContentType("application/xml")
