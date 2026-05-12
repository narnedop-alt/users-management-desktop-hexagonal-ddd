package com.jcaa.usersmanagement.domain.model;

import com.jcaa.usersmanagement.domain.enums.EmployeeStatus;
import com.jcaa.usersmanagement.domain.valueobject.BaseSalary;
import com.jcaa.usersmanagement.domain.valueobject.DocumentNumber;
import com.jcaa.usersmanagement.domain.valueobject.EmployeeEmail;
import com.jcaa.usersmanagement.domain.valueobject.EmployeeId;
import java.time.LocalDateTime;
import lombok.Value;

@Value
public class EmployeeModel {

  EmployeeId     id;
  DocumentNumber documentNumber;
  String         firstName;
  String         lastName;
  EmployeeEmail  email;
  String         phone;
  BaseSalary     baseSalary;
  EmployeeStatus status;
  LocalDateTime  createdAt;
  LocalDateTime  updatedAt;

  public static EmployeeModel create(
      final EmployeeId     id,
      final DocumentNumber documentNumber,
      final String         firstName,
      final String         lastName,
      final EmployeeEmail  email,
      final String         phone,
      final BaseSalary     baseSalary) {
    final LocalDateTime now = LocalDateTime.now();
    return new EmployeeModel(
        id, documentNumber, firstName, lastName, email, phone, baseSalary,
        EmployeeStatus.ACTIVE, now, now);
  }

  public EmployeeModel deactivate() {
    return new EmployeeModel(
        id, documentNumber, firstName, lastName, email, phone, baseSalary,
        EmployeeStatus.INACTIVE, createdAt, LocalDateTime.now());
  }

  public EmployeeModel suspend() {
    return new EmployeeModel(
        id, documentNumber, firstName, lastName, email, phone, baseSalary,
        EmployeeStatus.SUSPENDED, createdAt, LocalDateTime.now());
  }

  public EmployeeModel terminate() {
    return new EmployeeModel(
        id, documentNumber, firstName, lastName, email, phone, baseSalary,
        EmployeeStatus.TERMINATED, createdAt, LocalDateTime.now());
  }
}
