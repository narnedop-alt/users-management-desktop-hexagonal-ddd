package com.jcaa.usersmanagement.application.service.dto.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateContractCommand(
    @NotBlank(message = "id must not be blank") String id,
    @NotBlank(message = "employeeId must not be blank") String employeeId,
    @NotBlank(message = "contractNumber must not be blank") String contractNumber,
    @NotBlank(message = "position must not be blank") String position,
    @NotBlank(message = "contractType must not be blank") String contractType,
    @NotNull(message = "startDate must not be null") LocalDate startDate,
    LocalDate endDate,
    @NotNull(message = "monthlySalary must not be null")
        @Positive(message = "monthlySalary must be positive")
        BigDecimal monthlySalary,
    @NotBlank(message = "status must not be blank") String status)
{

}
