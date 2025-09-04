@smoke @login
Feature: User Login

  Background:
    Given User is on Login page

  Scenario: Successful login with valid credentials
    When User logs in with username "standard_user" and password "secret_sauce"
    Then User should land on Products page

  Scenario: Unsuccessful login with invalid credentials
    When User logs in with username "locked_out_user" and password "secret_sauce"
    Then Error message "Sorry, this user has been locked out" should be displayed
