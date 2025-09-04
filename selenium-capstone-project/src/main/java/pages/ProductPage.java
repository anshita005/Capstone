package pages;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;


public class ProductPage extends BasePage {
    private WebDriverWait wait;

    // Primary product page elements
    @FindBy(id = "productTitle")
    private WebElement productTitle;

    @FindBy(css = "span.a-price-whole, .a-price .a-offscreen")
    private WebElement productPrice;

    @FindBy(id = "add-to-cart-button")
    private WebElement addToCartButton;

    @FindBy(id = "availability")
    private WebElement availabilityInfo;

    // Alternative selectors for different Amazon layouts
    @FindBy(css = "#productTitle, h1[data-automation-id='product-title'], .product-title")
    private List<WebElement> alternativeProductTitles;

    @FindBy(css = ".a-price, .a-price-range, .price, #price_inside_buybox")
    private List<WebElement> alternativeProductPrices;

    // FIXED: Updated Add to Cart selectors (removed buy-now-button)
    @FindBy(css = "#add-to-cart-button, input[name='submit.add-to-cart'], [data-action='add-to-cart']")
    private List<WebElement> addToCartButtons;

    // ADDED: Modern Add to Cart selectors
    @FindBy(css = "#add-to-cart-button, input[name='submit.add-to-cart'], [data-action='add-to-cart'], .a-button-input[aria-labelledby*='cart'], input[id*='add-to-cart'], button[name='submit.add-to-cart'], #sw-atc-main-container input, [data-csa-c-func-deps*='add-to-cart']")
    private List<WebElement> modernAddToCartButtons;

    // Constructor
    public ProductPage() {
        super(); // call BasePage constructor
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }
    
    // Check if product page is loaded
    public boolean isProductPageLoaded() {
        try {
            String currentUrl = driver.getCurrentUrl();
            String pageTitle = driver.getTitle();

            System.out.println("🔍 Product page check - URL: " + currentUrl);
            System.out.println("🔍 Product page check - Title: " + pageTitle);

            boolean urlIndicatesProductPage = currentUrl.contains("/dp/") || 
                                              currentUrl.contains("/product/") ||
                                              currentUrl.contains("/gp/product/");
            boolean titleIndicatesAmazon = pageTitle.toLowerCase().contains("amazon");

            boolean hasProductElements = isElementPresent(productTitle) || 
                                         !alternativeProductTitles.isEmpty();

            System.out.println("🔍 Product page indicators - URL: " + urlIndicatesProductPage +
                               ", Title: " + titleIndicatesAmazon +
                               ", Elements: " + hasProductElements);

            if (urlIndicatesProductPage) {
                System.out.println("✅ Product page detected via URL pattern");
                return true;
            }

            if (hasProductElements && titleIndicatesAmazon) {
                System.out.println("✅ Product page detected via elements");
                return true;
            }

            try {
                wait.until(ExpectedConditions.or(
                    ExpectedConditions.presenceOfElementLocated(org.openqa.selenium.By.id("productTitle")),
                    ExpectedConditions.presenceOfElementLocated(org.openqa.selenium.By.cssSelector("h1[data-automation-id='product-title']")),
                    ExpectedConditions.urlContains("/dp/")
                ));
                System.out.println("✅ Product page detected after wait");
                return true;
            } catch (Exception e) {
                System.out.println("⚠ Product page detection timeout, but may still be valid");
                return urlIndicatesProductPage;
            }
        } catch (Exception e) {
            System.out.println("❌ Error checking product page: " + e.getMessage());
            return false;
        }
    }

    public String getProductTitle() {
        try {
            if (isElementPresent(productTitle)) {
                return productTitle.getText().trim();
            }
            for (WebElement titleElement : alternativeProductTitles) {
                if (isElementPresent(titleElement) && !titleElement.getText().trim().isEmpty()) {
                    return titleElement.getText().trim();
                }
            }
            // fallback to search general headings
            List<WebElement> headings = driver.findElements(org.openqa.selenium.By.cssSelector("h1, .a-size-large, .product-title"));
            for (WebElement heading : headings) {
                String text = heading.getText().trim();
                if (!text.isEmpty() && text.length() > 10) {
                    return text;
                }
            }
            return "Product title not available";
        } catch (Exception e) {
            System.out.println("❌ Error getting product title: " + e.getMessage());
            return "Product title not available";
        }
    }

    public String getProductPrice() {
        try {
            if (isElementPresent(productPrice)) {
                return productPrice.getText().trim();
            }
            for (WebElement priceElement : alternativeProductPrices) {
                if (isElementPresent(priceElement) && !priceElement.getText().trim().isEmpty()) {
                    String priceText = priceElement.getText().trim();
                    if (priceText.contains("$") || priceText.matches(".*\\d+.*")) {
                        return priceText;
                    }
                }
            }
            return "Price not available";
        } catch (Exception e) {
            System.out.println("❌ Error getting product price: " + e.getMessage());
            return "Price not available";
        }
    }

