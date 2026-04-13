// Declare that this file belongs to the com.scms.model package.
package com.scms.model;

// Declare the booking status enum.
public enum BookingStatus {
    // Confirmed.
    CONFIRMED("Confirmed"),
    // Provide the next argument for confirmed.
    CANCELLED("Cancelled"),
    // Provide the remaining arguments needed to finish confirmed.
    PENDING("Pending");

    // Declare the display name.
    private final String displayName;

    // Booking status.
    BookingStatus(String displayName) {
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

