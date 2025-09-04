// src/main/java/hooks/TestHooks.java
package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utils.ConfigReader;
import utils.DriverManager;

public class TestHooks {

  @Before
  public void setUp() {
    DriverManager.init();
    DriverManager.get().get(ConfigReader.get("baseUrl"));
  }

  @After
  public void tearDown(Scenario scenario) {
    WebDriver d = DriverManager.get();
    try {
      if (d != null && scenario.isFailed()) {
        byte[] img = ((TakesScreenshot) d).getScreenshotAs(OutputType.BYTES);
        scenario.attach(img, "image/png", "failure");
      }
    } finally {
      DriverManager.quit();
    }
  }
}
