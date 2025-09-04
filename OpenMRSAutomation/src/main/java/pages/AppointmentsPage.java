package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class AppointmentsPage extends BasePage {

  private final By createNewBtn = By.xpath("//button[contains(.,'Create new appointment')]");
  private final By patientSearchBox = By.cssSelector("input[placeholder*='Search for a patient']");
  private final By patientSearchBtn = By.xpath("//button[contains(.,'Search')]");
  private final By patientInfoLinkTpl = By.xpath("//span[normalize-space()='%s']");

  private final By serviceDropdown = By.id("service");
  private final By appointmentTypeDropdown = By.id("appointmentType");
  private final By durationInput = By.id("duration");

  private final By saveBtn = By.xpath("//button[contains(.,'Save and close') or contains(.,'Confirm')]");
  private final By expectedTab = By.xpath("//button[contains(.,'Expected')]");
  private final By appointmentTable = By.cssSelector("table");

  public AppointmentsPage(WebDriver d) {
    super(d);
  }

  private void clickPatientName(String patientName) {
    By locator = By.xpath(String.format("//span[normalize-space()='%s']", patientName));
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    WebElement el = driver.findElement(locator);
    try {
      el.click();
    } catch (Exception e) {
      ((JavascriptExecutor) driver).executeScript("arguments.click();", el);
    }
  }

  private void selectServiceByValue(String value) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    wait.until(ExpectedConditions.elementToBeClickable(serviceDropdown));
    new Select(driver.findElement(serviceDropdown)).selectByValue(value);
  }

  private void selectAppointmentTypeByValue(String value) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    wait.until(ExpectedConditions.elementToBeClickable(appointmentTypeDropdown));
    new Select(driver.findElement(appointmentTypeDropdown)).selectByValue(value);
  }

  private void setDurationTo60() {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    wait.until(ExpectedConditions.visibilityOfElementLocated(durationInput));
    WebElement durationEl = driver.findElement(durationInput);
    durationEl.clear();
    durationEl.sendKeys("60");
  }

  public boolean createAppointment(String patient, String date, String time, String serviceValue, String apptTypeValue) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

    wait.until(ExpectedConditions.elementToBeClickable(createNewBtn));
    click(createNewBtn);

    wait.until(ExpectedConditions.visibilityOfElementLocated(patientSearchBox));
    type(patientSearchBox, patient);

    wait.until(ExpectedConditions.elementToBeClickable(patientSearchBtn));
    click(patientSearchBtn);

    clickPatientName(patient);

    selectServiceByValue(serviceValue);
    selectAppointmentTypeByValue(apptTypeValue);
    setDurationTo60();

    wait.until(ExpectedConditions.elementToBeClickable(saveBtn));
    click(saveBtn);

    wait.until(ExpectedConditions.elementToBeClickable(expectedTab));
    return true;
  }

  public boolean appointmentDisplayed(String patientName, String service, String type) {
    click(expectedTab);
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    By rowLocator = By.xpath(
      "//tr[td[contains(normalize-space(),'" + patientName + "')] and " +
      "td[contains(normalize-space(),'" + service + "')] and " +
      "td[contains(normalize-space(),'" + type + "')]]"
    );
    try {
      wait.until(ExpectedConditions.visibilityOfElementLocated(rowLocator));
      return true;
    } catch (TimeoutException e) {
      return false;
    }
  }
}
