Feature: User
  Scenario: user login
    Given : Execute login Method
    Then : http ok status returned
  Scenario: user change password
    Given : Execute update method
    Then : http ok status returned
