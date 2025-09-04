
package stepDefinitions;

import io.cucumber.java.en.*;
import pages.*;
import utils.DriverManager;

public class SearchSteps {
  private final HomePage home = new HomePage(DriverManager.get());
  private SearchPage search;

  @When("open search")
  public void open_search() { home.openGlobalSearch(); search = new SearchPage(DriverManager.get()); }

  @When("search {string} and open")
  public void search_and_open(String name) { search.search(name); search.openPatient(name); }
}
