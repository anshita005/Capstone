package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {
    private WebDriver driver;

    private By cartItemRow = By.cssSelector("table.cart tbody tr");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean hasItems() {
        return driver.findElements(cartItemRow).size() > 0;
    }

    public void proceedToCheckout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkout")));

        WebElement terms = driver.findElement(By.id("termsofservice"));
        if (!terms.isSelected()) {
            terms.click();
        }

        driver.findElement(By.id("checkout")).click();
    }

}
