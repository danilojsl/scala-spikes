package com.design.patterns.chain

class Dispenser20(val next: Option[Dispenser]) extends Dispenser {
  override val amount: Int = 20
}
