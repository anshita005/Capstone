package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Screenshot utility class for capturing screenshots
 */
public class Screenshot {

    public static String captureScreenshot(WebDriver driver, String testName) {
        try {
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String fileName = testName + "_" + timestamp + ".png";

            String screenshotPath = ConfigReader.getScreenshotPath();
            
            // Ensure the screenshot directory exists
            File directory = new File(screenshotPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String filePath = screenshotPath + fileName;
            File destinationFile = new File(filePath);

            FileUtils.copyFile(sourceFile, destinationFile);

            System.out.println("Screenshot captured: " + filePath);
            return filePath;

        } catch (IOException e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }

    // Fixed method name from 'captureScareshot' to 'captureScreenshot' and updated to use DriverManager
    public static String captureScreenshot(String testName) {
        return captureScreenshot(DriverManager.getDriver(), testName);
    }

    public static void createScreenshotDirectory() {
        File directory = new File(ConfigReader.getScreenshotPath());
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
}
