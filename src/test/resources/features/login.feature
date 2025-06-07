Feature: Login

  Scenario: UI Login
    Given I open the login page
    When I enter username "admin" and password "pass"
    Then I should see the homepage

  Scenario: API Login
    When I send a login POST request with username "admin" and password "pass"
    Then the response status should be 200
