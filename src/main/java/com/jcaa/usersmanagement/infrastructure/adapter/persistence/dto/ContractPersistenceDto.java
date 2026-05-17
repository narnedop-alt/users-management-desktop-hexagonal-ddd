package com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto;

public record ContractPersistenceDto(
    String id,
    String employeeId,
    String contractNumber,
    String position,
    String contractType,
    String startDate,
    String endDate,
    String monthlySalary,
    String status,
    String createdAt,
    String updatedAt) {}
