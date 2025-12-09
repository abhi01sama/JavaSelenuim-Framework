package jam.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import jam.LandingPage;

public class BaseTest {

	public WebDriver driver;
	public LandingPage landingPage;

	// ✅ Initialize browser driver dynamically
	public WebDriver initializeDriver() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "//src//main//java//jam//resources//GlobalData.properties");
		prop.load(fis);

		String browserName = System.getProperty("browser") != null ? System.getProperty("browser")
				: prop.getProperty("browser");

		if (browserName.toLowerCase().contains("chrome")) {
			ChromeOptions options = new ChromeOptions();

			if (browserName.toLowerCase().contains("headless")) {
				options.addArguments("headless");
				options.addArguments("window-size=1920,1080");
			}

			driver = new ChromeDriver(options);

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

	// ✅ Read JSON test data
	public List<HashMap<String, String>> getJsonData(String filePath) throws IOException {
		String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
		});
	}

	// ✅ Capture screenshot
	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destinationPath = System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
		FileUtils.copyFile(source, new File(destinationPath));
		return destinationPath;
	}

	// ✅ Launch app before each test
	@BeforeMethod(alwaysRun = true)
	public LandingPage launchApplication() throws IOException {
		driver = initializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.goTo();
		return landingPage;
	}

	// ✅ Close browser cleanly
	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult result) {
		if (driver != null) {
			driver.quit();
		}
	}
}
