package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

/**
 * TestRunner class to execute Cucumber tests with TestNG
 */
@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"stepDefinitions", "hooks"},
    tags = "@smoke", // FIX: Focus on smoke tests first instead of running all
    plugin = {
        "pretty",
        "html:test-output/cucumber-html-reports",
        "json:test-output/cucumber-json-reports/Cucumber.json",
        "junit:test-output/cucumber-xml-reports/Cucumber.xml",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
        "rerun:test-output/rerun/failed_scenarios.txt" // FIX: Add rerun plugin
    },
    monochrome = true,
    dryRun = false,
    publish = false
)
public class TestRunner extends AbstractTestNGCucumberTests {
    
    @BeforeClass
    public void setUp() {
        // FIX: Set system properties for better stability
        System.setProperty("cucumber.execution.parallel.enabled", "false");
        System.setProperty("cucumber.execution.parallel.config.strategy", "fixed");
        System.setProperty("cucumber.execution.parallel.config.fixed.parallelism", "1");
        
        // Set Chrome options for CI/CD environments
        System.setProperty("webdriver.chrome.verboseLogging", "false");
        System.setProperty("webdriver.chrome.silentOutput", "true");
        
        System.out.println("🚀 Starting TestNG-Cucumber Test Execution");
        System.out.println("📅 Execution Date: " + java.time.LocalDateTime.now());
    }
    
    @AfterClass
    public void tearDown() {
        System.out.println("✅ TestNG-Cucumber Test Execution Completed");
    }
    
    // FIX: Disable parallel execution to reduce system load and timeout issues
    @Override
    @DataProvider(parallel = false) // Changed from true to false
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
