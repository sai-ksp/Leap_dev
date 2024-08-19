Feature: Get or create Employee ID

Background:
  * def baseUrl = karate.env
  * url baseUrl
  * def data = read('classpath:Features/data.json')
  * configure headers = { 'Content-Type': 'application/json' }

Scenario: Create a new employee and store the ID
  Given url baseUrl
  And request data.validData[0]
  When method POST
  Then status 201
  * def employeeId = response._id
  * karate.set('employeeId', employeeId)
  * def result = { employeeId: employeeId }
  * karate.signal(result)
