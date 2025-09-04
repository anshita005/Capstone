package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import java.time.Duration;
import java.util.Objects;

import utils.ConfigReader;

public class BaseTest {
	protected static WebDriver driver;

	public void setUp() {
		String browser = ConfigReader.get("browser");
		if (Objects.equals(browser, "chrome")) {
			driver = new ChromeDriver();
		} else if (Objects.equals(browser, "firefox")) {
			driver = new FirefoxDriver();
		} else if (Objects.equals(browser, "edge")) {
			System.setProperty("webdriver.edge.driver", "C:\\Users\\anshi\\Desktop\\SeleniumDemo\\edgedriver_win64\\msedgedriver.exe");
			driver = new EdgeDriver();
		} else {
			throw new RuntimeException("Unsupported browser: " + browser);
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(ConfigReader.get("url"));
	}

	public static WebDriver getDriver() {
		return driver;
	}

	public void tearDown() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
	}
}
