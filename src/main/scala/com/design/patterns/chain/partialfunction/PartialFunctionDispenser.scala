package com.design.patterns.chain.partialfunction

import com.design.patterns.chain.Money

trait PartialFunctionDispenser {

  def dispense(dispenserAmount: Int): PartialFunction[Money, Money] = {
    case Money(amount) if amount >= dispenserAmount =>
      val notes = amount / dispenserAmount
      val left = amount % dispenserAmount
      println(s"Dispensing $notes note/s of $dispenserAmount.")
      Money(left)
    case money @ Money(_) => money
  }

}
