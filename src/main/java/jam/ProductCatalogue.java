package jam;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import jam.AbstractComponents.AbstractComponent;

public class ProductCatalogue extends AbstractComponent {
	WebDriver driver;

	public ProductCatalogue(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = ".mb-3")
	List<WebElement> products;
	@FindBy(css = ".ng-animating")
	WebElement spiner;
	By productby = By.cssSelector(".mb-3");
	By addProductToCart = By.cssSelector(".card-body button:last-of-type");
	By toastmessage = By.cssSelector("#toast-container");
	
	
	

	public List<WebElement> getProductList() {
		waitForElementToAppear(By.cssSelector(".mb-3"));
		return products;
	}

	public WebElement getProductByName(String productName) {
		WebElement prod = getProductList().stream()
				.filter(product -> product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst()
				.orElse(null);
		return prod;

	}
	public void addProductToCart(String productName) {
		WebElement prod = getProductByName(productName);
		prod.findElement(addProductToCart).click();
		waitForElementToAppear(toastmessage);
		waitForElementToDisappear(spiner);
	}

}
