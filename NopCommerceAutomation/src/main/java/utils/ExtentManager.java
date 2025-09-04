package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.nio.file.Paths;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            String reportPath = Paths.get("reports", "extent-report.html").toString();
            ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
            reporter.config().setReportName("NopCommerce Automation Report");
            extent = new ExtentReports();
            extent.attachReporter(reporter);
        }
        return extent;
    }
}
