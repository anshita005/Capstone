package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.DriverManager;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    
    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }
    
    protected WebElement waitForElementToBeVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
    
    protected WebElement waitForElementToBeClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    
    protected WebElement waitForElementToBeVisible(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }
    
    protected void clickElement(WebElement element) {
        waitForElementToBeClickable(element).click();
    }
    
    protected void enterText(WebElement element, String text) {
        WebElement visibleElement = waitForElementToBeVisible(element);
        visibleElement.clear();
        visibleElement.sendKeys(text);
    }
    
    protected String getElementText(WebElement element) {
        return waitForElementToBeVisible(element).getText();
    }
    
    protected boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getPageTitle() {
        return driver.getTitle();
    }
    
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
    public void navigateBack() {
        driver.navigate().back();
    }
    
    public void refreshPage() {
        driver.navigate().refresh();
    }
    
    protected void waitForPageToLoad() {
        wait.until(webDriver -> ((org.openqa.selenium.JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }
}