package com.jcaa.usersmanagement.domain.enums;

public enum ContractType {
  FIXED_TERM,
  INDEFINITE,
  SERVICE,
  APPRENTICESHIP;

  public static ContractType fromString(final String value) {
    for (final ContractType type : values()) {
      if (type.name().equalsIgnoreCase(value)) {
        return type;
      }
    }
    throw new IllegalArgumentException("The contract type '" + value + "' is not valid.");
  }
}
