package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.regex.Pattern;

public class ProductPage {
	private WebDriver driver;

	public ProductPage(WebDriver driver) {
		this.driver = driver;
	}

	public void addToCart() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		// Handle product attributes dynamically
		if (driver.findElements(By.id("product_attribute_1")).size() > 0) {
			new Select(driver.findElement(By.id("product_attribute_1"))).selectByIndex(1);
		}
		if (driver.findElements(By.id("product_attribute_2")).size() > 0) {
			new Select(driver.findElement(By.id("product_attribute_2"))).selectByIndex(1);
		}
		driver.findElement(By.xpath("/html/body/div[6]/div[3]/div/div[2]/div/div/form/div/div[1]/div[2]/div[6]/dl/dd[3]/ul/li[2]/input")).click();		 

		WebElement addBtn = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[id^='add-to-cart-button']")));
		addBtn.click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".bar-notification.success")));

		wait.until(ExpectedConditions.textMatches(By.cssSelector("span.cart-qty"), Pattern.compile("\\(\\d+\\)")));
	}

	public void openCart() {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".bar-notification.success")));

	    WebElement cartLink = wait.until(ExpectedConditions
	            .elementToBeClickable(By.cssSelector("a.ico-cart")));
	    cartLink.click();
	}

}
