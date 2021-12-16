package com.design.patterns.chain

import scala.io.Source

object ATMExample {

  def main(args: Array[String]): Unit = {
    val atm = new ATM
    printHelp()
    Source.stdin.getLines().foreach(line => processLine(line, atm))
  }

  def printHelp(): Unit = {
    println("Usage")
    println("1. Write an amount to withdraw...")
    println("2. Write EXIT to quit the application")
  }

  def processLine(line: String, atm: ATM): Unit = {
    line match {
      case "EXIT" => println("Bye!")
      System.exit(0)
      case l => try {
        atm.requestMoney(Money(l.toInt))
        println("Thanks!")
      } catch {
        case _: Throwable =>
          System.err.println(s"Invalid input: $l")
          printHelp()
      }
    }
  }

}
