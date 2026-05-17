package com.jcaa.usersmanagement.domain.exception;

public final class InvalidContractDateRangeException extends DomainException {

  private static final String MESSAGE_END_DATE_BEFORE_START_DATE =
      "The contract end date must not be before the start date.";

  private InvalidContractDateRangeException(final String message) {
    super(message);
  }

  public static InvalidContractDateRangeException becauseEndDateIsBeforeStartDate() {
    return new InvalidContractDateRangeException(MESSAGE_END_DATE_BEFORE_START_DATE);
  }
}
