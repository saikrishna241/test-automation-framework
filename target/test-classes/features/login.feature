Feature: API test

  Scenario: Valid login returns token
    When I send a login POST request with username "admin" and password "pass"
    Then I should receive a JWT token

  Scenario: Valid token allows profile access
    Given I have a valid token
    When I send a GET request to the profile endpoint
    Then the response should contain username "admin" and role "admin"
