package utils;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class DriverManager {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();

    public static synchronized void initializeDriver(String browserType) {
        if (driver.get() != null) {
            System.out.println("⚠ Driver already exists for thread, closing previous session");
            quitDriver();
        }
        try {
            System.out.println("📋 Initializing browser: " + browserType);
            WebDriver webDriver;
            switch (browserType.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--disable-gpu",
                        "--disable-web-security", "--allow-running-insecure-content");
                    options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                    webDriver = new ChromeDriver(options);
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions fOptions = new FirefoxOptions();
                    fOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                    webDriver = new FirefoxDriver(fOptions);
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    EdgeOptions eOptions = new EdgeOptions();
                    eOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                    webDriver = new EdgeDriver(eOptions);
                    break;
                default:
                    System.out.println("⚠ Unknown browser, defaulting to Chrome");
                    WebDriverManager.chromedriver().setup();
                    webDriver = new ChromeDriver();
            }
            webDriver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(3))
                .pageLoadTimeout(Duration.ofSeconds(15))
  ;
            driver.set(webDriver);
            wait.set(new WebDriverWait(webDriver, Duration.ofSeconds(8)));
            System.out.println("✅ WebDriver initialized for: " + browserType);
        } catch (Exception e) {
            System.out.println("❌ Failed to initialize WebDriver: " + e.getMessage());
            throw new RuntimeException("WebDriver init failed", e);
        }
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static WebDriverWait getWait() {
        return wait.get();
    }

    public static synchronized void quitDriver() {
        WebDriver wd = driver.get();
        if (wd != null) {
            try {
                wd.quit();
                System.out.println("✅ Driver quit successfully");
            } catch (Exception e) {
                System.err.println("⚠️ Error quitting WebDriver: " + e.getMessage());
            } finally {
                driver.remove();
                wait.remove();
            }
        }
    }

    public static void ensureDriverAlive() {
        WebDriver wd = driver.get();
        if (wd == null) {
            System.out.println("⚠️ Driver not initialized, initializing default Chrome");
            initializeDriver("chrome");
            return;
        }
        try {
            wd.getCurrentUrl();
        } catch (Exception e) {
            System.out.println("⚠️ Driver session not alive, reinitializing...");
            initializeDriver("chrome");
        }
    }

    public static boolean isDriverAlive() {
        WebDriver wd = driver.get();
        if (wd == null) return false;
        try {
            wd.getCurrentUrl();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
