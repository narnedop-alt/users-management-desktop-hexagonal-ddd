package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidContractIdException;
import java.util.Objects;
import java.util.UUID;

public record ContractId(String value) {

  public ContractId {
    final String normalizedValue = Objects.requireNonNull(value, "ContractId cannot be null").trim();
    validateNotEmpty(normalizedValue);
    validateUuid(normalizedValue);
    value = normalizedValue;
  }

  public static ContractId generate() {
    return new ContractId(UUID.randomUUID().toString());
  }

  private static void validateNotEmpty(final String normalizedValue) {
    if (normalizedValue.isEmpty()) {
      throw InvalidContractIdException.becauseValueIsEmpty();
    }
  }

  private static void validateUuid(final String normalizedValue) {
    try {
      UUID.fromString(normalizedValue);
    } catch (final IllegalArgumentException exception) {
      throw InvalidContractIdException.becauseFormatIsInvalid(normalizedValue);
    }
  }

  @Override
  public String toString() {
    return value;
  }
}
