@regression @search
Feature: Search
  Scenario: Search existing product
    Given User opens nopCommerce home
    When User searches for "Apple Macbook Pro"
    Then Results are shown
