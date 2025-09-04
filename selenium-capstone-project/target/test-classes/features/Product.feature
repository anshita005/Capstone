Feature: Amazon Product Detail Page Functionality
  As a user
  I want to view product details
  So that I can make informed purchase decisions

  Background:
    Given I am on Amazon homepage

  @smoke @product
  Scenario: View product details
    When I search for "bluetooth speaker"
    Then I should see search results
    When I click on the first search result
    Then I should be on the product detail page
    And I should see the product title
    And I should see the product price
    And I should see Add to Cart button
    And I should see product availability status

  @product @regression
  Scenario: Add product to cart from product page
    When I search for "phone case"
    Then I should see search results
    When I click on the first search result
    Then I should be on the product detail page
    When I click on Add to Cart button
    Then the product should be added to cart

  @product @cart
  Scenario: Complete product selection and add to cart flow
    When I search for "computer mouse"
    Then I should see search results
    When I click on the first search result
    Then I should be on the product detail page
    And I should see product details
    When I add the product to cart
    Then the product should be added to cart