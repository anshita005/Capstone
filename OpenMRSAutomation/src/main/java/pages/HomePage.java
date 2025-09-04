package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class HomePage extends BasePage {
  private final By homeHeader = By.xpath("//main//h1[contains(normalize-space(),'Home')]");
  private final By addPatientBtn = By.xpath("//button[normalize-space()='Add patient' or @aria-label='Add patient']");
  private final By searchBtn = By.cssSelector(
      "button[aria-label='Search'], " +
      "button[aria-label='Search patient'], " +
      "[data-testid='search-button'], " +
      "button[title='Search'], " +
      "button[title='Search patient']");
  private final By tourClose = By.xpath("//button[contains(.,'Skip') or contains(.,'Got it') or @aria-label='Close']");
  private final By appointmentsLink = By.cssSelector("a.cds--side-nav__link[href*='appointments']");
  public HomePage(WebDriver d) { super(d); }
  public boolean isLoggedIn() {
    wait.until(wd -> wd.getCurrentUrl().contains("/spa/home"));
    if (exists(tourClose)) click(tourClose);
    return shown(homeHeader) || shown(addPatientBtn);
  }
  public void goToRegistration() {
    if (exists(tourClose)) click(tourClose);
    click(addPatientBtn);
  }
  public void openGlobalSearch() {
    try {
      click(searchBtn);
    } catch (Exception ignored) {
      String url = driver.getCurrentUrl();
      int i = url.indexOf("/spa");
      String baseSpa = (i > 0) ? url.substring(0, i + 4) : (url.endsWith("/") ? url + "spa" : url + "/spa");
      driver.get(baseSpa + "/patient-search");
    }
  }
  private String today() {
    return LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
  }
  public void goToAppointments(String date) {
    if (exists(tourClose)) click(tourClose);
    String day = (date == null || date.isBlank()) ? today() : date;
    String current = driver.getCurrentUrl();
    int i = current.indexOf("/spa");
    String baseSpa = (i > 0) ? current.substring(0, i + 4) : (current.endsWith("/") ? current + "spa" : current + "/spa");
    String appointmentsUrl = baseSpa + "/home/appointments/" + day;
    driver.get(appointmentsUrl);
    wait.until(wd -> wd.getCurrentUrl().contains("/appointments/" + day));
    By createNewBtn = By.xpath("//button[contains(.,'Create new appointment')]");
    wait.visible(createNewBtn);
  }
  // No-arg convenience: open Appointments for today
  public void goToAppointments() {
    goToAppointments(null);
  }
}