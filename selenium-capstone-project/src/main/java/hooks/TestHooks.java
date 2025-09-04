
package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.ConfigReader;
import utils.DriverManager;
import utils.Screenshot;

public class TestHooks {
    
    @Before
    public void setUp(Scenario scenario) {
        System.out.println("🏁 Starting scenario: " + scenario.getName());
        
        try {
            // Initialize WebDriver with only the specified browser
            String browser = ConfigReader.getBrowser();
            System.out.println("📋 Initializing browser: " + browser);
            DriverManager.initializeDriver(browser);
            
            // Create screenshot directory
            Screenshot.createScreenshotDirectory();
            
        } catch (Exception e) {
            System.err.println("❌ Setup failed for scenario: " + scenario.getName());
            System.err.println("Error details: " + e.getMessage());
            throw new RuntimeException("Test setup failed", e);
        }
    }
    
    @After
    public void tearDown(Scenario scenario) {
        System.out.println("🏁 Finishing scenario: " + scenario.getName());
        
        try {
            // Capture screenshot if scenario failed
            if (scenario.isFailed() && ConfigReader.isScreenshotOnFailure()) {
                String screenshotPath = Screenshot.captureScreenshot(scenario.getName());
                if (screenshotPath != null) {
                    System.out.println("📸 Screenshot saved for failed scenario: " + screenshotPath);
                }
            }
        } catch (Exception e) {
            System.err.println("⚠️ Error capturing screenshot: " + e.getMessage());
        } finally {
            // Always close WebDriver
            try {
                DriverManager.quitDriver();
            } catch (Exception e) {
                System.err.println("⚠️ Error closing WebDriver: " + e.getMessage());
            }
        }
    }
}
