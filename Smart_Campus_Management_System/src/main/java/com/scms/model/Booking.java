// Declare that this file belongs to the com.scms.model package.
package com.scms.model;

// Import java.time.LocalDate so it can be used in this file.
import java.time.LocalDate;
import java.time.LocalTime;

// Declare the booking class.
public class Booking {
    // Declare the id.
    private String id;
    // Declare the room id.
    private String roomId;
    // Declare the user id.
    private String userId;
    // Declare the date.
    private LocalDate date;
    // Declare the start time.
    private LocalTime startTime;
    // Declare the end time.
    private LocalTime endTime;
    // Declare the status.
    private BookingStatus status;
    // Declare the purpose.
    private String purpose;

    // Execute: public Booking(String id, String roomId, String userId, LocalDate date,
    public Booking(String id, String roomId, String userId, LocalDate date,
                   // Execute: LocalTime startTime, LocalTime endTime, String purpose) {
                   LocalTime startTime, LocalTime endTime, String purpose) {
        // Set id using the computed value.
        this.id = id;
        // Set room id using the computed value.
        this.roomId = roomId;
        // Set user id using the computed value.
        this.userId = userId;
        // Set date using the computed value.
        this.date = date;
        // Set start time using the computed value.
        this.startTime = startTime;
        // Set end time using the computed value.
        this.endTime = endTime;
        // Set status using the computed value.
        this.status = BookingStatus.CONFIRMED;
        // Set purpose using the computed value.
        this.purpose = purpose;
    }

    // Define the getter for id.
    public String getId() { return id; }
    // Define the getter for room id.
    public String getRoomId() { return roomId; }
    // Define the getter for user id.
    public String getUserId() { return userId; }
    // Define the getter for date.
    public LocalDate getDate() { return date; }
    // Define the getter for start time.
    public LocalTime getStartTime() { return startTime; }
    // Define the getter for end time.
    public LocalTime getEndTime() { return endTime; }
    // Define the getter for status.
    public BookingStatus getStatus() { return status; }
    // Define the getter for purpose.
    public String getPurpose() { return purpose; }

    // Define the setter for status.
    public void setStatus(BookingStatus status) { this.status = status; }
    // Define the setter for purpose.
    public void setPurpose(String purpose) { this.purpose = purpose; }

    // Define the overlaps method.
    public boolean overlaps(Booking other) {
        // Check whether not roomId.equals(other.roomId) or not date.equals(other.date).
        if (!this.roomId.equals(other.roomId) || !this.date.equals(other.date)) {
            // Return false to the caller.
            return false;
        }
        // Check whether status is BookingStatus.CANCELLED or other.status is BookingStatus.CANCELLED.
        if (this.status == BookingStatus.CANCELLED || other.status == BookingStatus.CANCELLED) {
            // Return false to the caller.
            return false;
        }
        // Return startTime.isBefore(other.endTime) and other.startTime.isBefore(endTime) to the caller.
        return this.startTime.isBefore(other.endTime) && other.startTime.isBefore(this.endTime);
    }

    // Define the getter for time slot.
    public String getTimeSlot() {
        return startTime.toString() + " - " + endTime.toString();
    }

    // Apply the Override annotation to the next declaration.
    @Override
    // Define the to string method.
    public String toString() {
        // Return "Booking " + id + ": Room " + roomId + " on " + date + " " + getTimeSlot() to the caller.
        return "Booking " + id + ": Room " + roomId + " on " + date + " " + getTimeSlot();
    }
}

