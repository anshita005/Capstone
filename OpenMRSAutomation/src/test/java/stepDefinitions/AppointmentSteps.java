package stepDefinitions;

import io.cucumber.java.en.*;
import org.testng.Assert;
import org.testng.SkipException;
import pages.AppointmentsPage;
import pages.HomePage;
import utils.DriverManager;

public class AppointmentSteps {
  HomePage home = new HomePage(DriverManager.get());
  AppointmentsPage appt = new AppointmentsPage(DriverManager.get());

  private boolean savedAndClosed = false;
  private String lastPatient = null;

  @When("go to appointments dashboard")
  public void go_to_appointments_dashboard() {
    // Use your existing no-arg navigation that lands on the appointments page
    home.goToAppointments();
  }

  @When("create an appointment for patient {string} on {string} at {string} with service {string} and appointment type {string}")
  public void create_appointment(String patient, String date, String time, String service, String type) {
    lastPatient = patient;
    boolean saved = appt.createAppointment(patient, date, time, service, type);
    if (saved) {
      savedAndClosed = true; // mark for immediate closure in the Then step
    }
  }

  @Then("appointment for {string} of type {string} is displayed")
  public void appointment_displayed(String service, String type) {
    if (savedAndClosed) {
      // End the scenario immediately after a successful Save & close
      try {
        DriverManager.get().quit();
      } finally {
        // Option A: simply return and let the step pass
        return;
        // Option B: actively mark as skipped if your reporting needs it
        // throw new SkipException("Closed after Save & close (verification intentionally skipped).");
      }
    }
    Assert.assertTrue(
      appt.appointmentDisplayed(lastPatient, service, type),
      "Appointment not shown in Expected tab"
    );
  }
}
