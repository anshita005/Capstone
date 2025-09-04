
Feature: Login
  Scenario: Login with default credentials
    Given user is on login
    When login default
    Then home visible
