package com.design.patterns.chain

class Dispenser10(val next: Option[Dispenser]) extends Dispenser {
  override val amount: Int = 10
}
