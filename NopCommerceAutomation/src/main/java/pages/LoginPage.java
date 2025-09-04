package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private WebDriver driver;

    private By emailInput = By.id("Email");
    private By passwordInput = By.id("Password");
    private By loginButton = By.cssSelector("button.login-button");
    private By errorMessage = By.cssSelector(".validation-summary-errors");
    private By accountLink = By.cssSelector("a.ico-account");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterEmail(String email) {
        driver.findElement(emailInput).clear();
        driver.findElement(emailInput).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    public boolean isErrorDisplayed() {
        return driver.findElements(errorMessage).size() > 0;
    }

    public boolean isUserLoggedIn() {
        // User is logged in if "My account" link appears
        return driver.findElements(accountLink).size() > 0;
    }
}
