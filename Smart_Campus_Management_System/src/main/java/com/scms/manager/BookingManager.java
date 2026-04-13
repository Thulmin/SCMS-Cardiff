// Declare that this file belongs to the com.scms.manager package.
package com.scms.manager;

// Import com.scms.exception.InvalidBookingException so it can be used in this file.
import com.scms.exception.InvalidBookingException;
import com.scms.model.*;
import com.scms.pattern.NotificationService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Declare the booking manager class.
public class BookingManager {
    // Declare the bookings.
    private Map<String, Booking> bookings;
    // Declare the room manager.
    private RoomManager roomManager;
    // Declare the notification service.
    private NotificationService notificationService;
    // Declare the booking counter.
    private int bookingCounter;

    // Construct a new booking manager instance.
    public BookingManager(RoomManager roomManager, NotificationService notificationService) {
        // Initialize bookings with a new value.
        this.bookings = new HashMap<>();
        // Set room manager using the computed value.
        this.roomManager = roomManager;
        // Set notification service using the computed value.
        this.notificationService = notificationService;
        // Set booking counter using the computed value.
        this.bookingCounter = 0;
    }

    // Execute: public Booking createBooking(String roomId, String userId, LocalDate date,
    public Booking createBooking(String roomId, String userId, LocalDate date,
                                  // Execute: LocalTime startTime, LocalTime endTime, String purpose)
                                  LocalTime startTime, LocalTime endTime, String purpose)
            // Execute: throws InvalidBookingException {
            throws InvalidBookingException {

        // Calculate and store the room.
        Room room = roomManager.getRoomById(roomId);
        // Check whether room is null.
        if (room == null) {
            // Throw a new exception to report this error condition.
            throw new InvalidBookingException("Room with ID " + roomId + " does not exist.");
        }
        // Check whether not room.isActive().
        if (!room.isActive()) {
            // Throw a new exception to report this error condition.
            throw new InvalidBookingException("Room " + room.getName() + " is currently deactivated.");
        }
        // Check whether date.isBefore(LocalDate.now()).
        if (date.isBefore(LocalDate.now())) {
            // Throw a new exception to report this error condition.
            throw new InvalidBookingException("Cannot book a room for a past date.");
        }
        // Check whether not endTime.isAfter(startTime).
        if (!endTime.isAfter(startTime)) {
            // Throw a new exception to report this error condition.
            throw new InvalidBookingException("End time must be after start time.");
        }

        // Increment booking counter.
        bookingCounter++;
        // Store the booking id value.
        String bookingId = "BK" + String.format("%04d", bookingCounter);
        // Create and store the new booking.
        Booking newBooking = new Booking(bookingId, roomId, userId, date, startTime, endTime, purpose);

        // Loop using Booking existing : bookings.values().
        for (Booking existing : bookings.values()) {
            // Check whether existing.overlaps(newBooking).
            if (existing.overlaps(newBooking)) {
                // Throw a new exception to report this error condition.
                throw new InvalidBookingException(
                    // Execute: "Time slot conflict: Room " + room.getName() + " is already booked on " +
                    "Time slot conflict: Room " + room.getName() + " is already booked on " +
                    // Execute: date + " from " + existing.getStartTime() + " to " + existing.getEndTime() + "."
                    date + " from " + existing.getStartTime() + " to " + existing.getEndTime() + "."
                // Execute: );
                );
            }
        }

        // Put on bookings.
        bookings.put(bookingId, newBooking);

        // Calculate and store the user manager.
        UserManager userManager = UserManager.getInstance();
        // Calculate and store the user.
        User user = userManager.getUserById(userId);
        // Check whether user is not null.
        if (user != null) {
            // Notify observer on notification service.
            notificationService.notifyObserver(user,
                // Provide the next argument for notify observer.
                "Booking Confirmed: Room " + room.getName() + " on " + date +
                // Provide the remaining arguments needed to finish notify observer.
                " from " + startTime + " to " + endTime + ".");
        }

        // Return newBooking to the caller.
        return newBooking;
    }

    // Define the cancel booking method.
    public void cancelBooking(String bookingId, String userId) throws InvalidBookingException {
        // Calculate and store the booking.
        Booking booking = bookings.get(bookingId);
        // Check whether booking is null.
        if (booking == null) {
            // Throw a new exception to report this error condition.
            throw new InvalidBookingException("Booking " + bookingId + " not found.");
        }
        // Check whether booking.getStatus() is BookingStatus.CANCELLED.
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            // Throw a new exception to report this error condition.
            throw new InvalidBookingException("Booking " + bookingId + " is already cancelled.");
        }

