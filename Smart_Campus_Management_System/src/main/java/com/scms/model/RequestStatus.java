// Declare that this file belongs to the com.scms.model package.
package com.scms.model;

// Declare the request status enum.
public enum RequestStatus {
    // Pending.
    PENDING("Pending"),
    // Provide the next argument for pending.
    ASSIGNED("Assigned"),
    // Provide the remaining arguments needed to finish pending.
    COMPLETED("Completed");

    // Declare the display name.
    private final String displayName;

    // Request status.
    RequestStatus(String displayName) {
        // Set display name using the computed value.
        this.displayName = displayName;
    // End the current code block.
    }

    // Define the getter for display name.
    public String getDisplayName() {
        // Return displayName to the caller.
        return displayName;
    }
}

