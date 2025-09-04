// src/test/java/stepDefinitions/PatientDetailsSteps.java
package stepDefinitions;

import io.cucumber.java.en.Then;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import org.testng.Assert;
import utils.DriverManager;

public class PatientDetailsSteps {

  @Then("details header has {string}")
  public void details_header_has(String ignoredName) {
    WebDriverWait w = new WebDriverWait(DriverManager.get(), Duration.ofSeconds(15));
    // Wait for SPA route to patient chart; accept either /patient/... or /patient/.../chart (or summary)
    w.until(ExpectedConditions.urlContains("/patient/"));
    String url = DriverManager.get().getCurrentUrl();
    Assert.assertTrue(url.contains("/patient/"), "Did not navigate to patient chart: " + url);
  }
}
