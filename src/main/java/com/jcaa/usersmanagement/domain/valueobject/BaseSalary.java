package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidBaseSalaryException;
import java.math.BigDecimal;
import java.util.Objects;

public record BaseSalary(BigDecimal value) {

  private static final int    MAX_SCALE   = 2;
  private static final BigDecimal MINIMUM = BigDecimal.ZERO;

  public BaseSalary {
    if (Objects.isNull(value)) {
      throw InvalidBaseSalaryException.becauseValueIsNull();
    }
    validatePositive(value);
    validateScale(value);
  }

  private static void validatePositive(final BigDecimal amount) {
    if (amount.compareTo(MINIMUM) <= 0) {
      throw InvalidBaseSalaryException.becauseValueIsNotPositive();
    }
  }

  private static void validateScale(final BigDecimal amount) {
    if (amount.stripTrailingZeros().scale() > MAX_SCALE) {
      throw InvalidBaseSalaryException.becauseScaleExceedsLimit();
    }
  }

  @Override
  public String toString() {
    return value.toPlainString();
  }
}
