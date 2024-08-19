
# Leap_dev Task 2:

## Overview

Testing of Employee API using the Karate Framework. The tests cover the CRUD operations (Create, Read, Update, Delete) on employee data, ensuring the API behaves as expected.

## Project Structure

The project is organized into various components:

- **Java Classes**
  - `DynamicIdFetcher.java`: This class is responsible for dynamically fetching and managing IDs used across various operations.
  - `DynamicUrlHandler.java`: This class manages the dynamic URLs used in API calls, allowing for flexible and robust URL handling 
  - `TestNGRunner.java`: This class runs the test suite using TestNG, a popular testing framework in the Java ecosystem.

- **Feature Files**
  - `Employee.feature`: Contains test scenarios for operations related to employees GET,PUT,DELETE, excluding the creation operation.
  - `getEmployeeId.feature`: Contains the test scenario for creating a new employee POST (valid and invalid case).
  * Note as the negative case if failed because the API is accepting invalid data 

- **Configuration Files**
  - `config.properties`: Configuration file that contain base URLs path and dynamic id for the end points
  base.url=https://crudcrud.com/api/  
  dynamic.id=dynamic-id

- **Data Files**
  - `data.json`: JSON file containing test data used for creating and updating employee records. It includes valid data for positive test cases, invalid data for negative test cases, and updated data for PUT operations.

**Technologies Used**

- **Java 8+**: The primary programming language used in the project.
- **Karate Framework**: Used for API testing.
- **TestNG**: Used for running and managing test cases.
- **Maven**: Used for project management and build automation.


# Running the Test Suite

To execute all tests:

1. **Via Maven**:
   ```bash
   mvn test
   ```````````
2. **Via TestNG**:
   - Open `TestNGRunner.java` in your IDE.
   - Run the `testAll()` method to execute the test suite.

## Test Data

Test data is provided in the `data.json` file. This includes:

- **Valid Data**: Used for positive test scenarios.
- **Invalid Data**: Used for testing how the API handles invalid input.
- **Update Data**: Used in PUT operations to update employee records.

## Reporting

After running the tests, reports are generated in the `target/surefire-reports` directory. You can view the results by opening the `karate-summary.html` file in a web browser.
