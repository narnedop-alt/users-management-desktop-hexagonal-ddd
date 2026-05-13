package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io;

import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.EmployeeResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class EmployeeResponsePrinter {

  private static final String SEPARATOR = "-".repeat(52);
  private static final String ROW_FORMAT = "  %-17s : %s%n";

  private final ConsoleIO console;

  public void print(final EmployeeResponse response) {
    console.println(SEPARATOR);
    console.printf(ROW_FORMAT, "ID",              response.id());
    console.printf(ROW_FORMAT, "Document number", response.documentNumber());
    console.printf(ROW_FORMAT, "First name",      response.firstName());
    console.printf(ROW_FORMAT, "Last name",       response.lastName());
    console.printf(ROW_FORMAT, "Email",           response.email());
    console.printf(ROW_FORMAT, "Phone",           response.phone());
    console.printf(ROW_FORMAT, "Base salary",     response.baseSalary());
    console.printf(ROW_FORMAT, "Status",          response.status());
    console.printf(ROW_FORMAT, "Created at",      response.createdAt());
    console.printf(ROW_FORMAT, "Updated at",      response.updatedAt());
    console.println(SEPARATOR);
  }

  public void printList(final List<EmployeeResponse> employees) {
    if (employees.isEmpty()) {
      console.println("  No employees found.");
      return;
    }
    console.printf("%n  Total: %d employee(s)%n", employees.size());
    employees.forEach(this::print);
  }
}
