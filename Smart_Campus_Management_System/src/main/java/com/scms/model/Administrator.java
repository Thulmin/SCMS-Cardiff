// Declare that this file belongs to the com.scms.model package.
package com.scms.model;

// Declare the administrator class.
public class Administrator extends User {

    // Construct a new administrator instance.
    public Administrator(String id, String name, String email, String password) {
        // Super.
        super(id, name, email, password, UserRole.ADMINISTRATOR);
    }

    // Apply the Override annotation to the next declaration.
    @Override
    // Define the getter for dashboard welcome.
    public String getDashboardWelcome() {
        // Return "Welcome, Admin " + getName() + "not You have full system access." to the caller.
        return "Welcome, Admin " + getName() + "! You have full system access.";
    }

    // Define the can manage rooms method.
    public boolean canManageRooms() { return true; }
    // Define the can manage users method.
    public boolean canManageUsers() { return true; }
    // Define the can assign maintenance method.
    public boolean canAssignMaintenance() { return true; }
}

