Feature: Trainer
  Scenario: registerTrainee
    Given : Execute registerTrainee
    Then : CredentialDetails is returned
  Scenario: deleteTrainee
    Given : Exceute deletTrainee
    Then : Http No content status is returned
