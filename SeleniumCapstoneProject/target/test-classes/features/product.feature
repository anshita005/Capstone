Feature: Amazon Product Purchase
  As a user
  I want to search for a product and add it to the cart
  So that I can proceed to checkout

  Background:
    Given I am on Amazon login page

  Scenario: Add product to cart using search
    When I search for "laptop" and select first product
    And I add the product to cart
    Then I should be on the cart page

  Scenario: Add specific product to cart using direct link
    Given I navigate to product page "https://www.amazon.in/dp/B0C7SGYGYW"
    When I add the product to cart
    Then I should be on the cart page
