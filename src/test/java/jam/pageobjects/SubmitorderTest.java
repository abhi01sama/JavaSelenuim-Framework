package jam.pageobjects;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import jam.CartPage;
import jam.CheckoutPage;
import jam.ConfirmationPage;
import jam.ProductCatalogue;
import jam.OrderPage;
import jam.TestComponents.BaseTest;

public class SubmitorderTest extends BaseTest {

    String productName = "ZARA COAT 3";

    @Test(dataProvider = "getData", groups = {"Purchase"})
    public void submitOrder(HashMap<String, String> input) throws IOException, InterruptedException {

        ProductCatalogue productCatalogue = 
                landingPage.loginApplication(input.get("email"), input.get("password"));

        // Fetch product list (optional)
        List<WebElement> products = productCatalogue.getProductList();

        // Add product to cart
        productCatalogue.addProductToCart(input.get("productName")); // corrected key

        // Go to cart page
        CartPage cartPage = productCatalogue.goToCartPage();

        // Verify product in cart
        Boolean match = cartPage.VerifyProductDisplay(input.get("productName"));
        Assert.assertTrue(match, "Product not found in cart: " + input.get("productName"));

        // Checkout
        CheckoutPage checkoutPage = cartPage.goToCheckout();
        checkoutPage.selectCountry("India");

        // Place order
        ConfirmationPage confirmationPage = checkoutPage.submitOrder();

        // Confirmation message
        String confirmMessage = confirmationPage.getConfirmationMessage();
        Assert.assertTrue(
                confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."),
                "Message mismatch. Actual: " + confirmMessage
        );
    }


    @Test(dependsOnMethods = {"submitOrder"})
    public void orderHistoryTest() throws IOException, InterruptedException {
        // use the same test data (first entry) that submitOrderTest used so the order exists
        List<HashMap<String, String>> data = getJsonData(
                System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "java" + File.separator + "jam" + File.separator + "data" + File.separator + "PurchaseOrder.json");
        HashMap<String, String> first = data.get(0);
        ProductCatalogue productCatalogue = landingPage.loginApplication(first.get("email"), first.get("password"));
        OrderPage orderPage = productCatalogue.goToOrderPage();
        Assert.assertTrue(orderPage.verifyOrderDisplayed(first.get("productName")));
    }

    @DataProvider
    public Object[][] getData() throws IOException {
        List<HashMap<String, String>> data = getJsonData(
                System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "java" + File.separator + "jam" + File.separator + "data" + File.separator + "PurchaseOrder.json");

        return new Object[][]{
                {data.get(0)},
                {data.get(1)}
        };
    }
}