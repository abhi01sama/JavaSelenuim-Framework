package jam.stepDefinitions;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jam.CartPage;
import jam.CheckoutPage;
import jam.ConfirmationPage;
import jam.LandingPage;
import jam.ProductCatalogue;
import jam.TestComponents.BaseTest;

public class stepDefinitionImpl extends BaseTest {

    public LandingPage landingPage;
    public ProductCatalogue productCatalogue;
    public ConfirmationPage confirmationPage;
    public String loginErrorMessage;   // <-- Added for error validation

    @Given("I landed on Ecommerce Page")
    public void I_landed_on_Ecommerce_Page() throws IOException {
        landingPage = launchApplication();
    }

    @Given("^Logged in with username (.+) and password (.+)$")
    public void logged_in_username_and_password(String username, String password) {

        try {
            productCatalogue = landingPage.loginApplication(username, password);

        } catch (RuntimeException e) {

            // Extract actual error message
            String msg = e.getMessage();
            String prefix = "Login failed: ";

            loginErrorMessage = msg != null && msg.startsWith(prefix)
                    ? msg.substring(prefix.length())
                    : msg;
        }
    }

    @When("^I add product (.+) to Cart$")
    public void I_add_product_to_Cart(String productName) {
        List<WebElement> products = productCatalogue.getProductList();
        productCatalogue.addProductToCart(productName);
    }

    @When("^Checkout (.+) and submit the order$")
    public void Checkout_and_submit_the_order(String productName) {
        CartPage cartPage = productCatalogue.goToCartPage();
        Boolean match = cartPage.VerifyProductDisplay(productName);
        Assert.assertTrue(match);

        CheckoutPage checkoutPage = cartPage.goToCheckout();
        checkoutPage.selectCountry("India");
        confirmationPage = checkoutPage.submitOrder();
    }

    @Then("{string} message is displayed on ConfirmationPage")
    public void message_is_displayed_on_ConfirmationPage(String expectedMessage) {
        String confirmMessage = confirmationPage.getConfirmationMessage();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase(expectedMessage));
    }

    @Then("{string} message is displayed")
    public void message_is_displayed(String expectedMessage) {
        Assert.assertTrue(
                loginErrorMessage != null 
                && loginErrorMessage.toLowerCase().contains(expectedMessage.toLowerCase()),
                "Expected message to contain: " + expectedMessage 
                + " but got: " + loginErrorMessage
        );
    }
}
