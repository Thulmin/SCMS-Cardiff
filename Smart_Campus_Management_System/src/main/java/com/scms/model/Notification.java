// Declare that this file belongs to the com.scms.model package.
package com.scms.model;

// Import java.time.LocalDateTime so it can be used in this file.
import java.time.LocalDateTime;
// Import java.time.format.DateTimeFormatter so it can be used in this file.
import java.time.format.DateTimeFormatter;

// Declare the notification class.
public class Notification {
    // Declare the id.
    private String id;
    // Declare the message.
    private String message;
    // Declare the recipient id.
    private String recipientId;
    // Declare the timestamp.
    private LocalDateTime timestamp;
    // Declare the read.
    private boolean read;

    // Construct a new notification instance.
    public Notification(String id, String message, String recipientId) {
        // Set id using the computed value.
        this.id = id;
        // Set message using the computed value.
        this.message = message;
        // Set recipient id using the computed value.
        this.recipientId = recipientId;
        // Set timestamp using the computed value.
        this.timestamp = LocalDateTime.now();
        // Set read using the computed value.
        this.read = false;
    }

    // Define the getter for id.
    public String getId() { return id; }
    // Define the getter for message.
    public String getMessage() { return message; }
    // Define the getter for recipient id.
    public String getRecipientId() { return recipientId; }
    // Define the getter for timestamp.
    public LocalDateTime getTimestamp() { return timestamp; }
    // Define the check for whether read.
    public boolean isRead() { return read; }

    // Define the mark as read method.
    public void markAsRead() { this.read = true; }

    // Define the getter for formatted timestamp.
    public String getFormattedTimestamp() {
        // Return timestamp.format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")) to the caller.
        return timestamp.format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm"));
    }

    // Apply the Override annotation to the next declaration.
    @Override
    // Define the to string method.
    public String toString() {
        // Return "[" + getFormattedTimestamp() + "] " + message to the caller.
        return "[" + getFormattedTimestamp() + "] " + message;
    }
}

