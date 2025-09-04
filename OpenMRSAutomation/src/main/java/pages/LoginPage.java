package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
  private final By username = By.cssSelector("#username, input[name='username']");
  private final By password = By.cssSelector("#password, input[name='password']");
  private final By continueOrLogin = By.xpath("//button[@id='kc-login' or contains(.,'Continue') or contains(.,'Next') or contains(.,'Log in') or contains(.,'Sign in') or @type='submit']");
  private final By legacyLocation = By.id("sessionLocation");
  private By locationTile(String loc) { return By.xpath("//button[normalize-space()='" + loc + "'] | //span[normalize-space()='" + loc + "']/ancestor::button"); }
  private final By locationContinue = By.xpath("//button[contains(.,'Continue') or contains(.,'Confirm') or @type='submit']");

  public LoginPage(WebDriver d) { super(d); }

  public void login(String user, String pass, String loc) {
    type(username, user);
    if (!isVisibleNow(password)) click(continueOrLogin);
    type(password, pass);
    click(continueOrLogin);

    if (exists(legacyLocation)) {
      click(legacyLocation);
      click(By.xpath("//select[@id='sessionLocation']/option[normalize-space()='" + loc + "']"));
      if (exists(continueOrLogin)) click(continueOrLogin);
    }
    if (exists(locationTile(loc))) {
      click(locationTile(loc));
      if (exists(locationContinue)) click(locationContinue);
    }
  }

  private boolean isVisibleNow(By by) {
    try { return driver.findElement(by).isDisplayed(); } catch (NoSuchElementException e) { return false; }
  }
}
