@regression @cart
Feature: Cart and Checkout
  Background:
    Given User opens nopCommerce home
    When User searches for "computer"
    Then Results are shown
    When User opens first product

  Scenario: Add to cart
    When User adds product to cart
    And User opens cart
    Then Cart has items

  Scenario: Checkout
    When User adds product to cart
    And User opens cart
    And User proceeds to checkout
    Then Checkout page should appear
