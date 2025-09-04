
package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import utils.WaitUtils;

public class BasePage {
  protected final WebDriver driver;
  protected final WaitUtils wait;
  public BasePage(WebDriver d) { this.driver = d; this.wait = new WaitUtils(d); }
  protected void click(By by) { wait.clickable(by).click(); }
  protected void type(By by, String txt) { WebElement e = wait.visible(by); e.clear(); e.sendKeys(txt); }
  protected String text(By by) { return wait.visible(by).getText(); }
  protected boolean shown(By by) { try { return wait.visible(by).isDisplayed(); } catch (Exception e) { return false; } }
  protected boolean exists(By by) { try { return driver.findElements(by).size() > 0; } catch (Exception e) { return false; } }
  protected void selectByText(By by, String value) { new Select(wait.visible(by)).selectByVisibleText(value); }
}
