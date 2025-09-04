package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverManager;

import java.time.Duration;
import java.util.List;

public class ProductPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    public ProductPage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.js = (JavascriptExecutor) driver;
    }

    public void navigateToProduct(String productUrl) {
        driver.get(productUrl);
        System.out.println("✅ Navigated to product page: " + productUrl);
    }

    public void searchAndSelectFirstProduct(String productName) {
        try {
            // Enter product name
            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("twotabsearchtextbox")));
            searchBox.clear();
            searchBox.sendKeys(productName);
            searchBox.submit();

            // Click first product in results
            WebElement firstProduct = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".s-search-results .s-title-instructions-style a")));
            firstProduct.click();
            System.out.println("✅ Selected first product for: " + productName);
        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to search/select product: " + e.getMessage());
        }
    }

    public void addProductToCart() {
        try {
            System.out.println("🛒 Attempting to click Add to Cart button...");

            handlePopups();

            By[] addToCartSelectors = {
                By.id("add-to-cart-button"),
                By.cssSelector("#submit.add-to-cart"),
                By.cssSelector("[name='submit.add-to-cart']"),
                By.xpath("//input[@id='add-to-cart-button']"),
                By.xpath("//input[contains(@name,'add-to-cart')]")
            };

            boolean clicked = false;

            for (By selector : addToCartSelectors) {
                try {
                    WebElement addToCartBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
                    js.executeScript("arguments[0].scrollIntoView(true);", addToCartBtn);
                    Thread.sleep(500);

                    try {
                        wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn)).click();
                        System.out.println("✅ Clicked Add to Cart button: " + selector);
                    } catch (Exception e) {
                        js.executeScript("arguments[0].click();", addToCartBtn);
                        System.out.println("✅ Clicked Add to Cart button using JS: " + selector);
                    }

                    clicked = true;
                    break;

                } catch (Exception ignored) {
                    // Try next selector
                }
            }

            if (!clicked) {
                throw new RuntimeException("❌ Add to Cart button not found or clickable");
            }

            Thread.sleep(3000);
            handlePopups();

        } catch (Exception e) {
            throw new RuntimeException("Failed to add product to cart: " + e.getMessage(), e);
        }
    }

    private void handlePopups() {
        try {
            List<By> popupSelectors = List.of(
                By.id("attach-close_sideSheet-link"),
                By.cssSelector("input[name='dismiss']"),
                By.cssSelector("button[aria-label='Close']")
            );

            for (By selector : popupSelectors) {
                try {
                    WebElement popupClose = driver.findElement(selector);
                    if (popupClose.isDisplayed()) {
                        popupClose.click();
                        System.out.println("✅ Closed popup: " + selector);
                        Thread.sleep(1000);
                    }
                } catch (Exception ignored) {}
            }
        } catch (Exception ignored) {}
    }
}
