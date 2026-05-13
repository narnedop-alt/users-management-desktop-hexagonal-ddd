package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto;

public record EmployeeResponse(
    String id,
    String documentNumber,
    String firstName,
    String lastName,
    String email,
    String phone,
    String baseSalary,
    String status,
    String createdAt,
    String updatedAt) {}
