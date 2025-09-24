package jam.pageobjects;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import jam.CartPage;
import jam.CheckoutPage;
import jam.ConfirmatioPage;
import jam.LandingPage;
import jam.ProductCatalogue;
import jam.TestComponents.BaseTest;

public class Submitoudertest extends BaseTest {

	@Test
	public void Submitorder() throws IOException, InterruptedException {

		String productname = "ZARA COAT 3";
		ProductCatalogue productcatalogue = landingpage.loginApplication("abhi01sama@gmail.com", "sR123456");
		List<WebElement> products = productcatalogue.getProductList();
		productcatalogue.addProductToCart(productname);
		CartPage cartpage = productcatalogue.goToCartPage();

		Boolean match = cartpage.VerifyProductDisplay(productname);
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartpage.goToCheckout();
		checkoutPage.selectCountry("India");
		ConfirmatioPage confirmationPage = checkoutPage.submitOrder();
    
		
		String Confirmessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(Confirmessage.equalsIgnoreCase("Thankyou for the order."));

	}

}
