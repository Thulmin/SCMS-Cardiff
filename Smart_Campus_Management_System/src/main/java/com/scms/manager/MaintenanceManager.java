// Declare that this file belongs to the com.scms.manager package.
package com.scms.manager;

// Import com.scms.exception.InvalidBookingException so it can be used in this file.
import com.scms.exception.InvalidBookingException;
import com.scms.exception.UnauthorizedActionException;
import com.scms.model.*;
import com.scms.pattern.NotificationService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Declare the maintenance manager class.
public class MaintenanceManager {
    // Declare the requests.
    private Map<String, MaintenanceRequest> requests;
    // Declare the notification service.
    private NotificationService notificationService;
    // Declare the request counter.
    private int requestCounter;

    // Construct a new maintenance manager instance.
    public MaintenanceManager(NotificationService notificationService) {
        // Initialize requests with a new value.
        this.requests = new HashMap<>();
        // Set notification service using the computed value.
        this.notificationService = notificationService;
        // Set request counter using the computed value.
        this.requestCounter = 0;
    }

    // Execute: public MaintenanceRequest createRequest(String roomId, String reporterId,
    public MaintenanceRequest createRequest(String roomId, String reporterId,
                                             // Execute: String description, Urgency urgency) {
                                             String description, Urgency urgency) {
        // Increment request counter.
        requestCounter++;
        // Store the request id value.
        String requestId = "MR" + String.format("%04d", requestCounter);
        // Start creating and assigning the request.
        MaintenanceRequest request = new MaintenanceRequest(
            // Provide the next value used to build request.
            requestId, roomId, reporterId, description, urgency
        // Provide the remaining values needed to finish request.
        );
        // Put on requests.
        requests.put(requestId, request);

        // Calculate and store the user manager.
        UserManager userManager = UserManager.getInstance();
        // Calculate and store the admins.
        List<User> admins = userManager.getUsersByRole(UserRole.ADMINISTRATOR);
        // Loop using User admin : admins.
        for (User admin : admins) {
            // Notify observer on notification service.
            notificationService.notifyObserver(admin,
                // Provide the next argument for notify observer.
                "New Maintenance Request: " + description +
                // Provide the remaining arguments needed to finish notify observer.
                " (Room: " + roomId + ", Urgency: " + urgency.getDisplayName() + ")");
        }

        // Return request to the caller.
        return request;
    }

    // Execute: public void assignRequest(String requestId, String assigneeId, User currentUser)
    public void assignRequest(String requestId, String assigneeId, User currentUser)
            // Execute: throws UnauthorizedActionException {
            throws UnauthorizedActionException {
        // Check whether currentUser.getRole() is not UserRole.ADMINISTRATOR.
        if (currentUser.getRole() != UserRole.ADMINISTRATOR) {
            // Throw a new exception to report this error condition.
            throw new UnauthorizedActionException(
                // Execute: "Only administrators can assign maintenance requests.");
                "Only administrators can assign maintenance requests.");
        }

        // Calculate and store the request.
        MaintenanceRequest request = requests.get(requestId);
        // Execute: if (request == null) return;
        if (request == null) return;

        // Set assignee id on request.
        request.setAssigneeId(assigneeId);
        // Set status on request.
        request.setStatus(RequestStatus.ASSIGNED);

        // Calculate and store the user manager.
        UserManager userManager = UserManager.getInstance();
        // Calculate and store the reporter.
        User reporter = userManager.getUserById(request.getReporterId());
        // Check whether reporter is not null.
        if (reporter != null) {
            // Notify observer on notification service.
            notificationService.notifyObserver(reporter,
                // Provide the next argument for notify observer.
                "Maintenance Update: Your request MR-" + requestId +
                // Provide the remaining arguments needed to finish notify observer.
                " has been assigned and is being addressed.");
        }

        // Calculate and store the assignee.
        User assignee = userManager.getUserById(assigneeId);
        // Check whether assignee is not null.
        if (assignee != null) {
            // Notify observer on notification service.
            notificationService.notifyObserver(assignee,
                // Provide the next argument for notify observer.
                "Maintenance Assignment: You have been assigned to request MR-" +
                // Provide the remaining arguments needed to finish notify observer.
                requestId + " - " + request.getDescription());
        }
    }

