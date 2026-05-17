package com.jcaa.usersmanagement.application.service.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreateContractCommand;
import com.jcaa.usersmanagement.application.service.dto.command.FinishContractCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateContractCommand;
import com.jcaa.usersmanagement.application.service.dto.query.FindContractByIdQuery;
import com.jcaa.usersmanagement.domain.enums.ContractStatus;
import com.jcaa.usersmanagement.domain.enums.ContractType;
import com.jcaa.usersmanagement.domain.model.ContractModel;
import com.jcaa.usersmanagement.domain.valueobject.ContractId;
import com.jcaa.usersmanagement.domain.valueobject.ContractNumber;
import com.jcaa.usersmanagement.domain.valueobject.EmployeeId;
import com.jcaa.usersmanagement.domain.valueobject.MonthlySalary;
import java.time.LocalDateTime;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ContractApplicationMapper {

  public ContractModel fromCreateCommandToModel(final CreateContractCommand command) {
    return ContractModel.create(
        ContractId.generate(),
        new EmployeeId(command.employeeId()),
        new ContractNumber(command.contractNumber()),
        command.position(),
        ContractType.fromString(command.contractType()),
        command.startDate(),
        command.endDate(),
        new MonthlySalary(command.monthlySalary()));
  }

  public ContractModel fromUpdateCommandToModel(
      final UpdateContractCommand command, final ContractModel current) {
    return new ContractModel(
        new ContractId(command.id()),
        new EmployeeId(command.employeeId()),
        new ContractNumber(command.contractNumber()),
        command.position(),
        ContractType.fromString(command.contractType()),
        command.startDate(),
        command.endDate(),
        new MonthlySalary(command.monthlySalary()),
        ContractStatus.fromString(command.status()),
        current.getCreatedAt(),
        LocalDateTime.now());
  }

  public ContractModel fromFinishCommandToModel(
      final FinishContractCommand command, final ContractModel current) {
    return current.finish(command.endDate());
  }

  public ContractId fromFindByIdQueryToContractId(final FindContractByIdQuery query) {
    return new ContractId(query.id());
  }

  public ContractId fromFinishCommandToContractId(final FinishContractCommand command) {
    return new ContractId(command.id());
  }
}
