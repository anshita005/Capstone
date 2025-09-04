package pages;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;

public class SearchResultsPage {
    private WebDriver driver;
    private By results = By.cssSelector(".item-box .product-title a");

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean hasResults() {
        return !driver.findElements(results).isEmpty();
    }

    public void openFirst() {
        driver.findElements(results).get(0).click();
    }
}