        // Set status on booking.
        booking.setStatus(BookingStatus.CANCELLED);

        // Calculate and store the user manager.
        UserManager userManager = UserManager.getInstance();
        // Calculate and store the user.
        User user = userManager.getUserById(booking.getUserId());
        // Calculate and store the room.
        Room room = roomManager.getRoomById(booking.getRoomId());
        // Check whether user is not null and room is not null.
        if (user != null && room != null) {
            // Notify observer on notification service.
            notificationService.notifyObserver(user,
                // Provide the next argument for notify observer.
                "Booking Cancelled: Your booking for " + room.getName() +
                // Provide the remaining arguments needed to finish notify observer.
                " on " + booking.getDate() + " has been cancelled.");
        }
    }

    // Define the getter for booking by id.
    public Booking getBookingById(String id) {
        // Return bookings.get(id) to the caller.
        return bookings.get(id);
    }

    // Define the getter for all bookings.
    public List<Booking> getAllBookings() {
        // Return new ArrayList<>(bookings.values()) to the caller.
        return new ArrayList<>(bookings.values());
    }

    // Define the getter for bookings by user.
    public List<Booking> getBookingsByUser(String userId) {
        // Create and store the result.
        List<Booking> result = new ArrayList<>();
        // Loop using Booking booking : bookings.values().
        for (Booking booking : bookings.values()) {
            // Check whether booking.getUserId().equals(userId).
            if (booking.getUserId().equals(userId)) {
                // Add on result.
                result.add(booking);
            }
        }
        // Return result to the caller.
        return result;
    }

    // Define the getter for bookings by room.
    public List<Booking> getBookingsByRoom(String roomId) {
        // Create and store the result.
        List<Booking> result = new ArrayList<>();
        // Loop using Booking booking : bookings.values().
        for (Booking booking : bookings.values()) {
            // Check whether booking.getRoomId().equals(roomId) and booking.getStatus() is not BookingStatus.CANCELLED.
            if (booking.getRoomId().equals(roomId) && booking.getStatus() != BookingStatus.CANCELLED) {
                // Add on result.
                result.add(booking);
            }
        // End the current code block.
        }
        // Return result to the caller.
        return result;
    }

    // Define the getter for bookings by date.
    public List<Booking> getBookingsByDate(LocalDate date) {
        // Create and store the result.
        List<Booking> result = new ArrayList<>();
        // Loop using Booking booking : bookings.values().
        for (Booking booking : bookings.values()) {
            // Check whether booking.getDate().equals(date) and booking.getStatus() is not BookingStatus.CANCELLED.
            if (booking.getDate().equals(date) && booking.getStatus() != BookingStatus.CANCELLED) {
                // Add on result.
                result.add(booking);
            }
        }
        // Return result to the caller.
        return result;
    }

    // Define the getter for active booking count.
    public int getActiveBookingCount() {
        // Store the count value.
        int count = 0;
        // Loop using Booking booking : bookings.values().
        for (Booking booking : bookings.values()) {
            // Check whether booking.getStatus() is not BookingStatus.CANCELLED.
            if (booking.getStatus() != BookingStatus.CANCELLED) {
                // Increment count.
                count++;
            }
        }
        // Return count to the caller.
        return count;
    }

    // Define the getter for total booking count.
    public int getTotalBookingCount() {
        // Return bookings.size() to the caller.
        return bookings.size();
    }

    // Define the getter for most booked rooms.
    public Map<String, Integer> getMostBookedRooms() {
        // Create and store the room booking count.
        Map<String, Integer> roomBookingCount = new HashMap<>();
        // Loop using Booking booking : bookings.values().
        for (Booking booking : bookings.values()) {
            // Check whether booking.getStatus() is not BookingStatus.CANCELLED.
            if (booking.getStatus() != BookingStatus.CANCELLED) {
                // Merge on room booking count.
                roomBookingCount.merge(booking.getRoomId(), 1, Integer::sum);
            // End the current code block.
            }
        }
        // Return roomBookingCount to the caller.
        return roomBookingCount;
    }
}

