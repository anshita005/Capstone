// src/main/java/utils/WaitUtils.java
package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.function.Function;

public class WaitUtils {
  private final WebDriver driver;
  private final WebDriverWait wait;
  public WaitUtils(WebDriver d) {
    this.driver = d;
    this.wait = new WebDriverWait(d, Duration.ofSeconds(Long.parseLong(ConfigReader.get("explicitWaitSec"))));
  }
  public WebElement visible(By by) { return wait.until(ExpectedConditions.visibilityOfElementLocated(by)); }
  public WebElement clickable(By by) { return wait.until(ExpectedConditions.elementToBeClickable(by)); }
  public <T> T until(Function<WebDriver,T> cond) { return wait.until(cond); }
}
