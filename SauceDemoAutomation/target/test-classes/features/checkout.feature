@regression @checkout
Feature: Checkout flow

  Background:
    Given User is on Login page
    When User logs in with username "standard_user" and password "secret_sauce"
    Then User should land on Products page
    When User adds product "Sauce Labs Backpack" to the cart
    And User opens the cart
    Then Cart should contain at least one item
    And User proceeds to checkout

  Scenario: Successful purchase
    When User fills checkout info with "John", "Doe", "560001"
    And User completes the purchase
    Then Order success message "Thank you for your order!" should be shown
