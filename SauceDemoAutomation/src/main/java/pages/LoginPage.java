package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.ConfigReader;
import utils.WaitUtils;

public class LoginPage {
    private final WebDriver driver;

    @FindBy(id = "user-name")
    private WebElement txtUsername;

    @FindBy(id = "password")
    private WebElement txtPassword;

    @FindBy(id = "login-button")
    private WebElement btnLogin;

    @FindBy(css = "[data-test='error']")
    private WebElement lblError;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get(ConfigReader.get("url"));
    }

    public void login(String username, String password) {
        txtUsername.clear();
        txtUsername.sendKeys(username);
        txtPassword.clear();
        txtPassword.sendKeys(password);
        btnLogin.click();
    }

    public String getError() {
        return WaitUtils.waitForVisible(driver, lblError, 5).getText();
    }
}
