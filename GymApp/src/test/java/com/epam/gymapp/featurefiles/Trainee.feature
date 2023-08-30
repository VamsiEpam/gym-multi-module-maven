Feature: Trainer
  Scenario: registerTrainee
    Given : Execute registerTrainee
    Then : CredentialDetails is returned
  Scenario: deleteTrainee
    Given : Execute deleteTrainee
    Then : Http No content status is returned
