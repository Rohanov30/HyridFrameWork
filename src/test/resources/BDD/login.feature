Feature: Login functionality (TDD)

  Scenario Outline: Login with different user types
    Given I am on the Swag Labs login page
    When I login with username "<username>" and password "<password>"
    Then I should see the expected result for "<username>"

    Examples:
      | username                | password      |
      | standard_user           | secret_sauce  |
      | locked_out_user         | secret_sauce  |
      | problem_user            | secret_sauce  |
      | performance_glitch_user | secret_sauce  |
      | error_user              | secret_sauce  |
      | visual_user             | secret_sauce  |
