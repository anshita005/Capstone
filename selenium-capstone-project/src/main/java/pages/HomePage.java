package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;


import utils.DriverManager;

import java.time.Duration;
import java.util.List;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Primary and alternative selectors
    @FindBy(id = "twotabsearchtextbox")
    private WebElement primarySearchBox;

    @FindBy(css = "input[type='text'][placeholder*='Search'], input[data-action-type='SEARCH'], #nav-search-bar-form input")
    private List<WebElement> alternativeSearchBoxes;

    @FindBy(id = "nav-search-submit-button")
    private WebElement primarySearchButton;

    @FindBy(css = "input[type='submit'][value='Go'], button[type='submit'], .nav-search-submit input")
    private List<WebElement> alternativeSearchButtons;

    @FindBy(id = "nav-logo-sprites")
    private WebElement primaryLogo;

    @FindBy(css = "a[href*='nav_logo'], .nav-logo, a[aria-label*='Amazon']")
    private List<WebElement> alternativeLogos;

    @FindBy(id = "nav-cart")
    private WebElement primaryCart;

    @FindBy(css = "#nav-cart, .nav-cart, a[href*='cart'], [data-nav-ref='nav_cart']")
    private List<WebElement> alternativeCarts;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = DriverManager.getWait(); // FIX: Use DriverManager's wait
        if (this.wait == null) {
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        }
        PageFactory.initElements(driver, this);
    }

    public void navigateToHomePage() {
        try {
            System.out.println("🌐 Navigating to: https://www.amazon.com");
            
            // Multiple attempts with different strategies
            int maxAttempts = 3;
            boolean success = false;
            
            for (int attempt = 1; attempt <= maxAttempts && !success; attempt++) {
                try {
                    System.out.println("🔄 Navigation attempt " + attempt + "/" + maxAttempts);
                    
                    if (attempt > 1) {
                        // Reinitialize driver if not first attempt
                        DriverManager.ensureDriverAlive();
                        // Update driver reference after potential reinitialization
                        this.driver = DriverManager.getDriver();
                        this.wait = DriverManager.getWait();
                        if (this.wait == null) {
                            this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(15));
                        }
                    }
                    
                    driver.get("https://www.amazon.com");
                    Thread.sleep(2000);
                    
                    // Verify navigation success
                    String title = driver.getTitle().toLowerCase();
                    String url = driver.getCurrentUrl().toLowerCase();
                    
                    if (title.contains("amazon") || url.contains("amazon")) {
                        success = true;
                        System.out.println("✅ Amazon page loaded successfully");
                    } else if (attempt == maxAttempts) {
                        throw new RuntimeException("Navigation failed after " + maxAttempts + " attempts");
                    }
                    
                } catch (TimeoutException te) {
                    System.out.println("⏰ Timeout on attempt " + attempt + ": " + te.getMessage());
                    if (attempt < maxAttempts) {
                        Thread.sleep(3000); // Wait before retry
                    } else {
                        throw new RuntimeException("Failed to navigate to homepage", te);
                    }
                } catch (Exception e) {
                    System.out.println("❌ Navigation attempt " + attempt + " failed: " + e.getMessage());
                    if (attempt == maxAttempts) {
                        throw new RuntimeException("Failed to navigate to homepage", e);
                    }
                }
            }
            
        } catch (Exception e) {
            System.out.println("❌ Navigation error: " + e.getMessage());
            throw new RuntimeException("Failed to navigate to homepage", e);
        }
    }

    public boolean isHomepageLoaded() {
        try {
            if (!DriverManager.isDriverAlive()) {
                System.out.println("❌ Driver session is not alive");
                return false;
            }

            boolean hasSearchBox = isSearchBoxVisible();
            boolean hasLogo = isAmazonLogoVisible();
            boolean titleContainsAmazon = driver.getTitle().toLowerCase().contains("amazon");
            boolean urlContainsAmazon = driver.getCurrentUrl().toLowerCase().contains("amazon");

            System.out.println("🔍 Homepage check - SearchBox: " + hasSearchBox +
                    ", Logo: " + hasLogo +
                    ", Title: " + titleContainsAmazon +
                    ", URL: " + urlContainsAmazon);

            int score = 0;
            if (hasSearchBox) score++;
            if (hasLogo) score++;
            if (titleContainsAmazon) score++;
            if (urlContainsAmazon) score++;

            return score >= 2;
        } catch (Exception e) {
            System.out.println("❌ Homepage verification error: " + e.getMessage());
            return false;
        }
    }

    public void searchFor(String searchTerm) {
        try {
            // Ensure driver is alive
            DriverManager.ensureDriverAlive();
            
            // Ensure we're on the right page first
            String currentUrl = driver.getCurrentUrl().toLowerCase();
            if (!currentUrl.contains("amazon")) {
                System.out.println("🌐 Not on Amazon, navigating first...");
                navigateToHomePage();
            }

            WebElement searchBox = null;
            
            // Try PageFactory elements first
            if (isElementPresent(primarySearchBox)) {
                searchBox = primarySearchBox;
                System.out.println("🔍 Using primary search box");
            } else if (!alternativeSearchBoxes.isEmpty()) {
                for (WebElement element : alternativeSearchBoxes) {
                    if (isElementPresent(element)) {
                        searchBox = element;
                        System.out.println("🔍 Using alternative search box");
                        break;
                    }
                }
            }
            
            // If PageFactory elements didn't work, try dynamic selectors
            if (searchBox == null) {
                System.out.println("🔍 PageFactory elements not found, trying dynamic selectors...");
                
                // Modern Amazon search box selectors (2024/2025)
                String[] searchSelectors = {
                    "#twotabsearchtextbox",           // Primary Amazon search box
                    "input[name='field-keywords']",   // Alternative search box
                    "#nav-search-box input",          // Nav search box
                    ".nav-search-field input",        // Search field
                    "input[placeholder*='Search']",   // Generic search input
                    "#searchDropdownBox + input",     // Search box next to dropdown
                    ".a-search-textbox",              // Amazon textbox class
                    "input[type='text']"              // Generic text input fallback
                };
                
                for (String selector : searchSelectors) {
                    try {
                        List<WebElement> elements = driver.findElements(By.cssSelector(selector));
                        for (WebElement element : elements) {
                            if (element.isDisplayed() && element.isEnabled()) {
                                searchBox = element;
                                System.out.println("🔍 Found search box with selector: " + selector);
                                break;
                            }
                        }
                        if (searchBox != null) break;
                    } catch (Exception e) {
                        // Continue to next selector
                    }
                }
            }

            if (searchBox == null) {
                System.out.println("❌ Search failed: Could not find any search box");
                throw new RuntimeException("Could not find any search box");
            }

            // Clear and enter search term
            try {
                searchBox.clear();
                Thread.sleep(500);
                searchBox.sendKeys(searchTerm);
                System.out.println("🔍 Entered search term: " + searchTerm);
                
                // Submit search
                searchBox.sendKeys(Keys.ENTER);
                System.out.println("🔍 Search submitted");
                
                // Wait for search results page to load
                Thread.sleep(3000);
                
            } catch (Exception inputException) {
                System.out.println("❌ Error during search input: " + inputException.getMessage());
                throw new RuntimeException("Failed to input search term", inputException);
            }
            
        } catch (Exception e) {
            System.out.println("❌ Search failed: " + e.getMessage());
            throw new RuntimeException("Failed to perform search", e);
        }
    }

    public boolean isSearchBoxVisible() {
        try {
            // Try primary search box first
            if (isElementPresent(primarySearchBox)) {
                return true;
            }
            
            // Try alternative search boxes
            for (WebElement element : alternativeSearchBoxes) {
                if (isElementPresent(element)) {
                    return true;
                }
            }
            
            // Dynamic fallback check
            try {
                List<WebElement> searchBoxes = driver.findElements(By.cssSelector(
                    "#twotabsearchtextbox, " +
                    "input[name='field-keywords'], " +
                    "input[placeholder*='Search'], " +
                    "#nav-search-box input"
                ));
                
                for (WebElement box : searchBoxes) {
                    if (box.isDisplayed() && box.isEnabled()) {
                        return true;
                    }
                }
            } catch (Exception e) {
                // Fallback failed
            }
            
            return false;
        } catch (Exception e) {
            System.out.println("❌ Error checking search box visibility: " + e.getMessage());
            return false;
        }
    }

    public boolean isCartIconVisible() {
        try {
            // Try primary cart first
            if (isElementPresent(primaryCart)) {
                return true;
            }
            
            // Try alternative carts
            for (WebElement element : alternativeCarts) {
                if (isElementPresent(element)) {
                    return true;
                }
            }
            
            // Dynamic fallback check
            try {
                List<WebElement> cartElements = driver.findElements(By.cssSelector(
                    "#nav-cart, " +
                    ".nav-cart, " +
                    "a[href*='cart'], " +
                    "[data-nav-ref='nav_cart']"
                ));
                
                for (WebElement cart : cartElements) {
                    if (cart.isDisplayed()) {
                        return true;
                    }
                }
            } catch (Exception e) {
                // Fallback failed
            }
            
            return false;
        } catch (Exception e) {
            System.out.println("❌ Error checking cart icon visibility: " + e.getMessage());
            return false;
        }
    }

    public boolean isAmazonLogoVisible() {
        try {
            // Try primary logo first
            if (isElementPresent(primaryLogo)) {
                return true;
            }
            
            // Try alternative logos
            for (WebElement element : alternativeLogos) {
                if (isElementPresent(element)) {
                    return true;
                }
            }
            
            // Dynamic fallback check
            try {
                List<WebElement> logoElements = driver.findElements(By.cssSelector(
                    "#nav-logo-sprites, " +
                    ".nav-logo, " +
                    "a[href*='nav_logo'], " +
                    "a[aria-label*='Amazon']"
                ));
                
                for (WebElement logo : logoElements) {
                    if (logo.isDisplayed()) {
                        return true;
                    }
                }
            } catch (Exception e) {
                // Fallback failed
            }
            
            return false;
        } catch (Exception e) {
            System.out.println("❌ Error checking logo visibility: " + e.getMessage());
            return false;
        }
    }

    private boolean isElementPresent(WebElement element) {
        try {
            return element != null && element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getPageTitle() {
        try {
            DriverManager.ensureDriverAlive();
            return driver.getTitle();
        } catch (Exception e) {
            System.out.println("❌ Cannot get page title: " + e.getMessage());
            return "";
        }
    }

    public String getCurrentUrl() {
        try {
            DriverManager.ensureDriverAlive();
            return driver.getCurrentUrl();
        } catch (Exception e) {
            System.out.println("❌ Cannot get current URL: " + e.getMessage());
            return "";
        }
    }
}
