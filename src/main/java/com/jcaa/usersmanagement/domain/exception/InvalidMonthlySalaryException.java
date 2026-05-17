package com.jcaa.usersmanagement.domain.exception;

public final class InvalidMonthlySalaryException extends DomainException {

  private static final String MESSAGE_NULL         = "The monthly salary must not be null.";
  private static final String MESSAGE_NOT_POSITIVE = "The monthly salary must be greater than zero.";
  private static final String MESSAGE_SCALE        = "The monthly salary must have at most 2 decimal places.";

  private InvalidMonthlySalaryException(final String message) {
    super(message);
  }

  public static InvalidMonthlySalaryException becauseValueIsNull() {
    return new InvalidMonthlySalaryException(MESSAGE_NULL);
  }

  public static InvalidMonthlySalaryException becauseValueIsNotPositive() {
    return new InvalidMonthlySalaryException(MESSAGE_NOT_POSITIVE);
  }

  public static InvalidMonthlySalaryException becauseScaleExceedsLimit() {
    return new InvalidMonthlySalaryException(MESSAGE_SCALE);
  }
}
