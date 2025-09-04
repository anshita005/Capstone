Feature: Amazon Shopping Cart Functionality
  As a user
  I want to manage items in my shopping cart
  So that I can review and purchase selected products

  Background:
    Given I am on Amazon homepage

  @smoke @cart
  Scenario: Add item to cart and view cart
    When I search for "tablet"
    Then I should see search results
    When I click on the first search result
    Then I should be on the product detail page
    When I add the product to cart
    Then the product should be added to cart
    When I click on cart icon
    Then I should see the cart page
    And I should see items in cart
    And I should see cart subtotal

  @cart @regression
  Scenario: View cart details
    When I search for "keyboard"
    Then I should see search results
    When I click on the first search result
    Then I should be on the product detail page
    When I add the product to cart
    Then the product should be added to cart
    When I navigate to cart
    Then I should be on the cart page
    And cart should not be empty
    And I should see cart item details
    And I should see Proceed to Checkout button

  @cart @checkout
  Scenario: Proceed to checkout from cart
    When I search for "USB cable"
    Then I should see search results
    When I click on the first search result
    Then I should be on the product detail page
    When I add the product to cart
    Then the product should be added to cart
    When I click on cart icon
    Then I should see the cart page
    And I should see Proceed to Checkout button

  @cart
  Scenario: Cart shows item count
    When I search for "power bank"
    Then I should see search results
    When I click on the first search result
    Then I should be on the product detail page
    When I add the product to cart
    Then the product should be added to cart
    And cart icon should show item count