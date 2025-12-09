@tag 
Feature: Purchase The order from ecommerce website

Background:
Given I landed on Ecommerce Page

@Regression
Scenario Outline: Positive Test of Submitting the Order
Given Logged in with username <name> and password <password>
When I add product <productName> to Cart
And Checkout <productName> and submit the order
Then "Thankyou for the order." message is displayed on ConfirmationPage

Examples:
| name                    | password | productName  |
| abhi01sama@gmail.com    | sR123456 | ZARA COAT 3  |