    // Execute: public void completeRequest(String requestId, User currentUser)
    public void completeRequest(String requestId, User currentUser)
            // Execute: throws UnauthorizedActionException {
            throws UnauthorizedActionException {
        // Check whether currentUser.getRole() is not UserRole.ADMINISTRATOR.
        if (currentUser.getRole() != UserRole.ADMINISTRATOR) {
            // Throw a new exception to report this error condition.
            throw new UnauthorizedActionException(
                // Execute: "Only administrators can complete maintenance requests.");
                "Only administrators can complete maintenance requests.");
        }

        // Calculate and store the request.
        MaintenanceRequest request = requests.get(requestId);
        // Execute: if (request == null) return;
        if (request == null) return;

        // Set status on request.
        request.setStatus(RequestStatus.COMPLETED);

        // Calculate and store the user manager.
        UserManager userManager = UserManager.getInstance();
        // Calculate and store the reporter.
        User reporter = userManager.getUserById(request.getReporterId());
        // Check whether reporter is not null.
        if (reporter != null) {
            // Notify observer on notification service.
            notificationService.notifyObserver(reporter,
                // Provide the next argument for notify observer.
                "Maintenance Completed: Your request MR-" + requestId +
                // Provide the remaining arguments needed to finish notify observer.
                " has been resolved.");
        }
    }

    // Define the getter for request by id.
    public MaintenanceRequest getRequestById(String id) {
        // Return requests.get(id) to the caller.
        return requests.get(id);
    }

    // Define the getter for all requests.
    public List<MaintenanceRequest> getAllRequests() {
        // Return new ArrayList<>(requests.values()) to the caller.
        return new ArrayList<>(requests.values());
    }

    // Define the getter for requests by status.
    public List<MaintenanceRequest> getRequestsByStatus(RequestStatus status) {
        // Create and store the result.
        List<MaintenanceRequest> result = new ArrayList<>();
        // Loop using MaintenanceRequest request : requests.values().
        for (MaintenanceRequest request : requests.values()) {
            // Check whether request.getStatus() is status.
            if (request.getStatus() == status) {
                // Add on result.
                result.add(request);
            }
        }
        // Return result to the caller.
        return result;
    }

    // Define the getter for requests by room.
    public List<MaintenanceRequest> getRequestsByRoom(String roomId) {
        // Create and store the result.
        List<MaintenanceRequest> result = new ArrayList<>();
        // Loop using MaintenanceRequest request : requests.values().
        for (MaintenanceRequest request : requests.values()) {
            // Check whether request.getRoomId().equals(roomId).
            if (request.getRoomId().equals(roomId)) {
                // Add on result.
                result.add(request);
            }
        }
        // Return result to the caller.
        return result;
    }

    // Define the getter for requests by reporter.
    public List<MaintenanceRequest> getRequestsByReporter(String reporterId) {
        // Create and store the result.
        List<MaintenanceRequest> result = new ArrayList<>();
        // Loop using MaintenanceRequest request : requests.values().
        for (MaintenanceRequest request : requests.values()) {
            // Check whether request.getReporterId().equals(reporterId).
            if (request.getReporterId().equals(reporterId)) {
                // Add on result.
                result.add(request);
            }
        }
        // Return result to the caller.
        return result;
    }

    // Define the getter for pending count.
    public int getPendingCount() {
        // Return getRequestsByStatus(RequestStatus.PENDING).size() to the caller.
        return getRequestsByStatus(RequestStatus.PENDING).size();
    }

    // Define the getter for assigned count.
    public int getAssignedCount() {
        // Return getRequestsByStatus(RequestStatus.ASSIGNED).size() to the caller.
        return getRequestsByStatus(RequestStatus.ASSIGNED).size();
    }

    // Define the getter for completed count.
    public int getCompletedCount() {
        // Return getRequestsByStatus(RequestStatus.COMPLETED).size() to the caller.
        return getRequestsByStatus(RequestStatus.COMPLETED).size();
    }

    // Define the getter for urgency breakdown.
    public Map<Urgency, Integer> getUrgencyBreakdown() {
        // Create and store the breakdown.
        Map<Urgency, Integer> breakdown = new HashMap<>();
        // Loop using MaintenanceRequest request : requests.values().
        for (MaintenanceRequest request : requests.values()) {
            // Merge on breakdown.
            breakdown.merge(request.getUrgency(), 1, Integer::sum);
        }
        // Return breakdown to the caller.
        return breakdown;
    }
}

