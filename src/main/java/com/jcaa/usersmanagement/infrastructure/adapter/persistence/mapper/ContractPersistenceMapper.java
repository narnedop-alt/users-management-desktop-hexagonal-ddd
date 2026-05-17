package com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper;

import com.jcaa.usersmanagement.domain.enums.ContractStatus;
import com.jcaa.usersmanagement.domain.enums.ContractType;
import com.jcaa.usersmanagement.domain.model.ContractModel;
import com.jcaa.usersmanagement.domain.valueobject.ContractId;
import com.jcaa.usersmanagement.domain.valueobject.ContractNumber;
import com.jcaa.usersmanagement.domain.valueobject.EmployeeId;
import com.jcaa.usersmanagement.domain.valueobject.MonthlySalary;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto.ContractPersistenceDto;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.ContractEntity;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ContractPersistenceMapper {

  private static final DateTimeFormatter MYSQL_DATETIME =
      new DateTimeFormatterBuilder()
          .appendPattern("yyyy-MM-dd HH:mm:ss")
          .optionalStart()
          .appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true)
          .optionalEnd()
          .toFormatter();

  public ContractPersistenceDto fromModelToDto(final ContractModel contract) {
    return new ContractPersistenceDto(
        contract.getId().value(),
        contract.getEmployeeId().value(),
        contract.getContractNumber().value(),
        contract.getPosition(),
        contract.getContractType().name(),
        contract.getStartDate().toString(),
        Objects.isNull(contract.getEndDate()) ? null : contract.getEndDate().toString(),
        contract.getMonthlySalary().value().toPlainString(),
        contract.getStatus().name(),
        null,
        null);
  }

  public ContractEntity fromResultSetToEntity(final ResultSet resultSet) throws SQLException {
    return new ContractEntity(
        resultSet.getString("id"),
        resultSet.getString("employee_id"),
        resultSet.getString("contract_number"),
        resultSet.getString("position"),
        resultSet.getString("contract_type"),
        resultSet.getString("start_date"),
        resultSet.getString("end_date"),
        resultSet.getString("monthly_salary"),
        resultSet.getString("status"),
        resultSet.getString("created_at"),
        resultSet.getString("updated_at"));
  }

  public ContractModel fromEntityToModel(final ContractEntity entity) {
    return new ContractModel(
        new ContractId(entity.id()),
        new EmployeeId(entity.employeeId()),
        new ContractNumber(entity.contractNumber()),
        entity.position(),
        ContractType.fromString(entity.contractType()),
        LocalDate.parse(entity.startDate()),
        Objects.isNull(entity.endDate()) ? null : LocalDate.parse(entity.endDate()),
        new MonthlySalary(new BigDecimal(entity.monthlySalary())),
        ContractStatus.fromString(entity.status()),
        LocalDateTime.parse(entity.createdAt(), MYSQL_DATETIME),
        LocalDateTime.parse(entity.updatedAt(), MYSQL_DATETIME));
  }

  public ContractModel fromResultSetToModel(final ResultSet resultSet) throws SQLException {
    return fromEntityToModel(fromResultSetToEntity(resultSet));
  }

  public List<ContractModel> fromResultSetToModelList(final ResultSet resultSet)
      throws SQLException {
    final List<ContractModel> contracts = new ArrayList<>();
    while (resultSet.next()) {
      contracts.add(fromResultSetToModel(resultSet));
    }
    return contracts;
  }
}
