package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto;

public record ContractResponse(
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
