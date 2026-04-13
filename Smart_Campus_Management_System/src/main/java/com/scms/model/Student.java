// Declare that this file belongs to the com.scms.model package.
package com.scms.model;

// Declare the student class.
public class Student extends User {

    // Declare the programme.
    private String programme;
    // Declare the year of study.
    private int yearOfStudy;

    // Construct a new student instance.
    public Student(String id, String name, String email, String password, String programme, int yearOfStudy) {
        // Super.
        super(id, name, email, password, UserRole.STUDENT);
        // Set programme using the computed value.
        this.programme = programme;
        // Set year of study using the computed value.
        this.yearOfStudy = yearOfStudy;
    }

    // Define the getter for programme.
    public String getProgramme() { return programme; }
    // Define the getter for year of study.
    public int getYearOfStudy() { return yearOfStudy; }
    // Define the setter for programme.
    public void setProgramme(String programme) { this.programme = programme; }
    // Define the setter for year of study.
    public void setYearOfStudy(int yearOfStudy) { this.yearOfStudy = yearOfStudy; }

    // Apply the Override annotation to the next declaration.
    @Override
    // Define the getter for dashboard welcome.
    public String getDashboardWelcome() {
        // Return "Welcome, " + getName() + "not Programme: " + programme + " (Year " + yearOfStudy + ")" to the caller.
        return "Welcome, " + getName() + "! Programme: " + programme + " (Year " + yearOfStudy + ")";
    }

    // Define the can view rooms method.
    public boolean canViewRooms() { return true; }
    // Define the can request booking method.
    public boolean canRequestBooking() { return true; }
}

