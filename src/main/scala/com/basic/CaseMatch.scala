package com.basic

object CaseMatch {

  def main(args: Array[String]): Unit = {
    val value = 10
    args.head match {
      case "x" if value > 100 => println("Value greater than 100")
      case "y" => println("Some value")
    }

  }

}
