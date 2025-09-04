@smoke @login
Feature: Login
  Scenario: Invalid login
    Given User opens nopCommerce home
    When User clicks login link
    And enters email "wrong@test.com" and password "badpass"
    Then Error message is shown
    
  Scenario:valid login
    Given User opens nopCommerce home
    When User clicks login link
    And enters email "xyz@gmail.com" and password "xyz@12"
    Then User is logged in successfully
