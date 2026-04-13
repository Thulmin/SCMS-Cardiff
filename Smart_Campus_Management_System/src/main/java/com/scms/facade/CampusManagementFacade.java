// Declare that this file belongs to the com.scms.facade package.
package com.scms.facade;

// Import com.scms.exception.* so it can be used in this file.
import com.scms.exception.*;
import com.scms.manager.*;
import com.scms.model.*;
import com.scms.pattern.NotificationService;
import com.scms.pattern.RoomFactory;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

// Declare the campus management facade class.
public class CampusManagementFacade {
    // Declare the user manager.
    private UserManager userManager;
    // Declare the room manager.
    private RoomManager roomManager;
    private BookingManager bookingManager;
    // Declare the maintenance manager.
    private MaintenanceManager maintenanceManager;
    // Declare the notification service.
    private NotificationService notificationService;

    // Construct a new campus management facade instance.
    public CampusManagementFacade() {
        // Set user manager using the computed value.
        this.userManager = UserManager.getInstance();
        // Initialize notification service with a new value.
        this.notificationService = new NotificationService();
        // Initialize room manager with a new value.
        this.roomManager = new RoomManager();
        // Initialize booking manager with a new value.
        this.bookingManager = new BookingManager(roomManager, notificationService);
        // Initialize maintenance manager with a new value.
        this.maintenanceManager = new MaintenanceManager(notificationService);
    }

    // --- Authentication ---
    // Define the login method.
    public User login(String email, String password) {
        // Calculate and store the user.
        User user = userManager.authenticate(email, password);
        // Check whether user is not null.
        if (user != null) {
            // Subscribe on notification service.
            notificationService.subscribe(user);
        }
        // Return user to the caller.
        return user;
    }

    // Define the logout method.
    public void logout() {
        // Calculate and store the current.
        User current = userManager.getCurrentUser();
        // Check whether current is not null.
        if (current != null) {
            // Unsubscribe on notification service.
            notificationService.unsubscribe(current);
        }
        // Log out on user manager.
        userManager.logout();
    }

    // Define the getter for current user.
    public User getCurrentUser() {
        // Return userManager.getCurrentUser() to the caller.
        return userManager.getCurrentUser();
    }

    // --- User Management ---
    // Define the add user method.
    public void addUser(User user) throws DuplicateDataException {
        // Add user on user manager.
        userManager.addUser(user);
    }

    // Define the getter for all users.
    public List<User> getAllUsers() { return userManager.getAllUsers(); }
    // Define the getter for user by id.
    public User getUserById(String id) { return userManager.getUserById(id); }

    // --- Room Management ---
    // Define the add room method.
    public Room addRoom(String id, String name, int capacity, RoomType type) throws DuplicateDataException {
        // Calculate and store the room.
        Room room = RoomFactory.createRoom(id, name, capacity, type);
        // Add room on room manager.
        roomManager.addRoom(room);
        // Return room to the caller.
        return room;
    }

    // Define the update room method.
    public void updateRoom(String id, String name, int capacity, RoomType type) {
        // Update room on room manager.
        roomManager.updateRoom(id, name, capacity, type);
    }

    // Define the deactivate room method.
    public void deactivateRoom(String id) {
        // Deactivate room on room manager.
        roomManager.deactivateRoom(id);
    }

    // Define the activate room method.
    public void activateRoom(String id) {
        // Activate room on room manager.
        roomManager.activateRoom(id);
    }

    // Define the getter for all rooms.
    public List<Room> getAllRooms() { return roomManager.getAllRooms(); }
    // Define the getter for active rooms.
    public List<Room> getActiveRooms() { return roomManager.getActiveRooms(); }
    // Define the getter for room by id.
    public Room getRoomById(String id) { return roomManager.getRoomById(id); }
    // Define the getter for rooms by type.
    public List<Room> getRoomsByType(RoomType type) { return roomManager.getRoomsByType(type); }

    // --- Booking Management ---
    // Execute: public Booking bookRoom(String roomId, String userId, LocalDate date,
    public Booking bookRoom(String roomId, String userId, LocalDate date,
                            // Execute: LocalTime startTime, LocalTime endTime, String purpose)
                            LocalTime startTime, LocalTime endTime, String purpose)
            // Execute: throws InvalidBookingException {
            throws InvalidBookingException {
        // Return bookingManager.createBooking(roomId, userId, date, startTime, endTime, purpose) to the caller.
        return bookingManager.createBooking(roomId, userId, date, startTime, endTime, purpose);
    }

    // Define the cancel booking method.
    public void cancelBooking(String bookingId, String userId) throws InvalidBookingException {
        // Cancel booking on booking manager.
        bookingManager.cancelBooking(bookingId, userId);
    }

