@regression @cart
Feature: Cart operations

  Background:
    Given User is on Login page
    When User logs in with username "standard_user" and password "secret_sauce"
    Then User should land on Products page

  Scenario: Add product to cart
    When User adds product "Sauce Labs Backpack" to the cart
    And User opens the cart
    Then Cart should contain at least one item
