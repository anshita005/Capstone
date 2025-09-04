// src/main/java/utils/DriverManager.java
package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverManager {
  private static final ThreadLocal<WebDriver> TL = new ThreadLocal<>();

  public static void init() {
    ChromeOptions options = new ChromeOptions();
    if (Boolean.parseBoolean(ConfigReader.get("headless"))) options.addArguments("--headless=new");
    options.addArguments("--remote-allow-origins=*");
    WebDriver d = new ChromeDriver(options); // Selenium Manager resolves the driver
    d.manage().window().maximize();
    TL.set(d);
  }

  public static WebDriver get() { return TL.get(); }

  public static void quit() {
    WebDriver d = TL.get();
    if (d != null) {
      d.quit();
      TL.remove();
    }
  }
}
