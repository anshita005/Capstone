Feature: Amazon Product Search Functionality
  As a user
  I want to search for products on Amazon
  So that I can find items I want to purchase

  Background:
    Given I am on Amazon homepage

  @smoke @search
  Scenario: Search for a product
    When I search for "laptop"
    Then I should see search results
    And I should see at least 1 search results
    And search results should contain product titles

  @search @regression
  Scenario Outline: Search for different products
    When I search for "<product>"
    Then I should see search results for "<product>"
    And I should see at least 5 search results
    And search results should contain product titles
    And I should be able to see product prices

    Examples:
      | product    |
      | iPhone     |
      | headphones |
      | books      |
      | shoes      |

  @search @navigation
  Scenario: Search and view product details
    When I search for "wireless mouse"
    Then I should see search results
    When I click on the first search result
    Then I should be on the product detail page
    And I should see product details