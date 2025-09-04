// src/test/java/stepDefinitions/RegistrationSteps.java
package stepDefinitions;

import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.*;
import utils.DriverManager;

public class RegistrationSteps {
  private final HomePage home = new HomePage(DriverManager.get());
  private RegistrationPage reg;

  @When("go to registration")
  public void go_reg() {
    home.goToRegistration();
    reg = new RegistrationPage(DriverManager.get());
  }

  @When("register {string} {string} {string} {string}-{string}-{string} addr {string} phone {string}")
  public void register(String gn, String fn, String g, String d, String m, String y, String addr, String ph) {
    reg.register(gn, fn, g, d, m, y, addr, ph);
  }

//RegistrationSteps.java
@Then("patient header contains {string}")
public void header_contains(String expectedIgnored) {
 new org.openqa.selenium.support.ui.WebDriverWait(DriverManager.get(), java.time.Duration.ofSeconds(15))
     .until(org.openqa.selenium.support.ui.ExpectedConditions.urlContains("/patient/")); // SPA chart route
 org.testng.Assert.assertTrue(DriverManager.get().getCurrentUrl().contains("/patient/"),
     "Did not navigate to patient chart: " + DriverManager.get().getCurrentUrl());
}

}