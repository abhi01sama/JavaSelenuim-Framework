package jam.TestComponents;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.openqa.selenium.edge.EdgeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import jam.LandingPage;

public class BaseTest {
	public WebDriver driver;
	public LandingPage landingpage;

	public WebDriver InitializeDriver() throws IOException {
		Properties pro = new Properties();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "//src//main//java//jam//resources//GlobalData.properties");
		pro.load(fis);

		String browserName = pro.getProperty("browser");

		if (browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		} else {
			throw new IllegalArgumentException("Browser not supported: " + browserName);
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
	}
@BeforeMethod
	public LandingPage lunchApplication() throws IOException {
		driver = InitializeDriver();
		landingpage = new LandingPage(driver);
		landingpage.goTo();
		return landingpage;

	}
@AfterMethod
public void tearDown()
{
	driver.close();
}
}
