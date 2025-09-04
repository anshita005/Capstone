package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class CartPage {
    private final WebDriver driver;

    private final By checkoutBtn = By.id("checkout");
    private final By cartItem = By.cssSelector(".cart_item");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean hasItems() {
        return !driver.findElements(cartItem).isEmpty();
    }

    public void clickCheckout() {
        driver.findElement(checkoutBtn).click();
    }
}
