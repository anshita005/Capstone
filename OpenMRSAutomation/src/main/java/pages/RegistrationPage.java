// src/main/java/pages/RegistrationPage.java
package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import utils.ConfigReader;

public class RegistrationPage extends BasePage {

  // Basic demographics
  private final By firstName = By.xpath(
      "//label[normalize-space()='First name']/following::input[2] | //input[@name='givenName'] | //input[@aria-label='First name']");
  private final By familyName = By.xpath(
      "//label[normalize-space()='Family name']/following::input[2] | //input[@name='familyName'] | //input[@aria-label='Family name']");
  private By sexLabel(String v) {
    return By.xpath("//label[normalize-space()='" + v + "'] | //span[normalize-space()='" + v + "']/parent::label");
  }

  // DOB fields
  private final By birthDateSingle = By.cssSelector("input[aria-label='Birthdate'], input[type='date']");
  private final By birthDay = By.xpath("//label[normalize-space()='Day']/following::input[2] | //input[@name='birthdateDay']");
  private final By birthMonth = By.xpath("//label[normalize-space()='Month']/following::input[2] | //input[@name='birthdateMonth']");
  private final By birthYear = By.xpath("//label[normalize-space()='Year']/following::input[2] | //input[@name='birthdateYear']");

  // Phone and submit
  private final By phoneNumber = By.xpath(
      "//label[contains(normalize-space(),'Phone') or contains(normalize-space(),'Telephone')]/following::input[2] | //input[@name='phoneNumber']");
  private final By registerBtn = By.xpath("//button[normalize-space()='Register patient']");

  public RegistrationPage(WebDriver d) { super(d); }

  public void register(String gn, String fn, String sex, String d, String m, String y, String addr, String phone) {
    // Names
    type(firstName, gn);
    type(familyName, fn);

    // Sex: robust click
    clickSexSimple(sex); // uses label/input resolution below [6][9]

    // DOB (manual or automatic)
    boolean manual = Boolean.parseBoolean(safeGet("manualDob", "true"));
    long pauseMs = safeLong("manualWaitMillis", 8000);
    if (manual) {
      sleepMillis(pauseMs);
    } else {
      if (exists(birthDateSingle)) {
        setDate(birthDateSingle, String.format("%02d/%02d/%s", Integer.parseInt(d), monthToNumber(m), y));
      } else {
        type(birthDay, d);
        type(birthMonth, m);
        type(birthYear, y);
      }
    }

    if (exists(phoneNumber)) type(phoneNumber, phone);

    click(registerBtn);
  }

  // FIXED: Accepts a sex text, finds the label and its input, scrolls, then clicks the input or the label
  public void clickSexSimple(String sexText) {
    WebElement label = wait.visible(sexLabel(sexText)); // a label element [6]
    // Try to resolve the associated input via "for" attribute
    WebElement target = null;
    try {
      String forId = label.getAttribute("for");
      if (forId != null && !forId.isBlank()) {
        target = driver.findElement(By.id(forId)); // input[type=radio] [6][9]
      }
    } catch (NoSuchElementException ignored) {}

    if (target == null) {
      // Fallback: input preceding the label in same container
      try {
        target = label.findElement(By.xpath(".//preceding::input[@type='radio'][12]"));
      } catch (NoSuchElementException e) {
        target = label; // last resort: click the label itself
      }
    }

  }

  private void setDate(By field, String ddMMyyyy) {
    WebElement e = wait.visible(field);
    e.click();
    e.sendKeys(Keys.chord(Keys.CONTROL, "a"));
    e.sendKeys(ddMMyyyy);
    e.sendKeys(Keys.ENTER);
    try { new Actions(driver).sendKeys(Keys.ESCAPE).perform(); } catch (Exception ignored) {}
  }

  private void sleepMillis(long ms) {
    try { Thread.sleep(ms); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
  }

  private long safeLong(String key, long def) {
    try { String v = ConfigReader.get(key); return (v == null || v.isBlank()) ? def : Long.parseLong(v); }
    catch (Exception e) { return def; }
  }

  private int monthToNumber(String m) {
    String s = m.trim().toLowerCase();
    switch (s) {
      case "1": case "01": case "jan": case "january": return 1;
      case "2": case "02": case "feb": case "february": return 2;
      case "3": case "03": case "mar": case "march": return 3;
      case "4": case "04": case "apr": case "april": return 4;
      case "5": case "05": case "may": return 5;
      case "6": case "06": case "jun": case "june": return 6;
      case "7": case "07": case "jul": case "july": return 7;
      case "8": case "08": case "aug": case "august": return 8;
      case "9": case "09": case "sep": case "sept": case "september": return 9;
      case "10": case "oct": case "october": return 10;
      case "11": case "nov": case "november": return 11;
      case "12": case "dec": case "december": return 12;
      default: throw new IllegalArgumentException("Unrecognized month: " + m);
    }
  }

  private String safeGet(String key, String def) {
    try { String v = ConfigReader.get(key); return (v == null || v.isBlank()) ? def : v; }
    catch (Exception e) { return def; }
  }
}
