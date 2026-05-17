package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidMonthlySalaryException;
import java.math.BigDecimal;
import java.util.Objects;

public record MonthlySalary(BigDecimal value) {

  private static final int        MAX_SCALE = 2;
  private static final BigDecimal MINIMUM   = BigDecimal.ZERO;

  public MonthlySalary {
    if (Objects.isNull(value)) {
      throw InvalidMonthlySalaryException.becauseValueIsNull();
    }
    validatePositive(value);
    validateScale(value);
  }

  private static void validatePositive(final BigDecimal amount) {
    if (amount.compareTo(MINIMUM) <= 0) {
      throw InvalidMonthlySalaryException.becauseValueIsNotPositive();
    }
  }

  private static void validateScale(final BigDecimal amount) {
    if (amount.stripTrailingZeros().scale() > MAX_SCALE) {
      throw InvalidMonthlySalaryException.becauseScaleExceedsLimit();
    }
  }

  @Override
  public String toString() {
    return value.toPlainString();
  }
}
