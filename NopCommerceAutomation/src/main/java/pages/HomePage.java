package pages;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import utils.ConfigReader;

public class HomePage {
    private WebDriver driver;
    private By searchBox = By.id("small-searchterms");
    private By searchBtn = By.cssSelector("button[type='submit']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void open() { driver.get(ConfigReader.get("url")); }
    public void search(String text) {
        driver.findElement(searchBox).sendKeys(text);
        driver.findElement(searchBtn).click();
    }
    public void clickLoginLink() {
        driver.findElement(By.className("ico-login")).click();
    }

}
