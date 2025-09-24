package jam;

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
		ProductCatalogue productcatalogue = new ProductCatalogue(driver);
		return productcatalogue;
	}

	public String getErrorMessage() {
		waitForElementToAppear(errorMessage);
		return errorMessage.getText();

	}

	public void goTo() {
		driver.get("https://rahulshettyacademy.com/client");

	}
}
