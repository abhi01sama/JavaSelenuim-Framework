package jam;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import jam.AbstractComponents.AbstractComponent;

public class CartPage extends AbstractComponent{

	public CartPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	WebDriver driver;
	@FindBy(css=".totalRow button")
	WebElement checkoutEle;
	@FindBy(css=".cartSection h3")
	private List<WebElement> cartTitle; 

	public Boolean VerifyProductDisplay(String productName ) {
		
		Boolean match = cartTitle.stream().anyMatch(product->product.getText().equalsIgnoreCase(productName));
		return match;
	}
	public CheckoutPage goToCheckout() {
		checkoutEle.click();
		return new CheckoutPage(driver);
		
	}

}
