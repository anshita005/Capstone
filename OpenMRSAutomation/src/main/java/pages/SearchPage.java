
package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class SearchPage extends BasePage {

  private final By searchBox = By.cssSelector(
      "#patient-search, input[name='patient-search'], input[type='search'], input[placeholder*='Search']");

  private final By resultItems = By.cssSelector(
      "[data-testid='patient-name'], " +                 
      "span.patients-name, " +                         
      "li[role='option'], " +                           
      "tr[role='row'] a, " +                             
      "a[href*='patient/']"                             
  );

  public SearchPage(WebDriver d) { super(d); }

  public void search(String term) {
    WebElement box = wait.visible(searchBox);
    box.click();
    box.clear();
    box.sendKeys(term);
    box.sendKeys(Keys.ENTER); 
    wait.until(wd -> !wd.findElements(resultItems).isEmpty());
  }

  public void openPatient(String name) {
    wait.until(wd -> !wd.findElements(resultItems).isEmpty());
    List<WebElement> items = driver.findElements(resultItems);

    WebElement target = null;
    for (WebElement el : items) {
      String txt = el.getText().trim();
      if (!txt.isEmpty() && txt.equalsIgnoreCase(name)) { target = el; break; }
    }
    if (target == null) target = items.get(0);

    try {
      new Actions(driver).moveToElement(target).perform();
    } catch (Exception ignored) {}
    try {
      target.click();
    } catch (Exception e) {
      ((JavascriptExecutor) driver).executeScript("arguments.click();", target);
    }
  }
}
