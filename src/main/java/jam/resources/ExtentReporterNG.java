package jam.resources;

import java.io.File;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {
    static ExtentReports extent;

    public static ExtentReports getReportObject() {
        if (extent == null) {
            // ensure reports directory exists
            String reportsDirPath = System.getProperty("user.dir") + File.separator + "reports";
            File reportsDir = new File(reportsDirPath);
            if (!reportsDir.exists()) {
                reportsDir.mkdirs();
            }

            String path = reportsDirPath + File.separator + "index.html";
            ExtentSparkReporter reporter = new ExtentSparkReporter(path);
            reporter.config().setReportName("Web Automation Results");
            reporter.config().setDocumentTitle("Test Results");

            extent = new ExtentReports();
            extent.attachReporter(reporter);
            extent.setSystemInfo("Tester", "Abhishek Kumar Mishra");
        }
        return extent;
    }

}