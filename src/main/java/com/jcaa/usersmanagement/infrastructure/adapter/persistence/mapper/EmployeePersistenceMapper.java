package com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper;

import com.jcaa.usersmanagement.domain.enums.EmployeeStatus;
import com.jcaa.usersmanagement.domain.model.EmployeeModel;
import com.jcaa.usersmanagement.domain.valueobject.BaseSalary;
import com.jcaa.usersmanagement.domain.valueobject.DocumentNumber;
import com.jcaa.usersmanagement.domain.valueobject.EmployeeEmail;
import com.jcaa.usersmanagement.domain.valueobject.EmployeeId;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto.EmployeePersistenceDto;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.EmployeeEntity;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EmployeePersistenceMapper {

  private static final DateTimeFormatter MYSQL_DATETIME =
      new DateTimeFormatterBuilder()
          .appendPattern("yyyy-MM-dd HH:mm:ss")
          .optionalStart()
          .appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true)
          .optionalEnd()
          .toFormatter();

  public EmployeePersistenceDto fromModelToDto(final EmployeeModel employee) {
    return new EmployeePersistenceDto(
        employee.getId().value(),
        employee.getDocumentNumber().value(),
        employee.getFirstName(),
        employee.getLastName(),
        employee.getEmail().value(),
        employee.getPhone(),
        employee.getBaseSalary().value().toPlainString(),
        employee.getStatus().name(),
        null,
        null);
  }

  public EmployeeEntity fromResultSetToEntity(final ResultSet resultSet) throws SQLException {
    return new EmployeeEntity(
        resultSet.getString("id"),
        resultSet.getString("document_number"),
        resultSet.getString("first_name"),
        resultSet.getString("last_name"),
        resultSet.getString("email"),
        resultSet.getString("phone"),
        resultSet.getString("base_salary"),
        resultSet.getString("status"),
        resultSet.getString("created_at"),
        resultSet.getString("updated_at"));
  }

  public EmployeeModel fromEntityToModel(final EmployeeEntity entity) {
    return new EmployeeModel(
        new EmployeeId(entity.id()),
        new DocumentNumber(entity.documentNumber()),
        entity.firstName(),
        entity.lastName(),
        new EmployeeEmail(entity.email()),
        entity.phone(),
        new BaseSalary(new BigDecimal(entity.baseSalary())),
        EmployeeStatus.fromString(entity.status()),
        LocalDateTime.parse(entity.createdAt(), MYSQL_DATETIME),
        LocalDateTime.parse(entity.updatedAt(), MYSQL_DATETIME));
  }

  public EmployeeModel fromResultSetToModel(final ResultSet resultSet) throws SQLException {
    return fromEntityToModel(fromResultSetToEntity(resultSet));
  }

  public List<EmployeeModel> fromResultSetToModelList(final ResultSet resultSet)
      throws SQLException {
    final List<EmployeeModel> employees = new ArrayList<>();
    while (resultSet.next()) {
      employees.add(fromResultSetToModel(resultSet));
    }
    return employees;
  }
}
