package jam.pageobjects;

import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.Test;

import jam.CartPage;
import jam.ProductCatalogue;
import jam.TestComponents.BaseTest;
import jam.TestComponents.Retry;

public class ErrorValidationsTest extends BaseTest {

    private final String VALID_EMAIL = "abhi01sama@gmail.com";
    private final String VALID_PASSWORD = "sR123456";
    private final String INVALID_PASSWORD = "sR1234567";
    private final String PRODUCT_NAME = "ZARA COAT 3";

    @Test(groups = {"ErrorValidation"},retryAnalyzer = Retry.class)
    public void ErrorVal() throws IOException, InterruptedException {
        // Login with wrong credentials and validate error message
        try {
            landingPage.loginApplication(VALID_EMAIL, INVALID_PASSWORD);
            // if loginApplication did not throw, the login unexpectedly succeeded
            Assert.fail("Expected login to fail with incorrect credentials but it succeeded.");
        } catch (RuntimeException e) {
            // LandingPage throws "Login failed: <error message>" on failed login
            String msg = e.getMessage();
            String prefix = "Login failed: ";
            String err = msg != null && msg.startsWith(prefix) ? msg.substring(prefix.length()) : msg;
            Assert.assertTrue(err != null && err.trim().toLowerCase().contains("failed"),
                    "Expected an 'incorrect' error message but got: '" + err + "'");
        }
    }

    @Test(groups = {"ErrorValidation"})
    public void productErrorValidationTest() throws IOException, InterruptedException {
        // Login with valid credentials
        ProductCatalogue productCatalogue = landingPage.loginApplication(VALID_EMAIL, VALID_PASSWORD);

        // Add product to cart
        productCatalogue.addProductToCart(PRODUCT_NAME);
        Thread.sleep(2000);

        // Go to cart page and verify product
        CartPage cartPage = productCatalogue.goToCartPage();
        boolean match = cartPage.VerifyProductDisplay(PRODUCT_NAME);
        Thread.sleep(2000);
        Assert.assertTrue(match, "Product not displayed in the cart!");
    }
}