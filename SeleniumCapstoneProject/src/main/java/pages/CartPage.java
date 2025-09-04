package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;


public class CartPage extends BasePage {
    
    @FindBy(css = "#sc-active-cart")
    private WebElement activeCart;
    
    @FindBy(css = "[data-name='Active Items'] [data-item-count]")
    private List<WebElement> cartItems;
    
    @FindBy(css = "[data-name='Active Items'] .sc-product-title")
    private List<WebElement> cartItemTitles;
    
    @FindBy(css = "[data-name='Active Items'] .sc-price")
    private List<WebElement> cartItemPrices;
    
    @FindBy(css = "[value='Delete']")
    private List<WebElement> deleteButtons;
    
    @FindBy(id = "sc-subtotal-amount-activecart")
    private WebElement subtotalAmount;
    
    @FindBy(name = "proceedToRetailCheckout")
    private WebElement proceedToCheckoutButton;
    
    @FindBy(css = "#sc-active-cart h1")
    private WebElement cartHeader;
    
    public int getCartItemsCount() {
        return cartItems.size();
    }
    
    public String getCartItemTitle(int index) {
        if (index >= 0 && index < cartItemTitles.size()) {
            return getElementText(cartItemTitles.get(index));
        }
        return "";
    }
    
    public String getCartItemPrice(int index) {
        if (index >= 0 && index < cartItemPrices.size()) {
            return getElementText(cartItemPrices.get(index));
        }
        return "";
    }
    
    public void deleteCartItem(int index) {
        if (index >= 0 && index < deleteButtons.size()) {
            clickElement(deleteButtons.get(index));
        }
    }
    
    public String getSubtotalAmount() {
        try {
            return getElementText(subtotalAmount);
        } catch (Exception e) {
            return "Subtotal not available";
        }
    }
    
    public void proceedToCheckout() {
        if (isElementDisplayed(proceedToCheckoutButton)) {
            clickElement(proceedToCheckoutButton);
        }
    }
    
    public boolean isCartEmpty() {
        return cartItems.isEmpty();
    }
    
    public boolean isCartPageLoaded() {
        return isElementDisplayed(cartHeader);
    }
    
    public boolean isProceedToCheckoutAvailable() {
        return isElementDisplayed(proceedToCheckoutButton);
    }
    
    public String getCartHeaderText() {
        return getElementText(cartHeader);
    }
    
    public void navigateToCart() {
        driver.get("https://www.amazon.com/gp/cart/view.html");
        waitForPageToLoad();
    }
}