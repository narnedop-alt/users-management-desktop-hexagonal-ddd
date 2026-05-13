package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto;

public record UpdateEmployeeRequest(
    String id,
    String documentNumber,
    String firstName,
    String lastName,
    String email,
    String phone,
    String baseSalary,
    String status) {}
