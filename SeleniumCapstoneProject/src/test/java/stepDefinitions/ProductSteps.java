package stepDefinitions;

import io.cucumber.java.en.*;
import pages.ProductPage;

public class ProductSteps {
    private ProductPage productPage;

    public ProductSteps() {
        this.productPage = new ProductPage();
    }

    @Given("I navigate to product page {string}")
    public void i_navigate_to_product_page(String url) {
        productPage.navigateToProduct(url);
    }

    @When("I search for {string} and select first product")
    public void i_search_for_and_select_first_product(String productName) {
        productPage.searchAndSelectFirstProduct(productName);
    }

    @And("I add the product to cart")
    public void i_add_the_product_to_cart() {
        productPage.addProductToCart();
    }
}
