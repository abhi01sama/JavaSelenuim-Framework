package jam;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import jam.AbstractComponents.AbstractComponent;

public class LandingPage extends AbstractComponent {

	WebDriver driver;
	public LandingPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "userEmail")
	WebElement userEmail;

	@FindBy(id = "userPassword")
	WebElement password;

	@FindBy(id = "login")
	WebElement submit;

	@FindBy(css = "[class*='flyInOut']")
	WebElement errorMessage;

	public ProductCatalogue loginApplication(String email, String pass) {
		userEmail.sendKeys(email);
		password.sendKeys(pass);
		submit.click();
		// wait for the next page to fully load before returning ProductCatalogue
		waitForPageLoad();
		// After submit, either the product catalogue should appear or an error message will show.
		try {
			waitForElementToAppear(By.cssSelector(".mb-3"));
			return new ProductCatalogue(driver);
		} catch (Exception e) {
			// if login failed, capture the error message and throw a clear exception
			try {
				String err = getErrorMessage();
				throw new RuntimeException("Login failed: " + err);
			} catch (Exception inner) {
				throw new RuntimeException("Login failed and product list did not load.", e);
			}
		}
	}

	public String getErrorMessage() {
		waitForElementToAppear(errorMessage);
		return errorMessage.getText();

	}

	public void goTo() {
		driver.get("https://rahulshettyacademy.com/client");

	}
}