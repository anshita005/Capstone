package stepdefinitions;

import base.BaseTest;
import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.CartPage;
import pages.ProductsPage;

public class CartSteps extends BaseTest {

    private ProductsPage productsPage;
    private CartPage cartPage;

    @When("User adds product {string} to the cart")
    public void user_adds_product_to_the_cart(String productName) {
        productsPage = new ProductsPage(getDriver());
        productsPage.addProductToCartByName(productName);
    }

    @And("User opens the cart")
    public void user_opens_the_cart() {
        productsPage.openCart();
    }

    @Then("Cart should contain at least one item")
    public void cart_should_contain_at_least_one_item() {
        cartPage = new CartPage(getDriver());
        Assert.assertTrue(cartPage.hasItems(), "Cart is empty!");
    }

    @And("User proceeds to checkout")
    public void user_proceeds_to_checkout() {
        cartPage.clickCheckout();
    }
}
