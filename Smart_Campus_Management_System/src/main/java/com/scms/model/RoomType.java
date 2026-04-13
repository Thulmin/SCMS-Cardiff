// Declare that this file belongs to the com.scms.model package.
package com.scms.model;

// Declare the room type enum.
public enum RoomType {
    // Lecture HALL.
    LECTURE_HALL("Lecture Hall"),
    // Provide the next argument for lecture HALL.
    COMPUTER_LAB("Computer Lab"),
    SEMINAR_ROOM("Seminar Room"),
    // Provide the next argument for lecture HALL.
    MEETING_ROOM("Meeting Room"),
    // Provide the remaining arguments needed to finish lecture HALL.
    WORKSHOP("Workshop");

    // Declare the display name.
    private final String displayName;

    // Room type.
    RoomType(String displayName) {
        // Set display name using the computed value.
        this.displayName = displayName;
    }

    // Define the getter for display name.
    public String getDisplayName() {
        // Return displayName to the caller.
        return displayName;
    }
}

