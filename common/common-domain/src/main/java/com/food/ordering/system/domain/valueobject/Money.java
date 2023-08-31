package com.food.ordering.system.domain.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Money {
	private final BigDecimal amout;

	public Money(BigDecimal amout) {
		this.amout = amout;
	}

	public boolean isGreaterThanZero() {
		return this.amout != null && this.amout.compareTo(BigDecimal.ZERO) >0;
	}

	public boolean isGreaterThan(Money money) {
		return this.amout != null && this.amout.compareTo(money.getAmout()) >0;
	}

	public Money add(Money money) {
		return new Money(setScale(this.amout.add(money.getAmout())));
	}

	public Money subtract(Money money) {
		return new Money(setScale(this.amout.subtract(money.getAmout())));
	}

	public Money multiply(int multiplier) {
		return new Money(setScale(this.amout.multiply(new BigDecimal(multiplier))));
	}

	public BigDecimal getAmout() {
		return amout;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Money money = (Money) o;
		return amout.equals(money.amout);
	}

	@Override
	public int hashCode() {
		return Objects.hash(amout);
	}

	private BigDecimal setScale(BigDecimal input) {
		return input.setScale(2, RoundingMode.HALF_EVEN);
	}
}
