package com.jcaa.usersmanagement.domain.model;

import com.jcaa.usersmanagement.domain.enums.ContractStatus;
import com.jcaa.usersmanagement.domain.enums.ContractType;
import com.jcaa.usersmanagement.domain.exception.InvalidContractDateRangeException;
import com.jcaa.usersmanagement.domain.valueobject.ContractId;
import com.jcaa.usersmanagement.domain.valueobject.ContractNumber;
import com.jcaa.usersmanagement.domain.valueobject.EmployeeId;
import com.jcaa.usersmanagement.domain.valueobject.MonthlySalary;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Value;

@Value
public class ContractModel {

  ContractId     id;
  EmployeeId     employeeId;
  ContractNumber contractNumber;
  String         position;
  ContractType   contractType;
  LocalDate      startDate;
  LocalDate      endDate;
  MonthlySalary  monthlySalary;
  ContractStatus status;
  LocalDateTime  createdAt;
  LocalDateTime  updatedAt;

  public ContractModel(
      final ContractId     id,
      final EmployeeId     employeeId,
      final ContractNumber contractNumber,
      final String         position,
      final ContractType   contractType,
      final LocalDate      startDate,
      final LocalDate      endDate,
      final MonthlySalary  monthlySalary,
      final ContractStatus status,
      final LocalDateTime  createdAt,
      final LocalDateTime  updatedAt) {
    this.id = Objects.requireNonNull(id, "Contract id cannot be null");
    this.employeeId = Objects.requireNonNull(employeeId, "Employee id cannot be null");
    this.contractNumber = Objects.requireNonNull(contractNumber, "Contract number cannot be null");
    this.position = normalizePosition(position);
    this.contractType = Objects.requireNonNull(contractType, "Contract type cannot be null");
    this.startDate = Objects.requireNonNull(startDate, "Start date cannot be null");
    validateDateRange(this.startDate, endDate);
    this.endDate = endDate;
    this.monthlySalary = Objects.requireNonNull(monthlySalary, "Monthly salary cannot be null");
    this.status = Objects.requireNonNull(status, "Contract status cannot be null");
    this.createdAt = Objects.requireNonNull(createdAt, "Created at cannot be null");
    this.updatedAt = Objects.requireNonNull(updatedAt, "Updated at cannot be null");
  }

  public static ContractModel create(
      final ContractId     id,
      final EmployeeId     employeeId,
      final ContractNumber contractNumber,
      final String         position,
      final ContractType   contractType,
      final LocalDate      startDate,
      final LocalDate      endDate,
      final MonthlySalary  monthlySalary) {
    final LocalDateTime now = LocalDateTime.now();
    return new ContractModel(
        id, employeeId, contractNumber, position, contractType, startDate, endDate,
        monthlySalary, ContractStatus.ACTIVE, now, now);
  }

  public ContractModel update(
      final String        position,
      final ContractType  contractType,
      final LocalDate     startDate,
      final LocalDate     endDate,
      final MonthlySalary monthlySalary) {
    return new ContractModel(
        id, employeeId, contractNumber, position, contractType, startDate, endDate,
        monthlySalary, status, createdAt, LocalDateTime.now());
  }

  public ContractModel suspend() {
    return changeStatus(ContractStatus.SUSPENDED);
  }

  public ContractModel finish(final LocalDate finishDate) {
    return new ContractModel(
        id, employeeId, contractNumber, position, contractType, startDate, finishDate,
        monthlySalary, ContractStatus.FINISHED, createdAt, LocalDateTime.now());
  }

  public ContractModel cancel() {
    return changeStatus(ContractStatus.CANCELLED);
  }

  private ContractModel changeStatus(final ContractStatus newStatus) {
    return new ContractModel(
        id, employeeId, contractNumber, position, contractType, startDate, endDate,
        monthlySalary, newStatus, createdAt, LocalDateTime.now());
  }

  private static String normalizePosition(final String position) {
    final String normalizedPosition =
        Objects.requireNonNull(position, "Contract position cannot be null").trim();
    if (normalizedPosition.length() < 2) {
      throw new IllegalArgumentException("The contract position must have at least 2 characters.");
    }
    return normalizedPosition;
  }

  private static void validateDateRange(final LocalDate startDate, final LocalDate endDate) {
    if (Objects.nonNull(endDate) && endDate.isBefore(startDate)) {
      throw InvalidContractDateRangeException.becauseEndDateIsBeforeStartDate();
    }
  }
}
