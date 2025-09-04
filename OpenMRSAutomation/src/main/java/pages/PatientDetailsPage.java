// src/main/java/pages/PatientDetailsPage.java
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class PatientDetailsPage extends BasePage {
  private final By headerTestId = By.cssSelector("[data-testid='patient-header-name']");
  private final By headerH4Class = By.cssSelector("h4.patient-name");
  private final By headerGeneric = By.xpath("//header//h4 | //header//*[@data-testid='patient-header-name']");

  public PatientDetailsPage(WebDriver d) { super(d); }

  public String name() {
    wait.until(wd -> wd.getCurrentUrl().contains("/patient/"));

    if (exists(headerTestId)) return text(headerTestId);
    if (exists(headerH4Class)) return text(headerH4Class);

    String n = text(headerGeneric);
    try { ((JavascriptExecutor) driver).executeScript("arguments.scrollIntoView({block:'center'})",
            driver.findElement(headerGeneric)); } catch (Exception ignored) {}
    return n;
  }
}
