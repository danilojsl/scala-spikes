package com.regex

object RegularExpressions {

  def main(args: Array[String]): Unit = {
    val tokens = List("123456", "numbers", "XYZ987")
    val regex = "\\d+"

    val result = tokens.map(token => token.matches(regex))
    val result2 = tokens.map(token => regex.r.findFirstMatchIn(token))

    println("Debugging")
  }

}
