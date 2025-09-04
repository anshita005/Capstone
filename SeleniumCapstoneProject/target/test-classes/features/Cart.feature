Feature: Amazon Shopping Cart Functionality
  As a user
  I want to manage items in my shopping cart
  So that I can review and purchase products

  Background:
    Given I am on Amazon homepage

  @cart @smoke
  Scenario: Add item to cart and view cart
    When I search for "wireless keyboard"
    And I click on the first search result
    And I add the product to cart
    Then the product should be added to cart
    When I click on cart icon
    Then I should see the cart page
    And I should see items in cart
    And I should see cart subtotal

  @cart @checkout
  Scenario: Proceed to checkout
    When I search for "USB cable"
    And I click on the first search result
    And I add the product to cart
    Then the product should be added to cart
    When I click on cart icon
    Then I should see the cart page
    And I should see Proceed to Checkout button

  @cart
  Scenario: Cart shows item count
    When I search for "keyboard"
    And I click on the first search result
    And I add the product to cart
    Then the product should be added to cart
    And cart icon should show item count
