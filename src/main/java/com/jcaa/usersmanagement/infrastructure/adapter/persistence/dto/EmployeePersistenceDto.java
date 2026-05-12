package com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto;

public record EmployeePersistenceDto(
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
