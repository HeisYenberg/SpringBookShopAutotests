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
- ___

## Features

- **Full Functional Coverage**: Automated tests for critical workflows:
    - User authentication and session management.
    - Cart operations, including adding and removing books.
    - Admin functionality for book management (add, edit, delete books).
- **Database Interactions**: Directly validate and manipulate database states
  during tests for advanced scenarios.
- **JSON Deserialization**: Parse and validate JSON responses for API-related UI
  validations.
- **Logging Support**: Comprehensive logging using SLF4J for test execution,
  debugging, and error tracking.
- **Selenide for UI Interactions**: Simplifies test creation with clean and
  expressive syntax.
- **TestNG for Test Management**: Supports test organization, parameterization,
  and parallel execution.
- **Allure Reports**: Generate detailed and visually appealing test execution
  reports.
- **Scalable and Configurable**: Easily extendable for new features and
  adaptable to various environments.
- **Selenoid Integration**: Run tests in containerized environments using
  Selenoid, enhancing test stability and consistency.
- **Docker Compose Setup**: Automated environment setup with Docker Compose,
  including Selenoid, Selenoid UI, and Allure server on port 9090.

---

## Prerequisites

- **Java 8 or later**
- **Maven**: For building the project and managing dependencies.
- **Docker**: For running Selenoid, Selenoid UI, and tests within containers.
- **Browser Driver**: *(If not using Selenoid)* Ensure the required browser
  driver (e.g., ChromeDriver) is available in the system PATH or configured in
  the framework.

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
#BASE_URL will be used if set, else selenide.remote property will be read
selenide.baseUrl=http://localhost:8080
selenide.browser=chrome
selenide.headless=true
#SELENOID_URL will be used if set, else selenide.remote property will be read
selenide.remote=http://localhost:4444/wd/hub
```

Update src/test/resources/datasource.properties with your database-specific
details:

```properties
#DATASOURCE_URL will be used if set, else selenide.remote property will be read
datasource.url=jdbc:postgresql://localhost:5432/spring_book
datasource.username=postgres
datasource.password=postgres
datasource.driver_class_name=org.postgresql.Driver
```

### 4. Execute tests (Local)

```bash
mvn clean test
```

### 5. Generate AllureReport (Local)

```bash
mvn allure:serve
```

### 6. Running tests in Selenoid and run Allure server with report (Docker)

```bash
docker compose up
```

### 7. Access Test Reports and Selenoid UI (Docker)

Allure Test Report: Open your browser and navigate to http://localhost:9090 to
view the Allure report.

Selenoid UI: Navigate to http://localhost:8080 to access the Selenoid UI for
real-time monitoring of browser sessions.
