package stepdefinitions;

import base.BaseTest;
import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.LoginPage;

public class LoginSteps extends BaseTest {
    private LoginPage loginPage;
    @When("User clicks login link")
    public void user_clicks_login_link() {
        getDriver().get("https://demo.nopcommerce.com/login");
        loginPage = new LoginPage(getDriver());
    }

    @When("enters email {string} and password {string}")
    public void enters_email_and_password(String email, String password) {
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLogin();
    }

    @Then("Error message is shown")
    public void error_message_is_shown() {
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message not displayed!");
    }

    @Then("User is logged in successfully")
    public void user_is_logged_in_successfully() {
        Assert.assertTrue(loginPage.isUserLoggedIn(), "User login failed!");
    }
}
