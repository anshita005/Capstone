package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import pages.LoginPage;
import utils.ConfigReader;
import utils.DriverManager;
import utils.Screenshot;

public class TestHooks {
    
    @Before(order = 0)  // Run first - setup driver
    public void setUp(Scenario scenario) {
        System.out.println("Starting scenario: " + scenario.getName());
        
        try {
            // Ensure driver is properly initialized
            if (DriverManager.getDriver() == null) {
                String browser = ConfigReader.getBrowser();
                System.out.println("Initializing browser: " + browser);
                DriverManager.initializeDriver(browser);
                
                // Verify driver was created
                if (DriverManager.getDriver() == null) {
                    throw new RuntimeException("Driver initialization failed - driver is still null");
                }
                
                System.out.println("✅ WebDriver initialized successfully");
                Screenshot.createScreenshotDirectory();
            } else {
                System.out.println("✅ WebDriver already initialized, reusing existing driver");
            }
            
        } catch (Exception e) {
            System.err.println("❌ Setup failed for scenario: " + scenario.getName());
            System.err.println("❌ Error details: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Test setup failed", e);
        }
    }
    
    @Before(value = "@cart or @product or @auth", order = 1)  // Run second - login for specific tests
    public void loginForTests(Scenario scenario) {
        try {
            System.out.println("🔐 Auto-login triggered for scenario: " + scenario.getName());
            
            // Verify driver exists before attempting login
            if (DriverManager.getDriver() == null) {
                throw new RuntimeException("Driver is null - cannot perform login");
            }
            
            LoginPage loginPage = new LoginPage();
            
            // Proceed with login
            loginPage.openLoginPage();
            
            String testEmail = ConfigReader.getTestEmail();
            String testPassword = ConfigReader.getTestPassword();
            
            loginPage.enterEmail(testEmail);
            loginPage.clickContinue();
            Thread.sleep(2000);
            loginPage.enterPassword(testPassword);
            loginPage.clickSignIn();
            
            System.out.println("✅ Auto-login process completed");
            
        } catch (Exception e) {
            System.out.println("❌ Auto-login failed: " + e.getMessage());
            e.printStackTrace();
            System.out.println("⚠ Continuing test without login verification");
        }
    }
    
    @After
    public void tearDown(Scenario scenario) {
        System.out.println("Finishing scenario: " + scenario.getName());
        
        try {
            // Capture screenshot if scenario failed
            if (scenario.isFailed() && ConfigReader.isScreenshotOnFailure()) {
                if (DriverManager.getDriver() != null) {
                    String screenshotPath = Screenshot.captureScreenshot(scenario.getName());
                    if (screenshotPath != null) {
                        System.out.println("Screenshot saved for failed scenario: " + screenshotPath);
                    }
                } else {
                    System.out.println("⚠ Cannot capture screenshot - driver is null");
                }
            }
        } catch (Exception e) {
            System.err.println("Error capturing screenshot: " + e.getMessage());
        } finally {
            // Always close WebDriver
            try {
                DriverManager.quitDriver();
            } catch (Exception e) {
                System.err.println("Error closing WebDriver: " + e.getMessage());
            }
        }
    }
}
