package stepdefinitions;

import base.BaseTest;
import io.cucumber.java.*;
import utils.ScreenshotUtil;
import utils.ExtentManager;
import com.aventstack.extentreports.ExtentReports;

public class Hooks extends BaseTest {
    private static ExtentReports extent = ExtentManager.getInstance();

    @Before
    public void beforeScenario(Scenario scenario) {
        setUp();
        scenario.log("Starting: " + scenario.getName());
    }

    @After
    public void afterScenario(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                String path = ScreenshotUtil.takeScreenshot(getDriver(), scenario.getName().replaceAll("\\W+","_"));
                if (path != null) {
                    byte[] bytes = java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(path));
                    scenario.attach(bytes, "image/png", "Failure Screenshot");
                }
            }
        } catch (Exception ignored) {}
        tearDown();
        extent.flush();
    }
}
