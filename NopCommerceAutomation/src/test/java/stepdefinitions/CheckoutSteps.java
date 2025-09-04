package stepdefinitions;

import base.BaseTest;
import io.cucumber.java.en.*;
import pages.CartPage;
import org.testng.Assert;

public class CheckoutSteps extends BaseTest {
    private CartPage cartPage;

    @When("User proceeds to checkout")
    public void user_proceeds_to_checkout() {
        cartPage = new CartPage(getDriver());
        cartPage.proceedToCheckout();
    }

    @Then("Checkout page should appear")
    public void checkout_page_should_appear() {
        String url = getDriver().getCurrentUrl();
        Assert.assertTrue(url.contains("checkout"), "Did not navigate to checkout, current URL: " + url);
    }
}
