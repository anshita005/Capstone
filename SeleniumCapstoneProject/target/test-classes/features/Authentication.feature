Feature: Amazon User Authentication
  As a user
  I want to login and register on Amazon
  So that I can access authorized features like add to cart

  Scenario: Login with valid credentials
    Given I am on Amazon login page
    When I enter email "anshitasharma2002@gmail.com"
    And I click continue button
    And I enter password "Avenger@123"
    And I click sign in button
    Then I should be logged in successfully

  Scenario: Register new user with valid details
    Given I start registration
    When I fill registration form with name "John Doe", email "john@example.com", password "Pass123", and confirm password "Pass123"
    And I submit the registration form
    Then registration should succeed
