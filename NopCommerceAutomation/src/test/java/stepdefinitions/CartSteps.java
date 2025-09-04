package stepdefinitions;

import base.BaseTest;
import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.ProductPage;
import pages.SearchResultsPage;
import pages.CartPage;

public class CartSteps extends BaseTest {
    private SearchResultsPage resultsPage;
    private ProductPage productPage;
    private CartPage cartPage;

    @When("User opens first product")
    public void user_opens_first_product() {
        resultsPage = new SearchResultsPage(getDriver());
        resultsPage.openFirst();
    }

    @When("User adds product to cart")
    public void user_adds_product_to_cart() {
        productPage = new ProductPage(getDriver());
        productPage.addToCart();
    }

    @When("User opens cart")
    public void user_opens_cart() {
        productPage.openCart();
        cartPage = new CartPage(driver);
    }

    @Then("Cart has items")
    public void cart_has_items() {
        Assert.assertTrue(cartPage.hasItems(), "Cart is empty!");
    }
}
