Feature: Clinic Appointment Booking

  Scenario: Schedule a new appointment for an existing patient
    Given user is on login
    When login default
    Then home visible
    When go to appointments dashboard
    When create an appointment for patient "James Martinez" on "2025-09-01" at "09:21" with service "General Medicine service" and appointment type "Scheduled"
    Then appointment for "General Medicine service" of type "Scheduled" is displayed