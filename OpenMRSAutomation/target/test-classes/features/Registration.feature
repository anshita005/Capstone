
Feature: Registration
  Background:
    Given user is on login
    When login default
    Then home visible

  Scenario: Register a new patient
    When go to registration
    And register "Anshita" "Sharma" "Female" "15"-"May"-"1980" addr "123 Elm St" phone "1234567890"
    Then patient header contains "Anshita Sharma"
