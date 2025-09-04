package stepDefinitions;

import utils.ConfigReader;
import utils.DriverManager;
import io.cucumber.java.en.Given;

public class CommonSteps {
  @Given("the app entry is open")
  public void app_entry_open() {
    DriverManager.get().get(ConfigReader.get("baseUrl"));
  }
}
