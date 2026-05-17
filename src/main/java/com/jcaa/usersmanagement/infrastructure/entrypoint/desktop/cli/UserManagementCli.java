package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli;

import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.CreateContractHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.CreateEmployeeHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.CreateUserHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.DeleteEmployeeHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.DeleteUserHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.FindContractByIdHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.FindEmployeeByIdHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.FindUserByIdHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.FinishContractHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.ListContractsHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.ListEmployeesHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.ListUsersHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.LoginHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.OperationHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.UpdateContractHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.UpdateEmployeeHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.UpdateUserHandler;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ContractResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.EmployeeResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.UserResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.menu.MenuOption;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.ContractController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.EmployeeController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.UserController;
import jakarta.validation.ConstraintViolationException;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class UserManagementCli {

  private static final String BANNER =
      """
      ==========================================
           Users Management System
      ==========================================""";

  private static final String MENU_BORDER = "  ==========================================";

  private final UserController userController;
  private final EmployeeController employeeController;
  private final ContractController contractController;
  private final ConsoleIO console;

  public void start() {
    console.println(BANNER);
    final UserResponsePrinter userPrinter = new UserResponsePrinter(console);
    final EmployeeResponsePrinter employeePrinter = new EmployeeResponsePrinter(console);
    final ContractResponsePrinter contractPrinter = new ContractResponsePrinter(console);
    runLoop(buildHandlers(userPrinter, employeePrinter, contractPrinter));
  }

  private void runLoop(final Map<MenuOption, OperationHandler> handlers) {
    boolean running = true;
    while (running) {
      printMenu();
      final int choice = console.readInt("\n  Option: ");
      final Optional<MenuOption> option = MenuOption.fromNumber(choice);

      if (option.isEmpty()) {
        console.println("  Invalid option. Please try again.");
      } else if (option.get() == MenuOption.EXIT) {
        console.println("\n  Goodbye!\n");
        running = false;
      } else {
        executeHandler(handlers, option.get());
      }
    }
  }

  private void executeHandler(
      final Map<MenuOption, OperationHandler> handlers, final MenuOption option) {
    try {
      handlers.get(option).handle();
    } catch (final ConstraintViolationException exception) {
      console.println("  Validation errors:");
      exception.getConstraintViolations()
          .forEach(violation -> console.println("    - " + violation.getMessage()));
    } catch (final RuntimeException exception) {
      console.println("  Unexpected error: " + exception.getMessage());
    }
  }

  private Map<MenuOption, OperationHandler> buildHandlers(
      final UserResponsePrinter userPrinter,
      final EmployeeResponsePrinter employeePrinter,
      final ContractResponsePrinter contractPrinter) {
    return Map.ofEntries(
        Map.entry(MenuOption.LIST_USERS,       new ListUsersHandler(userController, userPrinter)),
        Map.entry(MenuOption.FIND_USER,        new FindUserByIdHandler(userController, console, userPrinter)),
        Map.entry(MenuOption.CREATE_USER,      new CreateUserHandler(userController, console, userPrinter)),
        Map.entry(MenuOption.UPDATE_USER,      new UpdateUserHandler(userController, console, userPrinter)),
        Map.entry(MenuOption.DELETE_USER,      new DeleteUserHandler(userController, console)),
        Map.entry(MenuOption.LOGIN,            new LoginHandler(userController, console, userPrinter)),
        Map.entry(MenuOption.LIST_EMPLOYEES,   new ListEmployeesHandler(employeeController, employeePrinter)),
        Map.entry(MenuOption.FIND_EMPLOYEE,    new FindEmployeeByIdHandler(employeeController, console, employeePrinter)),
        Map.entry(MenuOption.CREATE_EMPLOYEE,  new CreateEmployeeHandler(employeeController, console, employeePrinter)),
        Map.entry(MenuOption.UPDATE_EMPLOYEE,  new UpdateEmployeeHandler(employeeController, console, employeePrinter)),
        Map.entry(MenuOption.DELETE_EMPLOYEE,  new DeleteEmployeeHandler(employeeController, console)),
        Map.entry(MenuOption.LIST_CONTRACTS,   new ListContractsHandler(contractController, contractPrinter)),
        Map.entry(MenuOption.FIND_CONTRACT,    new FindContractByIdHandler(contractController, console, contractPrinter)),
        Map.entry(MenuOption.CREATE_CONTRACT,  new CreateContractHandler(contractController, console, contractPrinter)),
        Map.entry(MenuOption.UPDATE_CONTRACT,  new UpdateContractHandler(contractController, console, contractPrinter)),
        Map.entry(MenuOption.FINISH_CONTRACT,  new FinishContractHandler(contractController, console, contractPrinter)));
  }

  private void printMenu() {
    console.println();
    console.println(MENU_BORDER);
    console.println("    Main Menu");
    console.println(MENU_BORDER);
    for (final MenuOption option : MenuOption.values()) {
      console.printf("    [%d] %s%n", option.getNumber(), option.getDescription());
    }
    console.println(MENU_BORDER);
  }
}
