package statuscode

enum StatusCode(val value: String, val code: Int):

  case OK extends StatusCode("OK", 200)
  case PermanentRedirect extends StatusCode("Permanent Redirect", 300)
  case BadRequest extends StatusCode("Bad Request", 400)
  case NotFound extends StatusCode("Not Found", 404)
  case InternalServerError extends StatusCode("Internal Server Error", 500)
