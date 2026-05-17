package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io;

import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.ContractResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ContractResponsePrinter {

  private static final String SEPARATOR = "-".repeat(58);
  private static final String ROW_FORMAT = "  %-17s : %s%n";

  private final ConsoleIO console;

  public void print(final ContractResponse response) {
    console.println(SEPARATOR);
    console.printf(ROW_FORMAT, "ID",              response.id());
    console.printf(ROW_FORMAT, "Employee ID",     response.employeeId());
    console.printf(ROW_FORMAT, "Contract number", response.contractNumber());
    console.printf(ROW_FORMAT, "Position",        response.position());
    console.printf(ROW_FORMAT, "Contract type",   response.contractType());
    console.printf(ROW_FORMAT, "Start date",      response.startDate());
    console.printf(ROW_FORMAT, "End date",        response.endDate());
    console.printf(ROW_FORMAT, "Monthly salary",  response.monthlySalary());
    console.printf(ROW_FORMAT, "Status",          response.status());
    console.printf(ROW_FORMAT, "Created at",      response.createdAt());
    console.printf(ROW_FORMAT, "Updated at",      response.updatedAt());
    console.println(SEPARATOR);
  }

  public void printList(final List<ContractResponse> contracts) {
    if (contracts.isEmpty()) {
      console.println("  No contracts found.");
      return;
    }
    console.printf("%n  Total: %d contract(s)%n", contracts.size());
    contracts.forEach(this::print);
  }
}
