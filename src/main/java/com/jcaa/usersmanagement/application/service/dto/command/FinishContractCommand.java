package com.jcaa.usersmanagement.application.service.dto.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record FinishContractCommand(
    @NotBlank(message = "id must not be blank") String id,
    @NotNull(message = "endDate must not be null") LocalDate endDate)
{

}
