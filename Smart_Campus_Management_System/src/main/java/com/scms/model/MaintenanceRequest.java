// Declare that this file belongs to the com.scms.model package.
package com.scms.model;

// Import java.time.LocalDateTime so it can be used in this file.
import java.time.LocalDateTime;

// Declare the maintenance request class.
public class MaintenanceRequest {
    // Declare the id.
    private String id;
    // Declare the room id.
    private String roomId;
    // Declare the reporter id.
    private String reporterId;
    // Declare the description.
    private String description;
    // Declare the urgency.
    private Urgency urgency;
    // Declare the status.
    private RequestStatus status;
    // Declare the assignee id.
    private String assigneeId;
    // Declare the created at.
    private LocalDateTime createdAt;
    // Declare the updated at.
    private LocalDateTime updatedAt;

    // Execute: public MaintenanceRequest(String id, String roomId, String reporterId,
    public MaintenanceRequest(String id, String roomId, String reporterId,
                              // Execute: String description, Urgency urgency) {
                              String description, Urgency urgency) {
        // Set id using the computed value.
        this.id = id;
        // Set room id using the computed value.
        this.roomId = roomId;
        // Set reporter id using the computed value.
        this.reporterId = reporterId;
        // Set description using the computed value.
        this.description = description;
        // Set urgency using the computed value.
        this.urgency = urgency;
        // Set status using the computed value.
        this.status = RequestStatus.PENDING;
        // Set assignee id using the computed value.
        this.assigneeId = null;
        // Set created at using the computed value.
        this.createdAt = LocalDateTime.now();
        // Set updated at using the computed value.
        this.updatedAt = LocalDateTime.now();
    }

    // Define the getter for id.
    public String getId() { return id; }
    // Define the getter for room id.
    public String getRoomId() { return roomId; }
    // Define the getter for reporter id.
    public String getReporterId() { return reporterId; }
    // Define the getter for description.
    public String getDescription() { return description; }
    // Define the getter for urgency.
    public Urgency getUrgency() { return urgency; }
    // Define the getter for status.
    public RequestStatus getStatus() { return status; }
    // Define the getter for assignee id.
    public String getAssigneeId() { return assigneeId; }
    // Define the getter for created at.
    public LocalDateTime getCreatedAt() { return createdAt; }
    // Define the getter for updated at.
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // Define the setter for status.
    public void setStatus(RequestStatus status) {
        // Set status using the computed value.
        this.status = status;
        // Set updated at using the computed value.
        this.updatedAt = LocalDateTime.now();
    }

    // Define the setter for assignee id.
    public void setAssigneeId(String assigneeId) {
        // Set assignee id using the computed value.
        this.assigneeId = assigneeId;
        // Set updated at using the computed value.
        this.updatedAt = LocalDateTime.now();
    // End the current code block.
    }

    // Define the setter for description.
    public void setDescription(String description) { this.description = description; }
    // Define the setter for urgency.
    public void setUrgency(Urgency urgency) { this.urgency = urgency; }

    // Apply the Override annotation to the next declaration.
    @Override
    // Define the to string method.
    public String toString() {
        // Return "MR-" + id + ": " + description + " [" + status.getDisplayName() + "]" to the caller.
        return "MR-" + id + ": " + description + " [" + status.getDisplayName() + "]";
    }
}

