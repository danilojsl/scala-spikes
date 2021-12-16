package com.design.patterns.chain

class Dispenser5(val next: Option[Dispenser]) extends Dispenser {
  override val amount: Int = 5
}
