// Declare that this file belongs to the com.scms.model package.
package com.scms.model;

// Declare the urgency enum.
public enum Urgency {
    // LOW.
    LOW("Low"),
    // Provide the next argument for LOW.
    MEDIUM("Medium"),
    HIGH("High"),
    // Provide the remaining arguments needed to finish LOW.
    CRITICAL("Critical");

    // Declare the display name.
    private final String displayName;

    // Urgency.
    Urgency(String displayName) {
        // Set display name using the computed value.
        this.displayName = displayName;
    }

    // Define the getter for display name.
    public String getDisplayName() {
        // Return displayName to the caller.
        return displayName;
    }
}

