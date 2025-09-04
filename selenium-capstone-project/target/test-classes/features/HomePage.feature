Feature: Amazon Homepage Functionality
  As a user
  I want to access Amazon homepage
  So that I can browse and shop for products

  Background:
    Given I am on Amazon homepage

  @smoke @homepage
  Scenario: Verify Amazon homepage loads successfully
    Then I should see Amazon homepage loaded successfully
    And the page title should contain "Amazon"
    And I should see the search box
    And I should see the cart icon

  @homepage
  Scenario: Verify homepage elements are displayed
    Then I should see Amazon homepage loaded successfully
    And I should see the search box
    And I should see the cart icon

  @navigation
  Scenario: Navigate to Amazon homepage
    When I navigate to Amazon homepage
    Then I should see Amazon homepage loaded successfully
    And the page title should contain "Amazon"