package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto;

public record CreateContractRequest(
    String employeeId,
    String contractNumber,
    String position,
    String contractType,
    String startDate,
    String endDate,
    String monthlySalary) {}
