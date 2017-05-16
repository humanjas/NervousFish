Feature: Multiple
  Perform multiple scenario's

  Scenario Outline: Input password and go to other or not
    Given I have a LoginActivity
    When I input password "<password>" and click submit
    Then I <should> continue to another activity

    Examples:
      | password  | should |
      | 59505  | false |
      | 12345  | true |
      | 23235  | false |