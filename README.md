# SpringBookShopAutotests : Test framework for UI auto-test of [SpringBookShop](https://github.com/HeisYenberg/SpringBookShop) project.

This repository contains the UI Automation Test Framework for
the [Spring Book Shop project](https://github.com/HeisYenberg/SpringBookShop).
The framework automates testing of all major functionalities of the bookshop,
ensuring the reliability and stability of the application.

The project uses Java, Selenide, TestNG, and Allure, providing a robust,
maintainable, and scalable structure for UI testing.

___

## Features

- **Full Functional Coverage**: Automated tests for critical workflows:
    - User authentication and session management.
    - Cart operations, including adding, removing books.
    - Admin functionality for book management (add, edit, delete books).
- **Database Interactions**: Directly validate and manipulate database states
  during tests for advanced test scenarios.
- **JSON Deserialization**: Parse and validate JSON responses for API-related UI
  validations.
- **Logging Support**: Comprehensive logging using SLF4J for test execution,
  debug information, and error tracking.
- **Selenide for UI Interactions**: Simplifies test creation with clean and
  expressive syntax.
- **TestNG for Test Management**: Supports test organization, parameterization,
  and parallel execution.
- **Allure Reports**: Generate detailed and visually appealing test execution
  reports.
- **Scalable and Configurable**: Easy to extend for new features and adaptable
  for various environments.

---

## Prerequisites

- **Java 8 or later**
- **Maven**: For building the project and managing dependencies.
- **Browser Driver**: Ensure the required browser driver (e.g., ChromeDriver) is
  available in the system PATH or configured in the framework.

---

## Getting Started

### 1. Clone the Repository

```bash  
git clone https://github.com/HeisYenberg/SpringBookShopAutotests.git  
cd SpringBookShopAutotests  
```

### 2. Setup SpringBookShop as described in [project page](https://github.com/HeisYenberg/SpringBookShop)

### 3. Configure database and application ports and hosts

Update src/test/resources/selenide.properties with your stand-specific details:

```properties
selenide.baseUrl=http://localhost:8080
selenide.browser=chrome
selenide.headless=true
```

Update src/test/resources/datasource.properties with your database-specific
details:

```properties
datasource.url=jdbc:postgresql://localhost:5432/spring_book
datasource.username=postgres
datasource.password=postgres
datasource.driver_class_name=org.postgresql.Driver
```

### 4. Execute tests

```bash
mvn clean test
```

### 5. Generate AllureReport

```bash
mvn allure:serve
```