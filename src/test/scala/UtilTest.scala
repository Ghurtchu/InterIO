import Util._

class UtilTest extends org.scalatest.funsuite.AnyFunSuite {

  test("StringToIntOptionConverter None case") {
    assert("gibberish".toOption.isEmpty)
  }

  test("StringToIntOptionConverter Some case") {
    assert("5".toOption.contains(5))
  }

  test("StringToIntOptionConverter empty string case") {
    assert(Array("1", "2", "3").getOrEmpty(100) == "")
    assert(Array("1", "2", "3").getOrEmpty(-1) == "")
    assert(Array("1", "2", "3").getOrEmpty(3) == "")
  }

  test("StringToIntOptionConverter nonempty string case") {
    assert(Array("1", "2", "3").getOrEmpty(0) == "1")
    assert(Array("1", "2", "3").getOrEmpty(1) == "2")
    assert(Array("1", "2", "3").getOrEmpty(2) == "3")
  }

}
