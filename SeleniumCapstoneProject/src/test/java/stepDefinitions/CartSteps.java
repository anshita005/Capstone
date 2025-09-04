package stepDefinitions;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverManager;
import pages.CartPage;

import java.time.Duration;
import java.util.List;

public class CartSteps {
    private CartPage cartPage;
    private WebDriverWait wait;

    public CartSteps() {
        this.cartPage = new CartPage();
        this.wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(15));
    }

    // ✅ Navigate to cart
    @When("I navigate to cart")
    public void i_navigate_to_cart() {
        try {
            cartPage.navigateToCart();
        } catch (Exception e) {
            System.out.println("❌ Error navigating to cart: " + e.getMessage());
            throw new RuntimeException("Failed to navigate to cart", e);
        }
    }

    // ✅ Click on cart icon
    @When("I click on cart icon")
    public void i_click_on_cart_icon() {
        try {
            boolean clickSuccessful = false;
            String[] cartSelectors = {
                "#nav-cart", "#nav-cart-text-container", ".nav-cart-icon",
                "[data-nav-ref='nav_cart']", "a[href*='cart']",
                "#nav-tools a[href*='cart']", ".nav-cart", "#nav-cart-count-container"
            };

            for (String selector : cartSelectors) {
                try {
                    List<WebElement> cartElements = DriverManager.getDriver().findElements(By.cssSelector(selector));
                    for (WebElement cartElement : cartElements) {
                        if (cartElement.isDisplayed() && cartElement.isEnabled()) {
                            ((JavascriptExecutor) DriverManager.getDriver()).executeScript("arguments[0].scrollIntoView(true);", cartElement);
                            try {
                                cartElement.click();
                            } catch (Exception e) {
                                ((JavascriptExecutor) DriverManager.getDriver()).executeScript("arguments[0].click();", cartElement);
                            }
                            clickSuccessful = true;
                            break;
                        }
                    }
                    if (clickSuccessful) break;
                } catch (Exception ignored) {}
            }

            if (!clickSuccessful) {
                throw new RuntimeException("Could not find or click cart icon");
            }

            wait.until(ExpectedConditions.urlContains("cart"));
        } catch (Exception e) {
            System.out.println("❌ Cart icon click failed: " + e.getMessage());
            throw new RuntimeException("Failed to click cart icon", e);
        }
    }

    // ✅ Validate Cart Page
    @Then("I should be on the cart page")
    @Then("I should see the cart page")
    public void i_should_be_on_the_cart_page() {
        boolean isCartPage = false;
        try {
            String currentUrl = DriverManager.getDriver().getCurrentUrl().toLowerCase();
            String currentTitle = DriverManager.getDriver().getTitle().toLowerCase();

            if (currentUrl.contains("cart") || currentTitle.contains("cart")) {
                isCartPage = true;
            }

            if (!isCartPage) {
                String[] cartSelectors = {"#sc-active-cart", ".sc-list-item", "#cart-title"};
                for (String selector : cartSelectors) {
                    List<WebElement> elements = DriverManager.getDriver().findElements(By.cssSelector(selector));
                    if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
                        isCartPage = true;
                        break;
                    }
                }
            }

            Assert.assertTrue(isCartPage, "Cart page should be loaded");
        } catch (Exception e) {
            Assert.fail("Cart page validation failed: " + e.getMessage());
        }
    }

    // ✅ Validate items in cart
    @Then("I should see items in cart")
    public void i_should_see_items_in_cart() {
        try {
            boolean hasItems = !cartPage.isCartEmpty();
            Assert.assertTrue(hasItems, "Cart should contain items");
        } catch (Exception e) {
            Assert.fail("Unable to validate cart items: " + e.getMessage());
        }
    }

    // ✅ Cart should not be empty
    @Then("cart should not be empty")
    public void cart_should_not_be_empty() {
        try {
            boolean isEmpty = cartPage.isCartEmpty();
            Assert.assertFalse(isEmpty, "Cart should not be empty");
        } catch (Exception e) {
            Assert.fail("Cart empty check failed: " + e.getMessage());
        }
    }

    // ✅ Cart item details
    @Then("I should see cart item details")
    public void i_should_see_cart_item_details() {
        try {
            int itemCount = cartPage.getCartItemsCount();
            Assert.assertTrue(itemCount > 0, "Cart should contain items");
        } catch (Exception e) {
            Assert.fail("Cart item details check failed: " + e.getMessage());
        }
    }

    // ✅ Proceed to checkout
    @Then("I should see Proceed to Checkout button")
    public void i_should_see_proceed_to_checkout_button() {
        try {
            boolean checkoutAvailable = cartPage.isProceedToCheckoutAvailable();
            Assert.assertTrue(checkoutAvailable, "Proceed to Checkout button should be available");
        } catch (Exception e) {
            Assert.fail("Checkout button check failed: " + e.getMessage());
        }
    }

    // ✅ Cart subtotal
    @Then("I should see cart subtotal")
    public void i_should_see_cart_subtotal() {
        try {
            String subtotal = cartPage.getSubtotalAmount();
            Assert.assertFalse(subtotal.equals("Subtotal not available"), "Cart subtotal should be displayed");
        } catch (Exception e) {
            Assert.fail("Subtotal check failed: " + e.getMessage());
        }
    }

    // ✅ NEW: Validate product detail page
    @Then("I should be on the product detail page")
    public void i_should_be_on_the_product_detail_page() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("productTitle")));
            WebElement productTitle = DriverManager.getDriver().findElement(By.id("productTitle"));
            Assert.assertTrue(productTitle.isDisplayed(), "Product detail page should be visible");
        } catch (Exception e) {
            Assert.fail("Not on product detail page: " + e.getMessage());
        }
    }
void i_add_the_product_to_cart() {
        try {
            WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-button")));
            addToCartBtn.click();
            wait.until(ExpectedConditions.or(
                ExpectedConditions.presenceOfElementLocated(By.id("sw-atc-details-single-container")),
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("h1.a-size-medium"))
            ));
        } catch (Exception e) {
            Assert.fail("Failed to add product to cart: " + e.getMessage());
        }
    }

    // ✅ NEW: Verify product added to cart
    @Then("the product should be added to cart")
    public void the_product_should_be_added_to_cart() {
        try {
            boolean added = DriverManager.getDriver().getPageSource().toLowerCase().contains("added to cart");
            Assert.assertTrue(added, "Product should be added to cart");
        } catch (Exception e) {
            Assert.fail("Product add-to-cart validation failed: " + e.getMessage());
        }
    }

    // ✅ NEW: Validate cart icon item count
    @Then("cart icon should show item count")
    public void cart_icon_should_show_item_count() {
        try {
            WebElement cartCount = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-cart-count")));
            String countText = cartCount.getText().trim();
            Assert.assertTrue(Integer.parseInt(countText) > 0, "Cart icon should show item count");
        } catch (Exception e) {
            Assert.fail("Cart icon count validation failed: " + e.getMessage());
        }
    }
}
