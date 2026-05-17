package com.jcaa.usersmanagement.domain.enums;

public enum ContractStatus {
  ACTIVE,
  SUSPENDED,
  FINISHED,
  CANCELLED;

  public static ContractStatus fromString(final String value) {
    for (final ContractStatus status : values()) {
      if (status.name().equalsIgnoreCase(value)) {
        return status;
      }
    }
    throw new IllegalArgumentException("The contract status '" + value + "' is not valid.");
  }
}
