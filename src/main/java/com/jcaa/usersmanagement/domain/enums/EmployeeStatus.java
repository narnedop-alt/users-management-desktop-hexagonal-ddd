package com.jcaa.usersmanagement.domain.enums;

public enum EmployeeStatus {
  ACTIVE,
  INACTIVE,
  SUSPENDED,
  TERMINATED;

  public static EmployeeStatus fromString(final String value) {
    for (final EmployeeStatus status : values()) {
      if (status.name().equalsIgnoreCase(value)) {
        return status;
      }
    }
    throw new IllegalArgumentException("The employee status '" + value + "' is not valid.");
  }
}
