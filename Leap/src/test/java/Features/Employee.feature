Feature: Operations on Employee using Karate Framework

Background:
  * def baseUrl = karate.env
  * url baseUrl
  * def data = read('classpath:Features/data.json')
  * configure headers = { 'Content-Type': 'application/json' }

Scenario Outline: Create a new employee - Positive
  Given url baseUrl
  And request <employee>
  When method POST
  Then status 201
  * def employeeId = response._id
  * karate.set('employeeId', employeeId)  // Store employeeId globally
  * def createdEmployee = response
  * print 'Created EmployeeID:', employeeId
  * match employeeId != null

Examples:
  | employee           |
  | data.validData[0]  |
  | data.validData[1]  |

Scenario Outline: Create a new employee - Invalid Data
  Given url baseUrl
  And request <employee>
  When method POST
  And match responseStatus == 201
  
  # Capture and print the status code
  * def statusCode = responseStatus
  * print 'Status Code:', statusCode

 # Conditional logic to handle unexpected status codes
* if (statusCode == 201) karate.log('BUG: The API accepted invalid data and returned 201 Created, which is incorrect behavior.')
* if (statusCode == 404) karate.log('Invalid data: Resource not found, check the employee ID or URL')
* if (statusCode == 400) karate.log('Expected failure: Bad Request due to invalid data')


  # Conditional logic to handle unexpected status codes
  # if (statusCode == 201) karate.log('BUG: The API accepted invalid data and returned 201 Created, which is incorrect behavior.');
  # if (statusCode == 404) karate.log('Invalid data: Resource not found, check the employee ID or URL');
  # if (statusCode == 400) karate.log('Expected failure: Bad Request due to invalid data');

Examples:
  | employee           |
  | data.invalidData[0]  |
  | data.invalidData[1]  |

Scenario: Get and validate the newly created employee
  * def result = callonce read('classpath:Features/getEmployeeId.feature')
  * def employeeId = result.employeeId
  * match employeeId != null
  Given url baseUrl + employeeId
  When method GET
  Then status 200
  * def Emp = response
  * print 'Employee Found:', Emp
  
  # Assertions to validate the data only with
  And match Emp._id == employeeId
 
Scenario: Update an employee
  * def result = callonce read('classpath:Features/getEmployeeId.feature')
  * def employeeId = result.employeeId
  * match employeeId != null
  
  Given url baseUrl + employeeId
  And request data.updateData[0]
  When method PUT
  Then status 200
  * def partialResponse = response
  * print 'Partial update response:', partialResponse

  # Validate that the employee has been updated
  Given url baseUrl + employeeId
  When method GET
  Then status 200
  * def updatedEmployee = response
  * print 'Updated employee details:', updatedEmployee
  
  # Debug: Print the actual response structure
  * print 'Full response:', response
  
  # Full validation (Ensure these fields exist in the response)
  And match response._id == employeeId
  # Implicitly matching response data to the updated data
  And match response contains data.updateData[0]
  * print data.updateData[0]
  # Explicitly  matching response data to the updated data
  And match response contains {UpdatedFirstName:"Kumar",UpdatedLastName:"Varanasi",UpdatedJobTitle:"Senior Quality Engineer",UpdatedEmail:"kumar.varanasi@example.com",UpdatedBaseSalary:60000}
  


Scenario: Delete an employee
  * def result = callonce read('classpath:Features/getEmployeeId.feature')
  * def employeeId = result.employeeId
  * match employeeId != null
  Given url baseUrl + employeeId
  When method DELETE
  Then status 200
  * print 'Deleted employee ID:', employeeId

  # Validate that the employee has been deleted
  Given url baseUrl + employeeId
  When method GET
  Then status 404
  * print 'Employee successfully deleted, no data found'