    // Define the getter for all bookings.
    public List<Booking> getAllBookings() { return bookingManager.getAllBookings(); }
    // Define the getter for user bookings.
    public List<Booking> getUserBookings(String userId) { return bookingManager.getBookingsByUser(userId); }
    // Define the getter for room bookings.
    public List<Booking> getRoomBookings(String roomId) { return bookingManager.getBookingsByRoom(roomId); }

    // --- Maintenance Management ---
    // Execute: public MaintenanceRequest reportMaintenance(String roomId, String reporterId,
    public MaintenanceRequest reportMaintenance(String roomId, String reporterId,
                                                 // Execute: String description, Urgency urgency) {
                                                 String description, Urgency urgency) {
        // Return maintenanceManager.createRequest(roomId, reporterId, description, urgency) to the caller.
        return maintenanceManager.createRequest(roomId, reporterId, description, urgency);
    }

    // Execute: public void assignMaintenance(String requestId, String assigneeId)
    public void assignMaintenance(String requestId, String assigneeId)
            // Execute: throws UnauthorizedActionException {
            throws UnauthorizedActionException {
        // Calculate and store the current.
        User current = userManager.getCurrentUser();
        // Check whether current is null.
        if (current == null) {
            // Throw a new exception to report this error condition.
            throw new UnauthorizedActionException("No user is currently logged in.");
        }
        // Assign request on maintenance manager.
        maintenanceManager.assignRequest(requestId, assigneeId, current);
    }

    // Execute: public void completeMaintenance(String requestId)
    public void completeMaintenance(String requestId)
            // Execute: throws UnauthorizedActionException {
            throws UnauthorizedActionException {
        // Calculate and store the current.
        User current = userManager.getCurrentUser();
        // Check whether current is null.
        if (current == null) {
            // Throw a new exception to report this error condition.
            throw new UnauthorizedActionException("No user is currently logged in.");
        }
        // Complete request on maintenance manager.
        maintenanceManager.completeRequest(requestId, current);
    }

    // Define the getter for all maintenance requests.
    public List<MaintenanceRequest> getAllMaintenanceRequests() {
        // Return maintenanceManager.getAllRequests() to the caller.
        return maintenanceManager.getAllRequests();
    }

    // Define the getter for maintenance by status.
    public List<MaintenanceRequest> getMaintenanceByStatus(RequestStatus status) {
        // Return maintenanceManager.getRequestsByStatus(status) to the caller.
        return maintenanceManager.getRequestsByStatus(status);
    }

    // Define the getter for user maintenance requests.
    public List<MaintenanceRequest> getUserMaintenanceRequests(String userId) {
        // Return maintenanceManager.getRequestsByReporter(userId) to the caller.
        return maintenanceManager.getRequestsByReporter(userId);
    }

    // --- Analytics ---
    // Define the getter for total rooms.
    public int getTotalRooms() { return roomManager.getRoomCount(); }
    // Define the getter for active room count.
    public int getActiveRoomCount() { return roomManager.getActiveRoomCount(); }
    // Define the getter for total bookings.
    public int getTotalBookings() { return bookingManager.getTotalBookingCount(); }
    // Define the getter for active bookings.
    public int getActiveBookings() { return bookingManager.getActiveBookingCount(); }
    // Define the getter for pending maintenance.
    public int getPendingMaintenance() { return maintenanceManager.getPendingCount(); }
    // Define the getter for assigned maintenance.
    public int getAssignedMaintenance() { return maintenanceManager.getAssignedCount(); }
    // Define the getter for completed maintenance.
    public int getCompletedMaintenance() { return maintenanceManager.getCompletedCount(); }
    // Define the getter for total users.
    public int getTotalUsers() { return userManager.getUserCount(); }
    // Define the getter for most booked rooms.
    public Map<String, Integer> getMostBookedRooms() { return bookingManager.getMostBookedRooms(); }
    // Define the getter for maintenance urgency breakdown.
    public Map<Urgency, Integer> getMaintenanceUrgencyBreakdown() {
        // Return maintenanceManager.getUrgencyBreakdown() to the caller.
        return maintenanceManager.getUrgencyBreakdown();
    }

    // --- Notification ---
    // Define the getter for notification service.
    public NotificationService getNotificationService() { return notificationService; }

    // --- Getters for Managers ---
    // Define the getter for room manager.
    public RoomManager getRoomManager() { return roomManager; }
    // Define the getter for booking manager.
    public BookingManager getBookingManager() { return bookingManager; }
    // Define the getter for maintenance manager.
    public MaintenanceManager getMaintenanceManager() { return maintenanceManager; }
    // Define the getter for user manager.
    public UserManager getUserManager() { return userManager; }
}

