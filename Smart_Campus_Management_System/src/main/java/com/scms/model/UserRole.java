// Declare that this file belongs to the com.scms.model package.
package com.scms.model;

// Declare the user role enum.
public enum UserRole {
    // Administrator.
    ADMINISTRATOR("Administrator"),
    // Provide the next argument for administrator.
    STAFF("Staff Member"),
    // Provide the remaining arguments needed to finish administrator.
    STUDENT("Student");

    // Declare the display name.
    private final String displayName;

    // User role.
    UserRole(String displayName) {
        // Set display name using the computed value.
        this.displayName = displayName;
    }

    // Define the getter for display name.
    public String getDisplayName() {
        // Return displayName to the caller.
        return displayName;
    }
}

