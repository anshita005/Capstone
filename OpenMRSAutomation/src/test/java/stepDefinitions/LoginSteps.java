package stepDefinitions;

import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.HomePage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.DriverManager;

public class LoginSteps {
  private final LoginPage login = new LoginPage(DriverManager.get());

  @Given("user is on login")
  public void on_login() { DriverManager.get().get(ConfigReader.get("baseUrl")); }

  @When("login default")
  public void login_default() {
    login.login(ConfigReader.get("username"), ConfigReader.get("password"), ConfigReader.get("location"));
  }

  @Then("home visible")
  public void home_visible() {
    Assert.assertTrue(new HomePage(DriverManager.get()).isLoggedIn(), "Home header not visible");
  }
}