    // FIXED: Complete rewrite of Add to Cart detection
    public boolean isAddToCartButtonAvailable() {
        try {
            Thread.sleep(2000); // Wait for page load
            
            // Check primary button first
            if (isElementPresent(addToCartButton)) {
                System.out.println("✅ Found Add to Cart button (primary)");
                return true;
            }
            
            // Check PageFactory elements
            for (WebElement button : addToCartButtons) {
                if (isElementPresent(button)) {
                    System.out.println("✅ Found Add to Cart button (PageFactory)");
                    return true;
                }
            }
            
            // Check modern selectors
            for (WebElement button : modernAddToCartButtons) {
                if (isElementPresent(button)) {
                    System.out.println("✅ Found Add to Cart button (modern selector)");
                    return true;
                }
            }
            
            // Dynamic search as fallback
            String[] dynamicSelectors = {
                "#add-to-cart-button",
                "input[name='submit.add-to-cart']",
                "[data-action='add-to-cart']",
                ".a-button-input[aria-labelledby*='cart']",
                "input[id*='add-to-cart']",
                "button[name='submit.add-to-cart']",
                "#sw-atc-main-container input",
                "[data-csa-c-func-deps*='add-to-cart']"
            };
            
            for (String selector : dynamicSelectors) {
                try {
                    List<WebElement> elements = driver.findElements(org.openqa.selenium.By.cssSelector(selector));
                    for (WebElement element : elements) {
                        if (element.isDisplayed() && element.isEnabled()) {
                            System.out.println("✅ Found Add to Cart button (dynamic): " + selector);
                            return true;
                        }
                    }
                } catch (Exception e) {
                    // Continue to next selector
                }
            }
            
            System.out.println("❌ No Add to Cart button found");
            return false;
            
        } catch (Exception e) {
            System.out.println("❌ Error checking add to cart button: " + e.getMessage());
            return false;
        }
    }

    // FIXED: Complete rewrite of Add to Cart clicking
    public void clickAddToCart() {
        try {
            Thread.sleep(2000);
            boolean clicked = false;
            
            // Try primary button first
            if (isElementPresent(addToCartButton)) {
                wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
                addToCartButton.click();
                System.out.println("✅ Clicked Add to Cart button (primary)");
                clicked = true;
            }
            
            if (!clicked) {
                // Try PageFactory elements
                for (WebElement button : addToCartButtons) {
                    if (isElementPresent(button)) {
                        wait.until(ExpectedConditions.elementToBeClickable(button));
                        button.click();
                        System.out.println("✅ Clicked Add to Cart button (PageFactory)");
                        clicked = true;
                        break;
                    }
                }
            }
            
            if (!clicked) {
                // Try modern selectors
                for (WebElement button : modernAddToCartButtons) {
                    if (isElementPresent(button)) {
                        wait.until(ExpectedConditions.elementToBeClickable(button));
                        button.click();
                        System.out.println("✅ Clicked Add to Cart button (modern)");
                        clicked = true;
                        break;
                    }
                }
            }
            
            if (!clicked) {
                // Dynamic fallback
                String[] dynamicSelectors = {
                    "#add-to-cart-button",
                    "input[name='submit.add-to-cart']",
                    "[data-action='add-to-cart']",
                    ".a-button-input[aria-labelledby*='cart']",
                    "input[id*='add-to-cart']",
                    "button[name='submit.add-to-cart']",
                    "#sw-atc-main-container input",
                    "[data-csa-c-func-deps*='add-to-cart']"
                };
                
                for (String selector : dynamicSelectors) {
                    try {
                        List<WebElement> elements = driver.findElements(org.openqa.selenium.By.cssSelector(selector));
                        for (WebElement element : elements) {
                            if (element.isDisplayed() && element.isEnabled()) {
                                // Scroll to element
                                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
                                Thread.sleep(1000);
                                
                                element.click();
                                System.out.println("✅ Clicked Add to Cart button (dynamic): " + selector);
                                clicked = true;
                                break;
                            }
                        }
                        if (clicked) break;
                    } catch (Exception e) {
                        System.out.println("⚠ Click failed with selector " + selector + ": " + e.getMessage());
                    }
                }
            }
            
            if (!clicked) {
                throw new RuntimeException("No Add to Cart button found to click");
            }
            
            Thread.sleep(2000); // Wait for action to complete
            
        } catch (Exception e) {
            System.out.println("❌ Error clicking add to cart: " + e.getMessage());
            throw new RuntimeException("Failed to click add to cart button", e);
        }
    }

    public String getAvailabilityStatus() {
        try {
            if (isElementPresent(availabilityInfo)) {
                return availabilityInfo.getText().trim();
            }
            List<WebElement> availabilityElements = driver.findElements(
                org.openqa.selenium.By.cssSelector("#availability, .availability, .stock-info, #merchant-info")
            );
            for (WebElement element : availabilityElements) {
                String text = element.getText().trim();
                if (!text.isEmpty()) {
                    return text;
                }
            }
            return "Availability info not found";
        } catch (Exception e) {
            System.out.println("❌ Error getting availability: " + e.getMessage());
            return "Availability info not found";
        }
    }

    private boolean isElementPresent(WebElement element) {
        try {
            return element != null && element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
