package com.benchmarks

object RegexVsMap {

  val regex = "\\d+"
  val regexCache = Map("123456&\\d+" -> "123456", "XYZ987&\\d+" -> "987")

  def main(args: Array[String]): Unit = {
    val tokens = List("123456", "numbers", "XYZ987")

    var t0 = System.nanoTime()

    for (i <- 1 to 1000) {
      findRegexMatch(tokens)
    }
    var t1 = System.nanoTime()
    println("Elapsed time Regex: " + (t1 - t0) / 1e6d + " ms")

    t0 = System.nanoTime()
    for (i <- 1 to 1000) {
      findRegexCacheMatch(tokens)
    }
    t1 = System.nanoTime()
    println("Elapsed time CacheRegex: " + (t1 - t0) / 1e6d  + " ms")
  }

  def findRegexMatch(tokens: List[String]): Unit = {
    tokens.map(token => regex.r.findFirstMatchIn(token))
  }

  def findRegexCacheMatch(tokens: List[String]): Unit = {
    tokens.map(token => regexCache.getOrElse(s"$token&$regex", ""))
  }

}
