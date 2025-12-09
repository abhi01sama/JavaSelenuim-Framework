package jam;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

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
		try {
			// wait for full page load and spinner invisibility (if any)
			waitForPageLoad();
			// ensure spinner (animation) is not blocking
			try {
				waitForElementToDisappear(spiner);
			} catch (Exception ignore) {
				// spinner may not be present; ignore
			}
			// wait until at least one product element is present
			WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(20));
			wait.until(org.openqa.selenium.support.ui.ExpectedConditions.numberOfElementsToBeMoreThan(productby, 0));
		} catch (Exception e) {
			// diagnostic output to help find why products didn't load
			try {
				System.out.println("[ProductCatalogue] Timeout waiting for product elements. Current URL: " + driver.getCurrentUrl());
				System.out.println("[ProductCatalogue] Page title: " + driver.getTitle());
				String src = driver.getPageSource();
				System.out.println("[ProductCatalogue] page source snapshot (first 2000 chars):\n" + (src.length() > 2000 ? src.substring(0, 2000) : src));
			} catch (Exception inner) {
				System.out.println("[ProductCatalogue] Failed to read page context: " + inner.getMessage());
			}
			throw e;
		}
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