package jam.TestComponents;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import jam.resources.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener {

    ExtentReports extent = ExtentReporterNG.getReportObject();
    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.get().fail(result.getThrowable());
        WebDriver driver = null;

        try {
            // Access 'driver' declared in BaseTest superclass
            driver = (WebDriver) result.getInstance()
                    .getClass()
                    .getSuperclass()
                    .getDeclaredField("driver")
                    .get(result.getInstance());
        } catch (Exception e1) {
            System.out.println("Driver not available for screenshot in test: " 
                    + result.getMethod().getMethodName() + " - " + e1.getMessage());
        }

        if (driver != null) {
            try {
                String filePath = getScreenshot(result.getMethod().getMethodName(), driver);
                extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
            } catch (Exception e) {
                System.out.println("Screenshot capture failed for test: " 
                        + result.getMethod().getMethodName() + " - " + e.getMessage());
            }
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
