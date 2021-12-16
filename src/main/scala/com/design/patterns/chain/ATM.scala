package com.design.patterns.chain

class ATM {

  val dispenser: Dispenser = {
    //Building the dispenser chain
    val dispenser1 = new Dispenser5(None)
    val dispenser2 = new Dispenser10(Some(dispenser1))
    val dispenser3 = new Dispenser20(Some(dispenser2))
    new Dispenser50(Some(dispenser3))
  }

  def requestMoney(money: Money): Unit = {
    if (money.amount % 5 != 0) {
      System.err.println("The smallest nominal is 5 and we cannot satisfy your request.")
    } else {
      dispenser.dispense(money)
    }
  }

}
