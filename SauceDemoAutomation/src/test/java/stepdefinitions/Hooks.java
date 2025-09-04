package stepdefinitions;

import base.BaseTest;
import io.cucumber.java.*;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.nio.file.Paths;

public class Hooks extends BaseTest {

    @Before
    public void beforeScenario() {
        setUp();
    }

    @After
    public void afterScenario(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
                String path = Paths.get("reports", "screenshots", scenario.getName().replaceAll("\\W+", "_") + ".png").toString();
                File dest = new File(path);
                dest.getParentFile().mkdirs();
                FileUtils.copyFile(src, dest);
                scenario.attach(FileUtils.readFileToByteArray(dest), "image/png", "Failure Screenshot");
            }
        } catch (Exception ignored) {}
        tearDown();
    }
}
