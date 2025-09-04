package stepdefinitions;

import base.BaseTest;
import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.CheckoutPage;

public class CheckoutSteps extends BaseTest {

    private CheckoutPage checkoutPage;

    @When("User fills checkout info with {string}, {string}, {string}")
    public void user_fills_checkout_info_with(String first, String last, String zip) {
        checkoutPage = new CheckoutPage(getDriver());
        checkoutPage.fillInfo(first, last, zip);
    }

    @And("User completes the purchase")
    public void user_completes_the_purchase() {
        checkoutPage.finish();
    }

    @Then("Order success message {string} should be shown")
    public void order_success_message_should_be_shown(String expected) {
        Assert.assertEquals(checkoutPage.getSuccessMessage(), expected);
    }
}
