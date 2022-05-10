package header

enum ContentType(val get: String):

  case HTML extends ContentType("text/html")
  case CSV extends ContentType("text/csv")
  case PDF extends ContentType("application/pdf")
  case TEXT extends ContentType("text/plain")
  case JSON extends ContentType("application/json")
  case XML extends ContentType("application/xml")
