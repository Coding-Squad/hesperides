Feature: Get modules name

  Background:
    Given an authenticated user

  Scenario: get a list of all the modules names
    Given a list of 12 modules with different names
    When I get the modules name
    Then a list of 12 elements is returned

  Scenario: get a list of all the modules name
    Given a list of 12 modules with the same name
    When I get the modules name
    Then a list of 1 element is returned