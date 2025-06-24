Feature: API test

  Scenario Outline: Valid login returns token
    When I send a login POST request with username "<username>" and password "<password>"
    Then I should receive a JWT token
    And the response should match login schema
    Examples:
      | username | password |
      | testuser | testpass |
#      | admin    | pass     |

  Scenario: Valid token allows profile access
    Given I have a valid token
    When I send a GET request to the profile endpoint
#    Then the response should contain username "testuser" and role "admin"
    Then the response should match profile schema
    And the user "testuser" should exist in the database

#  Scenario Outline: Login with various invalid combinations
#    When I send a login POST request with username "<username>" and password "<password>"
#    Then the response status should be "fail"
#    And the status code should be 401
#
#    Examples:
#      | username  | password  |
#      | wronguser | pass      |
#      | admin     | wrongpass |
#      |           | pass      |
#      | admin     |           |
#      |           |           |
#      | wronguser | wrongpass |

