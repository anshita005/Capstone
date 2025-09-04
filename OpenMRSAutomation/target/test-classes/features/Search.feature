Feature: Search
  Background:
    Given user is on login
    When login default
    Then home visible

  Scenario: Open existing patient from search
    When open search
    And search "Jane Smith" and open
    Then details header has "Jane Smith"
