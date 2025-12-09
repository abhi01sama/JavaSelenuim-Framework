package jam.AbstractComponents;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import jam.CartPage;
import jam.OrderPage;

public class AbstractComponent {
	WebDriver driver;
	WebDriverWait wait;

	// Spinner locator
	By spinnerOverlay = By.cssSelector(".ngx-spinner-overlay");

	public AbstractComponent(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = "[routerlink*='cart']")
	WebElement cartHeader;

	@FindBy(css = "[routerlink*='myorders']")
	WebElement orderHeader;

	public void waitForElementToAppear(By findBy) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}

	public void waitForElementToAppear(WebElement findBy) {
		wait.until(ExpectedConditions.visibilityOf(findBy));
	}

	// 🔥 NEW METHOD: Wait until Angular spinner overlay disappears
	public void waitForSpinnerToDisappear() {
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(spinnerOverlay));
		} catch (Exception e) {
			System.out.println("[INFO] Spinner already gone or not present.");
		}
	}

	public CartPage goToCartPage() {

		// ✔ Wait for spinner before clicking
		waitForSpinnerToDisappear();

		cartHeader.click();
		return new CartPage(driver);
	}

	public OrderPage goToOrderPage() {

		waitForSpinnerToDisappear();

		orderHeader.click();
		return new OrderPage(driver);
	}

	public void waitForElementToDisappear(WebElement ele) {
		WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(5));
		w.until(ExpectedConditions.invisibilityOf(ele));
	}

	public void waitForPageLoad() {
		try {
			WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(30));
			w.until(webDriver -> ((JavascriptExecutor) webDriver)
					.executeScript("return document.readyState")
					.equals("complete"));
		} catch (Exception e) {
			System.out.println("[AbstractComponent] waitForPageLoad timed out: " + e.getMessage());
		}
	}
}
