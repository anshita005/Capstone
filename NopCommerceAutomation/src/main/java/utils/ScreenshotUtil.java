package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Paths;
import java.util.UUID;

public class ScreenshotUtil {
    public static String takeScreenshot(WebDriver driver, String namePrefix) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String fileName = namePrefix + "_" + UUID.randomUUID() + ".png";
            String path = Paths.get("reports", "screenshots", fileName).toString();
            File dest = new File(path);
            dest.getParentFile().mkdirs();
            FileUtils.copyFile(src, dest);
            return dest.getAbsolutePath();
        } catch (Exception e) {
            return null;
        }
    }
}
