package com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository;

import com.jcaa.usersmanagement.application.port.out.GetAllContractsPort;
import com.jcaa.usersmanagement.application.port.out.GetContractByIdPort;
import com.jcaa.usersmanagement.application.port.out.GetContractByNumberPort;
import com.jcaa.usersmanagement.application.port.out.GetContractsByEmployeeIdPort;
import com.jcaa.usersmanagement.application.port.out.SaveContractPort;
import com.jcaa.usersmanagement.application.port.out.UpdateContractPort;
import com.jcaa.usersmanagement.domain.exception.ContractNotFoundException;
import com.jcaa.usersmanagement.domain.model.ContractModel;
import com.jcaa.usersmanagement.domain.valueobject.ContractId;
import com.jcaa.usersmanagement.domain.valueobject.ContractNumber;
import com.jcaa.usersmanagement.domain.valueobject.EmployeeId;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto.ContractPersistenceDto;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception.PersistenceException;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper.ContractPersistenceMapper;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@RequiredArgsConstructor
public final class ContractRepositoryMySQL
    implements SaveContractPort,
        UpdateContractPort,
        GetContractByIdPort,
        GetAllContractsPort,
        GetContractByNumberPort,
        GetContractsByEmployeeIdPort {

  private static final String SELECT_COLUMNS =
      "id, employee_id, contract_number, position, contract_type, start_date, end_date,"
          + " monthly_salary, status, created_at, updated_at ";

  private static final String SQL_INSERT =
      "INSERT INTO contracts "
          + "(id, employee_id, contract_number, position, contract_type, start_date, end_date,"
          + " monthly_salary, status, created_at, updated_at) "
          + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";

  private static final String SQL_UPDATE =
      "UPDATE contracts "
          + "SET employee_id = ?, contract_number = ?, position = ?, contract_type = ?,"
          + " start_date = ?, end_date = ?, monthly_salary = ?, status = ?, updated_at = NOW() "
          + "WHERE id = ?";

  private static final String SQL_SELECT_BY_ID =
      "SELECT " + SELECT_COLUMNS + "FROM contracts WHERE id = ? LIMIT 1";

  private static final String SQL_SELECT_BY_NUMBER =
      "SELECT " + SELECT_COLUMNS + "FROM contracts WHERE contract_number = ? LIMIT 1";

  private static final String SQL_SELECT_BY_EMPLOYEE_ID =
      "SELECT "
          + SELECT_COLUMNS
          + "FROM contracts WHERE employee_id = ? ORDER BY start_date DESC, contract_number ASC";

  private static final String SQL_SELECT_ALL =
      "SELECT " + SELECT_COLUMNS + "FROM contracts ORDER BY start_date DESC, contract_number ASC";

  private final Connection connection;

  @Override
  public ContractModel save(final ContractModel contract) {
    final ContractPersistenceDto dto = ContractPersistenceMapper.fromModelToDto(contract);
    executeSave(dto);
    return findByIdOrFail(contract.getId());
  }

  @Override
  public ContractModel update(final ContractModel contract) {
    final ContractPersistenceDto dto = ContractPersistenceMapper.fromModelToDto(contract);
    executeUpdate(dto);
    return findByIdOrFail(contract.getId());
  }

  @Override
  public Optional<ContractModel> getById(final ContractId contractId) {
    try (final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
      statement.setString(1, contractId.value());
      final ResultSet resultSet = statement.executeQuery();
      if (!resultSet.next()) {
        return Optional.empty();
      }
      return Optional.of(ContractPersistenceMapper.fromResultSetToModel(resultSet));
    } catch (final SQLException exception) {
      throw PersistenceException.becauseFindByIdFailed(contractId.value(), exception);
    }
  }

  @Override
  public Optional<ContractModel> getByContractNumber(final ContractNumber contractNumber) {
    try (final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_NUMBER)) {
      statement.setString(1, contractNumber.value());
      final ResultSet resultSet = statement.executeQuery();
      if (!resultSet.next()) {
        return Optional.empty();
      }
      return Optional.of(ContractPersistenceMapper.fromResultSetToModel(resultSet));
    } catch (final SQLException exception) {
      throw PersistenceException.becauseFindByEmailFailed(contractNumber.value(), exception);
    }
  }

  @Override
  public List<ContractModel> getByEmployeeId(final EmployeeId employeeId) {
    try (final PreparedStatement statement =
        connection.prepareStatement(SQL_SELECT_BY_EMPLOYEE_ID)) {
      statement.setString(1, employeeId.value());
      final ResultSet resultSet = statement.executeQuery();
      return ContractPersistenceMapper.fromResultSetToModelList(resultSet);
    } catch (final SQLException exception) {
      throw PersistenceException.becauseFindByIdFailed(employeeId.value(), exception);
    }
  }

  @Override
  public List<ContractModel> getAll() {
    try (final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL)) {
      final ResultSet resultSet = statement.executeQuery();
      return ContractPersistenceMapper.fromResultSetToModelList(resultSet);
    } catch (final SQLException exception) {
      throw PersistenceException.becauseFindAllFailed(exception);
    }
  }

  private void executeSave(final ContractPersistenceDto dto) {
    try (final PreparedStatement statement = connection.prepareStatement(SQL_INSERT)) {
      statement.setString(1, dto.id());
      statement.setString(2, dto.employeeId());
      statement.setString(3, dto.contractNumber());
      statement.setString(4, dto.position());
      statement.setString(5, dto.contractType());
      statement.setDate(6, Date.valueOf(dto.startDate()));
      setNullableDate(statement, 7, dto.endDate());
      statement.setString(8, dto.monthlySalary());
      statement.setString(9, dto.status());
      statement.executeUpdate();
    } catch (final SQLException exception) {
      throw PersistenceException.becauseSaveFailed(dto.id(), exception);
    }
  }

  private void executeUpdate(final ContractPersistenceDto dto) {
    try (final PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
      statement.setString(1, dto.employeeId());
      statement.setString(2, dto.contractNumber());
      statement.setString(3, dto.position());
      statement.setString(4, dto.contractType());
      statement.setDate(5, Date.valueOf(dto.startDate()));
      setNullableDate(statement, 6, dto.endDate());
      statement.setString(7, dto.monthlySalary());
      statement.setString(8, dto.status());
      statement.setString(9, dto.id());
      statement.executeUpdate();
    } catch (final SQLException exception) {
      throw PersistenceException.becauseUpdateFailed(dto.id(), exception);
    }
  }

  private static void setNullableDate(
      final PreparedStatement statement, final int index, final String value) throws SQLException {
    if (Objects.isNull(value)) {
      statement.setDate(index, null);
      return;
    }
    statement.setDate(index, Date.valueOf(value));
  }

  private ContractModel findByIdOrFail(final ContractId contractId) {
    return getById(contractId)
        .orElseThrow(() -> ContractNotFoundException.becauseIdWasNotFound(contractId.value()));
  }
}
