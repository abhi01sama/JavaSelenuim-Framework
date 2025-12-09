@ErrorValidation
Feature: Error Validation
  I want to use this template for my feature file

  Scenario Outline: Login error validation
    Given I landed on Ecommerce Page
    And Logged in with username <name> and password <password>
    Then "Incorrect Email and Password" message is displayed

  Examples:
    | name                  | password   |
    | abhi01sama1@gmail.com | sR1234567  |



