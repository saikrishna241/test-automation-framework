Feature: UI Testing


  Scenario: Launch browser and perform Operations
    Given I open the website page
    Then I should see the homepage title as "omayo"

  @LaunchGoogleandPerformOperations
  Scenario: Login functionality
    Given I open the website page
    Then verify that "OmayoHome" page is displayed
    When user enters text "hello" in "textAreaField" control
    And verify that control "textfiled" contains "This is a sample text in the Page One." text
    Then select by "value" as "def" in "mainDropdown" control
    Then clear text on "textAreaField" control
