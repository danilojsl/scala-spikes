package com.design.patterns.chain

/** Code example for Chain of Responsibility */

trait Dispenser {

  val amount: Int
  val next: Option[Dispenser]

  def dispense(money: Money): Unit = {
    if (money.amount >= amount) {
      val notes = money.amount /amount
      val left = money.amount % amount
      println(s"Dispensing $notes note/s of $amount.")
      if (left > 0) next.map(_.dispense(Money(left)))
    } else next.foreach(_.dispense(money))
  }

}
