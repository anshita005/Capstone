package stepdefinitions;

import base.BaseTest;
import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.LoginPage;
import pages.ProductsPage;

public class LoginSteps extends BaseTest {

    private LoginPage loginPage;
    private ProductsPage productsPage;

    @Given("User is on Login page")
    public void user_is_on_login_page() {
        loginPage = new LoginPage(getDriver());
        loginPage.open();
    }

    @When("User logs in with username {string} and password {string}")
    public void user_logs_in_with_username_and_password(String username, String password) {
        loginPage.login(username, password);
    }

    @Then("User should land on Products page")
    public void user_should_land_on_products_page() {
        productsPage = new ProductsPage(getDriver());
        Assert.assertTrue(productsPage.isLoaded(), "Products page did not load.");
    }

    @Then("Error message {string} should be displayed")
    public void error_message_should_be_displayed(String expected) {
        Assert.assertTrue(loginPage.getError().contains(expected), "Error text mismatch!");
    }
}
