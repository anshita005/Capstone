// src/main/java/utils/ScreenshotUtil.java
package utils;

import org.openqa.selenium.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtil {
  public static File capture(WebDriver d, String name) {
    try {
      File src = ((TakesScreenshot) d).getScreenshotAs(OutputType.FILE);
      BufferedImage img = ImageIO.read(src);
      new File(ConfigReader.get("screenshotDir")).mkdirs();
      File dest = new File(ConfigReader.get("screenshotDir") + File.separator +
          name + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS")) + ".png");
      ImageIO.write(img, "png", dest);
      return dest;
    } catch (Exception e) { return null; }
  }
}
