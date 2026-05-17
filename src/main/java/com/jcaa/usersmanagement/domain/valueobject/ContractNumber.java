package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidContractNumberException;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

public record ContractNumber(String value) {

  private static final int     MIN_LENGTH = 4;
  private static final int     MAX_LENGTH = 30;
  private static final Pattern VALID_FORMAT = Pattern.compile("^[A-Z0-9_-]+$");

  public ContractNumber {
    final String normalizedValue =
        Objects.requireNonNull(value, "ContractNumber cannot be null")
            .trim()
            .toUpperCase(Locale.ROOT);
    validateNotEmpty(normalizedValue);
    validateLength(normalizedValue);
    validateFormat(normalizedValue);
    value = normalizedValue;
  }

  private static void validateNotEmpty(final String normalizedValue) {
    if (normalizedValue.isEmpty()) {
      throw InvalidContractNumberException.becauseValueIsEmpty();
    }
  }

  private static void validateLength(final String normalizedValue) {
    if (normalizedValue.length() < MIN_LENGTH || normalizedValue.length() > MAX_LENGTH) {
      throw InvalidContractNumberException.becauseLengthIsInvalid();
    }
  }

  private static void validateFormat(final String normalizedValue) {
    if (!VALID_FORMAT.matcher(normalizedValue).matches()) {
      throw InvalidContractNumberException.becauseFormatIsInvalid(normalizedValue);
    }
  }

  @Override
  public String toString() {
    return value;
  }
}
