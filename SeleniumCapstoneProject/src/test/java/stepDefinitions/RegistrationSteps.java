package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import pages.RegistrationPage;

public class RegistrationSteps {

    private RegistrationPage registrationPage = new RegistrationPage();

    @Given("I start registration")
    public void i_start_registration() {
        registrationPage.startRegistration();
    }

    @When("I fill registration form with name {string}, email {string}, password {string}, and confirm password {string}")
    public void i_fill_registration_form(String name, String email, String password, String passwordCheck) {
        registrationPage.fillForm(name, email, password, passwordCheck);
    }

    @When("I submit the registration form")
    public void i_submit_the_registration_form() {
        registrationPage.submitForm();
    }

    @Then("registration should fail with an error")
    public void registration_should_fail_with_an_error() {
        Assert.assertTrue(registrationPage.isErrorDisplayed(), "Error message should be displayed");
    }

    @Then("registration should succeed")
    public void registration_should_succeed() {
        // Add validation logic based on your application flow or page redirects
        // For now, assume no error means success
        Assert.assertFalse(registrationPage.isErrorDisplayed(), "No errors should be present on successful registration");
    }
}
