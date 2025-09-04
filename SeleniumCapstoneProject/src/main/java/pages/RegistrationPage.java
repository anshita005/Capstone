package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegistrationPage extends BasePage {

    @FindBy(id = "createAccountSubmit")
    private WebElement startRegistrationButton;

    @FindBy(id = "ap_customer_name")
    private WebElement customerNameInput;

    @FindBy(id = "ap_email")
    private WebElement emailInput;

    @FindBy(id = "ap_password")
    private WebElement passwordInput;

    @FindBy(id = "ap_password_check")
    private WebElement passwordCheckInput;

    @FindBy(id = "continue")
    private WebElement createAccountButton;

    @FindBy(css = ".a-alert-content")
    private WebElement errorMessage;

    public RegistrationPage() {
        super();
        PageFactory.initElements(driver, this);
    }

    public void startRegistration() {
        clickElement(startRegistrationButton);
    }

    public void fillForm(String name, String email, String password, String passwordCheck) {
        enterText(customerNameInput, name.trim());
        enterText(emailInput, email.trim());
        enterText(passwordInput, password.trim());
        enterText(passwordCheckInput, passwordCheck.trim());
    }

    public void submitForm() {
        clickElement(createAccountButton);
    }

    public boolean isErrorDisplayed() {
        return isElementDisplayed(errorMessage);
    }
}
