package com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository;

import com.jcaa.usersmanagement.application.port.out.DeleteEmployeePort;
import com.jcaa.usersmanagement.application.port.out.GetAllEmployeesPort;
import com.jcaa.usersmanagement.application.port.out.GetEmployeeByDocumentPort;
import com.jcaa.usersmanagement.application.port.out.GetEmployeeByIdPort;
import com.jcaa.usersmanagement.application.port.out.SaveEmployeePort;
import com.jcaa.usersmanagement.application.port.out.UpdateEmployeePort;
import com.jcaa.usersmanagement.domain.exception.EmployeeNotFoundException;
import com.jcaa.usersmanagement.domain.model.EmployeeModel;
import com.jcaa.usersmanagement.domain.valueobject.DocumentNumber;
import com.jcaa.usersmanagement.domain.valueobject.EmployeeId;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto.EmployeePersistenceDto;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception.PersistenceException;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper.EmployeePersistenceMapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@RequiredArgsConstructor
public final class EmployeeRepositoryMySQL
    implements SaveEmployeePort,
        UpdateEmployeePort,
        GetEmployeeByIdPort,
        GetAllEmployeesPort,
        DeleteEmployeePort,
        GetEmployeeByDocumentPort {

  private static final String SQL_INSERT =
      "INSERT INTO employees "
          + "(id, document_number, first_name, last_name, email, phone, base_salary, status,"
          + " created_at, updated_at) "
          + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";

  private static final String SQL_UPDATE =
      "UPDATE employees "
          + "SET document_number = ?, first_name = ?, last_name = ?, email = ?, phone = ?,"
          + " base_salary = ?, status = ?, updated_at = NOW() "
          + "WHERE id = ?";

  private static final String SQL_SELECT_BY_ID =
      "SELECT id, document_number, first_name, last_name, email, phone, base_salary, status,"
          + " created_at, updated_at "
          + "FROM employees "
          + "WHERE id = ? LIMIT 1";

  private static final String SQL_SELECT_BY_DOCUMENT =
      "SELECT id, document_number, first_name, last_name, email, phone, base_salary, status,"
          + " created_at, updated_at "
          + "FROM employees "
          + "WHERE document_number = ? LIMIT 1";

  private static final String SQL_SELECT_ALL =
      "SELECT id, document_number, first_name, last_name, email, phone, base_salary, status,"
          + " created_at, updated_at "
          + "FROM employees "
          + "ORDER BY last_name ASC, first_name ASC";

  private static final String SQL_DELETE =
      "DELETE FROM employees "
          + "WHERE id = ?";

  private final Connection connection;

  @Override
  public EmployeeModel save(final EmployeeModel employee) {
    final EmployeePersistenceDto dto = EmployeePersistenceMapper.fromModelToDto(employee);
    executeSave(dto);
    return findByIdOrFail(employee.getId());
  }

  @Override
  public EmployeeModel update(final EmployeeModel employee) {
    final EmployeePersistenceDto dto = EmployeePersistenceMapper.fromModelToDto(employee);
    executeUpdate(dto);
    return findByIdOrFail(employee.getId());
  }

  @Override
  public Optional<EmployeeModel> getById(final EmployeeId employeeId) {
    try (final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
      statement.setString(1, employeeId.value());
      final ResultSet resultSet = statement.executeQuery();
      if (!resultSet.next()) {
        return Optional.empty();
      }
      return Optional.of(EmployeePersistenceMapper.fromResultSetToModel(resultSet));
    } catch (final SQLException exception) {
      throw PersistenceException.becauseFindByIdFailed(employeeId.value(), exception);
    }
  }

  @Override
  public Optional<EmployeeModel> getByDocumentNumber(final DocumentNumber documentNumber) {
    try (final PreparedStatement statement =
        connection.prepareStatement(SQL_SELECT_BY_DOCUMENT)) {
      statement.setString(1, documentNumber.value());
      final ResultSet resultSet = statement.executeQuery();
      if (!resultSet.next()) {
        return Optional.empty();
      }
      return Optional.of(EmployeePersistenceMapper.fromResultSetToModel(resultSet));
    } catch (final SQLException exception) {
      throw PersistenceException.becauseFindByEmailFailed(documentNumber.value(), exception);
    }
  }

  @Override
  public List<EmployeeModel> getAll() {
    try (final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL)) {
      final ResultSet resultSet = statement.executeQuery();
      return EmployeePersistenceMapper.fromResultSetToModelList(resultSet);
    } catch (final SQLException exception) {
      throw PersistenceException.becauseFindAllFailed(exception);
    }
  }

  @Override
  public void delete(final EmployeeId employeeId) {
    try (final PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
      statement.setString(1, employeeId.value());
      statement.executeUpdate();
    } catch (final SQLException exception) {
      throw PersistenceException.becauseDeleteFailed(employeeId.value(), exception);
    }
  }

  private void executeSave(final EmployeePersistenceDto dto) {
    try (final PreparedStatement statement = connection.prepareStatement(SQL_INSERT)) {
      statement.setString(1, dto.id());
      statement.setString(2, dto.documentNumber());
      statement.setString(3, dto.firstName());
      statement.setString(4, dto.lastName());
      statement.setString(5, dto.email());
      statement.setString(6, dto.phone());
      statement.setString(7, dto.baseSalary());
      statement.setString(8, dto.status());
      statement.executeUpdate();
    } catch (final SQLException exception) {
      throw PersistenceException.becauseSaveFailed(dto.id(), exception);
    }
  }

  private void executeUpdate(final EmployeePersistenceDto dto) {
    try (final PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
      statement.setString(1, dto.documentNumber());
      statement.setString(2, dto.firstName());
      statement.setString(3, dto.lastName());
      statement.setString(4, dto.email());
      statement.setString(5, dto.phone());
      statement.setString(6, dto.baseSalary());
      statement.setString(7, dto.status());
      statement.setString(8, dto.id());
      statement.executeUpdate();
    } catch (final SQLException exception) {
      throw PersistenceException.becauseUpdateFailed(dto.id(), exception);
    }
  }

  private EmployeeModel findByIdOrFail(final EmployeeId employeeId) {
    return getById(employeeId)
        .orElseThrow(
            () -> EmployeeNotFoundException.becauseIdWasNotFound(employeeId.value()));
  }
}
