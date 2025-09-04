package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.testng.Assert;

import pages.HomePage;
import pages.LoginPage;
import utils.DriverManager;

public class LoginSteps {

    private LoginPage loginPage = new LoginPage();
   
    @Given("I am on Amazon login page")
    public void i_am_on_amazon_login_page() {
        HomePage homePage = new HomePage(DriverManager.getDriver());
        homePage.navigateToHomePage();
        homePage.clickSignInLink();  // This opens the login page

        // Optionally, add assertion to check login page loaded
        LoginPage loginPage = new LoginPage();
        Assert.assertTrue(loginPage.isLoginPageOpened(), "Login page should be displayed");
    }


    @When("I enter email {string}")
    public void i_enter_email(String email) {
        loginPage.enterEmail(email);
    }

    @When("I click continue button")
    public void i_click_continue_button() {
        loginPage.clickContinue();
    }

    @When("I enter password {string}")
    public void i_enter_password(String password) {
        loginPage.enterPassword(password);
    }

    @When("I click sign in button")
    public void i_click_sign_in_button() {
        loginPage.clickSignIn();
    }

    @Then("login should fail with email error")
    public void login_should_fail_with_email_error() {
        Assert.assertTrue(loginPage.isEmailErrorDisplayed(), "Email error message should be displayed");
    }

    @Then("login should fail with password error")
    public void login_should_fail_with_password_error() {
        Assert.assertTrue(loginPage.isPasswordErrorDisplayed(), "Password error message should be displayed");
    }

    @Then("I should be logged in successfully")
    public void i_should_be_logged_in_successfully() {
        // Validate login succeeded by checking the URL or presence of account page
        String currentUrl = DriverManager.getDriver().getCurrentUrl();

        Assert.assertTrue(currentUrl.contains("amazon.com") && 
                          !currentUrl.contains("ap/signin"), 
                          "Expected to be logged in (URL does not contain signin)");

        // Or check for user account elements if needed
    }
}
