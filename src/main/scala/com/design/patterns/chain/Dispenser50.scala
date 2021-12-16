package com.design.patterns.chain

class Dispenser50(val next: Option[Dispenser]) extends Dispenser {
  override val amount: Int = 50
}
