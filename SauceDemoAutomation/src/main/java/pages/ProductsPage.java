package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class ProductsPage {
    private final WebDriver driver;

    private final By title = By.cssSelector(".title");
    private final By cartIcon = By.id("shopping_cart_container");

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isLoaded() {
        return driver.findElement(title).getText().equalsIgnoreCase("Products");
    }

    public void addProductToCartByName(String productName) {
		String formatted = productName.toLowerCase().replace(" ", "-");
		String id = "add-to-cart-" + formatted;
		driver.findElement(By.id(id)).click();
	}

    public void openCart() {
        driver.findElement(cartIcon).click();
    }
}
