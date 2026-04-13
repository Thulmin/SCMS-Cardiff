// Declare that this file belongs to the com.scms.model package.
package com.scms.model;

// Declare the staff member class.
public class StaffMember extends User {

    // Declare the department.
    private String department;

    // Construct a new staff member instance.
    public StaffMember(String id, String name, String email, String password, String department) {
        // Super.
        super(id, name, email, password, UserRole.STAFF);
        // Set department using the computed value.
        this.department = department;
    }

    // Define the getter for department.
    public String getDepartment() { return department; }
    // Define the setter for department.
    public void setDepartment(String department) { this.department = department; }

    // Apply the Override annotation to the next declaration.
    @Override
    // Define the getter for dashboard welcome.
    public String getDashboardWelcome() {
        // Return "Welcome, " + getName() + "not Department: " + department to the caller.
        return "Welcome, " + getName() + "! Department: " + department;
    }

    // Define the can book rooms method.
    public boolean canBookRooms() { return true; }
    // Define the can submit maintenance method.
    public boolean canSubmitMaintenance() { return true; }
}

