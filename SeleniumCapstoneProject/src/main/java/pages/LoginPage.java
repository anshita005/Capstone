package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    @FindBy(id = "nav-link-accountList")
    private WebElement signInLink;

    @FindBy(id = "ap_email")
    private WebElement emailInput;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(id = "ap_password")
    private WebElement passwordInput;

    @FindBy(id = "signInSubmit")
    private WebElement signInButton;

    @FindBy(xpath = "//div[contains(text(),'Your password is incorrect')]")
    private WebElement passwordError;

    @FindBy(xpath = "//div[contains(text(),'We cannot find an account')]")
    private WebElement emailError;

    // The working Amazon login URL with required OpenID parameters
    private static final String AMAZON_LOGIN_URL = "https://www.amazon.com/ap/signin?openid.pape.max_auth_age=0&openid.return_to=https%3A%2F%2Fwww.amazon.com%2F%3Fref_%3Dnav_ya_signin&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.assoc_handle=usflex&openid.mode=checkid_setup&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0";

    public LoginPage() {
        super();
        PageFactory.initElements(driver, this);
    }

    public void openLoginPage() {
        try {
            System.out.println("🌐 Opening Amazon login page with required parameters");
            
            // Use the full working URL with OpenID parameters
            driver.get(AMAZON_LOGIN_URL);
            
            // Wait for email input to be visible
            wait.until(ExpectedConditions.visibilityOf(emailInput));
            System.out.println("✅ Login page opened successfully");
            
        } catch (Exception e) {
            System.out.println("❌ Failed to open login page directly, trying fallback method");
            
            try {
                // Fallback: Go to homepage and click sign-in link
                driver.get("https://www.amazon.com");
                Thread.sleep(2000);
                
                wait.until(ExpectedConditions.elementToBeClickable(signInLink));
                signInLink.click();
                
                wait.until(ExpectedConditions.visibilityOf(emailInput));
                System.out.println("✅ Login page opened via sign-in link");
                
            } catch (Exception fallbackException) {
                System.out.println("❌ Both methods failed: " + fallbackException.getMessage());
                throw new RuntimeException("Cannot open login page", fallbackException);
            }
        }
    }

    public void enterEmail(String email) {
        try {
            wait.until(ExpectedConditions.visibilityOf(emailInput));
            emailInput.clear();
            emailInput.sendKeys(email);
            System.out.println("✅ Email entered: " + email);
        } catch (Exception e) {
            System.out.println("❌ Failed to enter email: " + e.getMessage());
            throw new RuntimeException("Cannot enter email", e);
        }
    }

    public void clickContinue() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(continueButton));
            continueButton.click();
            
            // Wait for password field to appear
            wait.until(ExpectedConditions.visibilityOf(passwordInput));
            System.out.println("✅ Continue button clicked, password field appeared");
        } catch (Exception e) {
            System.out.println("❌ Failed to click continue: " + e.getMessage());
            throw new RuntimeException("Cannot click continue button", e);
        }
    }

    public void enterPassword(String password) {
        try {
            wait.until(ExpectedConditions.visibilityOf(passwordInput));
            passwordInput.clear();
            passwordInput.sendKeys(password);
            System.out.println("✅ Password entered");
        } catch (Exception e) {
            System.out.println("❌ Failed to enter password: " + e.getMessage());
            throw new RuntimeException("Cannot enter password", e);
        }
    }

    public void clickSignIn() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(signInButton));
            signInButton.click();
            System.out.println("🧩 Please solve CAPTCHA manually if it appears.");
            System.out.println("⏳ Waiting 10 seconds...");
            Thread.sleep(10000); // Wait 10 seconds for manual CAPTCHA solving

            String finalUrl = driver.getCurrentUrl();

            if (!finalUrl.contains("ap/signin")) {
                System.out.println("✅ Login successful or CAPTCHA resolved, redirected from login page");
            } else {
                System.out.println("⚠ Still on login page after wait - may need manual intervention");
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to click sign in: " + e.getMessage());
            throw new RuntimeException("Cannot click sign in button", e);
        }
    }



    public boolean isAlreadyLoggedIn() {
        try {
            // Go to a page that requires login to check status
            driver.get("https://www.amazon.com");
            Thread.sleep(2000);
            
            // Look for user account indicators
            String pageSource = driver.getPageSource().toLowerCase();
            return pageSource.contains("hello,") || pageSource.contains("account & lists") && 
                   !pageSource.contains("sign in");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPasswordErrorDisplayed() {
        try {
            return passwordError.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isEmailErrorDisplayed() {
        try {
            return emailError.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLoginPageOpened() {
        try {
            return emailInput.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Helper method to perform complete login flow
    public void performLogin(String email, String password) {
        openLoginPage();
        enterEmail(email);
        clickContinue();
        enterPassword(password);
        clickSignIn();
    }
}
