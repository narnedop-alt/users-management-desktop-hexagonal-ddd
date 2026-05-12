package com.jcaa.usersmanagement.application.service.dto.command;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record UpdateEmployeeCommand(
    @NotBlank(message = "id must not be blank") String id,
    @NotBlank(message = "documentNumber must not be blank") String documentNumber,
    @NotBlank(message = "firstName must not be blank") String firstName,
    @NotBlank(message = "lastName must not be blank") String lastName,
    @NotBlank(message = "email must not be blank")
        @Email(message = "email must be a valid email address")
        String email,
    String phone,
    @NotNull(message = "baseSalary must not be null")
        @Positive(message = "baseSalary must be positive")
        BigDecimal baseSalary,
    @NotBlank(message = "status must not be blank") String status)
{

}
