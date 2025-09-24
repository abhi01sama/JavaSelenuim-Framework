package jam.pageobjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class Register {

	public static void main(String[] args) {
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://rahulshettyacademy.com/client");
		driver.findElement(By.linkText("Register")).click();
		driver.findElement(By.id("firstName")).sendKeys("Abhishek");
		driver.findElement(By.id("lastName")).sendKeys("Mishra");
		driver.findElement(By.id("userEmail")).sendKeys("abhi01sama@gmail.com");
		driver.findElement(By.id("userMobile")).sendKeys("9876543211");
		
		WebElement dropdown = driver.findElement(By.xpath("//select[@formcontrolname='occupation']"));
		Select select = new Select(dropdown);
		select.selectByVisibleText("Engineer");

		driver.findElement(By.xpath("//input[@value=\"Male\"]")).click();
		driver.findElement(By.id("userPassword")).sendKeys("sR123456");
		driver.findElement(By.id("confirmPassword")).sendKeys("sR123456");
		driver.findElement(By.xpath("//input[@type='checkbox' and @formcontrolname='required']")).click();
		driver.findElement(By.xpath("//input[@type=\"submit\"]")).click();
		WebElement heading = driver.findElement(By.xpath("//h1[@class='headcolor']"));
		String message = heading.getText();
		System.out.println("Message: " + message);
	}

}
