package jam;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import jam.AbstractComponents.AbstractComponent;

public class OrderPage extends AbstractComponent {

    public OrderPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".totalRow button")
    private WebElement checkoutEle;

    @FindBy(css = "tr td:nth-child(3)")
    private List<WebElement> productNames;

    public boolean verifyOrderDisplayed(String productName) {
        // ensure order rows are loaded before checking
        waitForElementToAppear(By.cssSelector("tr td:nth-child(3)"));
        // debug: log product names found in orders page
        List<String> names = productNames.stream().map(WebElement::getText).toList();
        System.out.println("[OrderPage] product names found in order history: " + names);
        return names.stream().anyMatch(name -> name.equalsIgnoreCase(productName));
    }

    public void clickCheckout() {
        checkoutEle.click();
    }
}