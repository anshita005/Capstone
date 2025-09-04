package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.DriverManager;

import java.time.Duration;
import java.util.List;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Corrected selectors for Amazon's actual elements
    @FindBy(id = "nav-link-accountList")
    private WebElement signInLink;

    @FindBy(id = "twotabsearchtextbox")  // Fixed: correct ID
    private WebElement primarySearchBox;

    @FindBy(css = "input[name='field-keywords']")
    private List<WebElement> alternativeSearchBoxes;

    @FindBy(id = "nav-search-submit-button")
    private WebElement primarySearchButton;

    @FindBy(id = "nav-logo-sprites")
    private WebElement primaryLogo;

    @FindBy(css = "#nav-logo")
    private List<WebElement> alternativeLogos;

    @FindBy(id = "nav-cart")
    private WebElement primaryCart;

    @FindBy(css = "#nav-cart-count")
    private List<WebElement> alternativeCarts;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = DriverManager.getWait();
        if (this.wait == null) {
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        }
        PageFactory.initElements(driver, this);
    }

    public void navigateToHomePage() {
        try {
            System.out.println("🌐 Navigating to: https://www.amazon.com");
            driver.get("https://www.amazon.com");
            
            // Wait for key elements to ensure page is loaded
            wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(By.id("twotabsearchtextbox")),
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='field-keywords']")),
                ExpectedConditions.titleContains("Amazon")
            ));
            
            System.out.println("✅ Amazon homepage loaded successfully");
        } catch (Exception e) {
            System.out.println("❌ Error navigating to homepage: " + e.getMessage());
            throw new RuntimeException("Failed to navigate to homepage", e);
        }
    }

    public boolean isHomepageLoaded() {
        try {
            // More lenient homepage validation
            String currentUrl = driver.getCurrentUrl().toLowerCase();
            String pageTitle = driver.getTitle().toLowerCase();
            
            boolean urlIsAmazon = currentUrl.contains("amazon.com");
            boolean titleIsAmazon = pageTitle.contains("amazon");
            boolean hasSearchBox = isSearchBoxVisible();
            
            System.out.println("🔍 Homepage validation - URL: " + urlIsAmazon + 
                             ", Title: " + titleIsAmazon + 
                             ", SearchBox: " + hasSearchBox);
            
            // At least 2 of these should be true for a valid homepage
            int validationScore = 0;
            if (urlIsAmazon) validationScore++;
            if (titleIsAmazon) validationScore++;
            if (hasSearchBox) validationScore++;
            
            return validationScore >= 2;
            
        } catch (Exception e) {
            System.out.println("❌ Error validating homepage: " + e.getMessage());
            return false;
        }
    }

    public void searchFor(String searchTerm) {
        try {
            System.out.println("🔍 Searching for: " + searchTerm);
            
            WebElement searchBox = null;
            
            // Try to find search box with multiple strategies
            if (isElementPresent(primarySearchBox)) {
                searchBox = primarySearchBox;
                System.out.println("✅ Using primary search box");
            } else {
                // Try alternative selectors
                String[] searchSelectors = {
                    "input[name='field-keywords']",
                    "#twotabsearchtextbox",
                    "input[placeholder*='Search']"
                };
                
                for (String selector : searchSelectors) {
                    try {
                        List<WebElement> elements = driver.findElements(By.cssSelector(selector));
                        for (WebElement element : elements) {
                            if (element.isDisplayed() && element.isEnabled()) {
                                searchBox = element;
                                System.out.println("✅ Found search box with: " + selector);
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
                throw new RuntimeException("Could not find search box");
            }

            // Perform search
            wait.until(ExpectedConditions.elementToBeClickable(searchBox));
            searchBox.clear();
            searchBox.sendKeys(searchTerm);
            searchBox.sendKeys(Keys.ENTER);
            
            // Wait for search results page
            wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/s?"),
                ExpectedConditions.urlContains("k=" + searchTerm.replace(" ", "+")),
                ExpectedConditions.titleContains(searchTerm)
            ));
            
            System.out.println("✅ Search completed for: " + searchTerm);
            
        } catch (Exception e) {
            System.out.println("❌ Search failed: " + e.getMessage());
            throw new RuntimeException("Search operation failed", e);
        }
    }

    public boolean isSearchBoxVisible() {
        try {
            // Check primary search box
            if (isElementPresent(primarySearchBox)) {
                return true;
            }
            
            // Check alternative search boxes
            for (WebElement element : alternativeSearchBoxes) {
                if (isElementPresent(element)) {
                    return true;
                }
            }
            
            // Dynamic check
            try {
                List<WebElement> searchBoxes = driver.findElements(By.cssSelector(
                    "#twotabsearchtextbox, input[name='field-keywords'], input[placeholder*='Search']"
                ));
                
                for (WebElement box : searchBoxes) {
                    if (box.isDisplayed() && box.isEnabled()) {
                        return true;
                    }
                }
            } catch (Exception e) {
                // Continue
            }
            
            return false;
        } catch (Exception e) {
            System.out.println("❌ Error checking search box: " + e.getMessage());
            return false;
        }
    }

    public boolean isAmazonLogoVisible() {
        try {
            if (isElementPresent(primaryLogo)) return true;
            
            String[] logoSelectors = {
                "#nav-logo",
                ".nav-logo",
                "a[aria-label*='Amazon']"
            };
            
            for (String selector : logoSelectors) {
                try {
                    List<WebElement> logos = driver.findElements(By.cssSelector(selector));
                    for (WebElement logo : logos) {
                        if (logo.isDisplayed()) return true;
                    }
                } catch (Exception e) {
                    // Continue
                }
            }
            
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCartIconVisible() {
        try {
            if (isElementPresent(primaryCart)) return true;
            
            // Check for cart icon with different selectors
            String[] cartSelectors = {
                "#nav-cart",
                "#nav-cart-count",
                "a[href*='cart']"
            };
            
            for (String selector : cartSelectors) {
                try {
                    List<WebElement> carts = driver.findElements(By.cssSelector(selector));
                    for (WebElement cart : carts) {
                        if (cart.isDisplayed()) return true;
                    }
                } catch (Exception e) {
                    // Continue
                }
            }
            
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public void clickSignInLink() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(signInLink));
            signInLink.click();
            
            // Wait for login page to load
            wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("signin"),
                ExpectedConditions.urlContains("ap/signin"),
                ExpectedConditions.presenceOfElementLocated(By.id("ap_email"))
            ));
            
            System.out.println("✅ Clicked Sign-in link and navigated to login page");
        } catch (Exception e) {
            System.out.println("❌ Failed to click Sign-in link: " + e.getMessage());
            throw new RuntimeException("Cannot click Sign-in link", e);
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
            return driver.getTitle();
        } catch (Exception e) {
            return "";
        }
    }

    public String getCurrentUrl() {
        try {
            return driver.getCurrentUrl();
        } catch (Exception e) {
            return "";
        }
    }
}
