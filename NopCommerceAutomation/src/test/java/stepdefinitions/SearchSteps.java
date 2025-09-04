package stepdefinitions;

import base.BaseTest;
import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.HomePage;
import pages.SearchResultsPage;

public class SearchSteps extends BaseTest {
    private HomePage homePage;
    private SearchResultsPage resultsPage;

    @Given("User opens nopCommerce home")
    public void user_opens_home() {
        homePage = new HomePage(getDriver());
        homePage.open();
    }

    @When("User searches for {string}")
    public void user_searches_for(String query) {
        homePage.search(query);
        resultsPage = new SearchResultsPage(getDriver());
    }

    @Then("Results are shown")
    public void results_are_shown() {
        Assert.assertTrue(resultsPage.hasResults(), "No results displayed!");
    }
}
