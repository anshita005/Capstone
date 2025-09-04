package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"stepDefinitions", "hooks"},  // Make sure both stepDefinitions and hooks are included
    tags = "@smoke",
    plugin = {
        "pretty",
        "html:test-output/cucumber-html-reports",
        "json:test-output/cucumber-json-reports/Cucumber.json",
        "junit:test-output/cucumber-xml-reports/Cucumber.xml",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
        "rerun:test-output/rerun/failed_scenarios.txt"
    },
    monochrome = true,
    dryRun = false,
    publish = false
)
public class TestRunner extends AbstractTestNGCucumberTests {
    
    @BeforeClass
    public void setUp() {
        System.setProperty("cucumber.execution.parallel.enabled", "false");
        System.setProperty("cucumber.execution.parallel.config.strategy", "fixed");
        System.setProperty("cucumber.execution.parallel.config.fixed.parallelism", "1");
        
        System.setProperty("webdriver.chrome.verboseLogging", "false");
        System.setProperty("webdriver.chrome.silentOutput", "true");
        
        System.out.println("🚀 Starting TestNG-Cucumber Test Execution");
        System.out.println("📅 Execution Date: " + java.time.LocalDateTime.now());
    }
    
    @AfterClass
    public void tearDown() {
        System.out.println("✅ TestNG-Cucumber Test Execution Completed");
    }
    
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
