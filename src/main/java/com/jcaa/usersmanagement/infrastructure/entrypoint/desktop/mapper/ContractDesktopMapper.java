package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreateContractCommand;
import com.jcaa.usersmanagement.application.service.dto.command.FinishContractCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateContractCommand;
import com.jcaa.usersmanagement.application.service.dto.query.FindContractByIdQuery;
import com.jcaa.usersmanagement.domain.model.ContractModel;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.ContractResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CreateContractRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UpdateContractRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public final class ContractDesktopMapper {

  private static final DateTimeFormatter DISPLAY_FORMAT =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  private ContractDesktopMapper() {}

  public static CreateContractCommand toCreateCommand(final CreateContractRequest request) {
    return new CreateContractCommand(
        request.employeeId(),
        request.contractNumber(),
        request.position(),
        request.contractType(),
        LocalDate.parse(request.startDate()),
        parseOptionalDate(request.endDate()),
        new BigDecimal(request.monthlySalary()));
  }

  public static UpdateContractCommand toUpdateCommand(final UpdateContractRequest request) {
    return new UpdateContractCommand(
        request.id(),
        request.employeeId(),
        request.contractNumber(),
        request.position(),
        request.contractType(),
        LocalDate.parse(request.startDate()),
        parseOptionalDate(request.endDate()),
        new BigDecimal(request.monthlySalary()),
        request.status());
  }

  public static FindContractByIdQuery toFindByIdQuery(final String id) {
    return new FindContractByIdQuery(id);
  }

  public static FinishContractCommand toFinishCommand(final String id, final String endDate) {
    return new FinishContractCommand(id, LocalDate.parse(endDate));
  }

  public static ContractResponse toResponse(final ContractModel contract) {
    return new ContractResponse(
        contract.getId().value(),
        contract.getEmployeeId().value(),
        contract.getContractNumber().value(),
        contract.getPosition(),
        contract.getContractType().name(),
        contract.getStartDate().toString(),
        contract.getEndDate() != null ? contract.getEndDate().toString() : "",
        contract.getMonthlySalary().value().toPlainString(),
        contract.getStatus().name(),
        contract.getCreatedAt().format(DISPLAY_FORMAT),
        contract.getUpdatedAt().format(DISPLAY_FORMAT));
  }

  public static List<ContractResponse> toResponseList(final List<ContractModel> contracts) {
    return contracts.stream().map(ContractDesktopMapper::toResponse).toList();
  }

  private static LocalDate parseOptionalDate(final String value) {
    return value.isBlank() ? null : LocalDate.parse(value);
  }
}
