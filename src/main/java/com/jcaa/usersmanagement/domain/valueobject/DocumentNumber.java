package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidDocumentNumberException;
import java.util.Objects;
import java.util.regex.Pattern;

public record DocumentNumber(String value) {

  private static final Pattern DIGITS_ONLY  = Pattern.compile("^[0-9]{6,20}$");

  public DocumentNumber {
    final String normalizedValue = Objects.requireNonNull(value, "DocumentNumber cannot be null").trim();
    validateNotEmpty(normalizedValue);
    validateFormat(normalizedValue);
    value = normalizedValue;
  }

  private static void validateNotEmpty(final String normalizedValue) {
    if (normalizedValue.isEmpty()) {
      throw InvalidDocumentNumberException.becauseValueIsEmpty();
    }
  }

  private static void validateFormat(final String normalizedValue) {
    if (!DIGITS_ONLY.matcher(normalizedValue).matches()) {
      throw InvalidDocumentNumberException.becauseFormatIsInvalid(normalizedValue);
    }
  }

  @Override
  public String toString() {
    return value;
  }
}
