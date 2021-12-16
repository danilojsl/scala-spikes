package com.design.patterns.chain.partialfunction

import com.design.patterns.chain.Money


class PartialFunctionATM extends PartialFunctionDispenser {

  val dispenser: PartialFunction[Money, Money]  = dispense(50)
    .andThen(dispense(20))
    .andThen(dispense(10))
    .andThen(dispense(5))

  def requestMoney(money: Money): Unit = {
    if (money.amount % 5 != 0) {
      System.err.println("The smallest nominal is 5 and we cannot satisfy your request.")
    } else {
      dispenser(money)
    }
  }

}
