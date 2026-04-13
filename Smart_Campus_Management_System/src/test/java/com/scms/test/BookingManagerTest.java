// Declare that this file belongs to the com.scms.test package.
package com.scms.test;

// Import com.scms.exception.DuplicateDataException so it can be used in this file.
import com.scms.exception.DuplicateDataException;
import com.scms.exception.InvalidBookingException;
import com.scms.manager.BookingManager;
import com.scms.manager.RoomManager;
import com.scms.manager.UserManager;
import com.scms.model.*;
import com.scms.pattern.NotificationService;
import com.scms.pattern.RoomFactory;
import org.junit.jupiter.api.*;
import java.time.LocalDate;
import java.time.LocalTime;

// Import static members from org.junit.jupiter.api.Assertions.* for direct use.
import static org.junit.jupiter.api.Assertions.*;

// Apply the TestMethodOrder annotation to the next declaration.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// Declare the booking manager test class.
public class BookingManagerTest {
    // Declare the booking manager.
    private BookingManager bookingManager;
    // Declare the room manager.
    private RoomManager roomManager;
    // Declare the notification service.
    private NotificationService notificationService;

    // Apply the BeforeEach annotation to the next declaration.
    @BeforeEach
    // Define the setter for up.
    void setUp() throws DuplicateDataException {
        // Reset the singleton instance on user manager.
        UserManager.resetInstance();
        // Calculate and store the user manager.
        UserManager userManager = UserManager.getInstance();

        // Initialize room manager with a new value.
        roomManager = new RoomManager();
        // Initialize notification service with a new value.
        notificationService = new NotificationService();
        // Initialize booking manager with a new value.
        bookingManager = new BookingManager(roomManager, notificationService);

        // Add test data
        // Calculate and store the room1.
        Room room1 = RoomFactory.createLectureHall("R001", "Lecture Hall A", 100);
        // Calculate and store the room2.
        Room room2 = RoomFactory.createComputerLab("R002", "Computer Lab 1", 40);
        // Add room on room manager.
        roomManager.addRoom(room1);
        // Add room on room manager.
        roomManager.addRoom(room2);

        // Create and store the admin.
        Administrator admin = new Administrator("U001", "Admin", "admin@test.com", "pass");
        // Create and store the staff.
        StaffMember staff = new StaffMember("U002", "Staff", "staff@test.com", "pass", "CS");
        // Add user on user manager.
        userManager.addUser(admin);
        userManager.addUser(staff);
    }

    // Apply the Test annotation to the next declaration.
    @Test
    // Apply the Order annotation to the next declaration.
    @Order(1)
    // Apply the DisplayName annotation to the next declaration.
    @DisplayName("Successful room booking creates booking with CONFIRMED status")
    // Define the test successful booking test case.
    void testSuccessfulBooking() throws InvalidBookingException {
        // Calculate and store the tomorrow.
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        // Execute: Booking booking = bookingManager.createBooking(
        Booking booking = bookingManager.createBooking(
            // Execute: "R001", "U001", tomorrow,
            "R001", "U001", tomorrow,
            // Of on local time.
            LocalTime.of(9, 0), LocalTime.of(11, 0), "Test Lecture"
        // Provide the remaining arguments needed to finish of.
        );

        // Assert that the value is not null.
        assertNotNull(booking);
        // Assert that the actual value matches the expected value.
        assertEquals(BookingStatus.CONFIRMED, booking.getStatus());
        assertEquals("R001", booking.getRoomId());
        assertEquals("U001", booking.getUserId());
        assertEquals("Test Lecture", booking.getPurpose());
    }

    // Apply the Test annotation to the next declaration.
    @Test
    // Apply the Order annotation to the next declaration.
    @Order(2)
    // Apply the DisplayName annotation to the next declaration.
    @DisplayName("Double booking of the same room and time throws InvalidBookingException")
    // Define the test double booking prevention test case.
    void testDoubleBookingPrevention() throws InvalidBookingException {
        // Calculate and store the tomorrow.
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        // Create booking on booking manager.
        bookingManager.createBooking(
            // Provide the next argument for create booking.
            "R001", "U001", tomorrow,
            // Provide the next argument for create booking.
            LocalTime.of(9, 0), LocalTime.of(11, 0), "First Booking"
        // Provide the remaining arguments needed to finish create booking.
        );

        // Assert that the enclosed call throws the expected exception.
        assertThrows(InvalidBookingException.class, () -> {
            // Create booking on booking manager.
            bookingManager.createBooking(
                // Provide the next argument for create booking.
                "R001", "U002", tomorrow,
                // Provide the next argument for create booking.
                LocalTime.of(10, 0), LocalTime.of(12, 0), "Overlapping Booking"
            // Provide the remaining arguments needed to finish create booking.
            );
        });
    }

    // Apply the Test annotation to the next declaration.
    @Test
    @Order(3)
    // Apply the DisplayName annotation to the next declaration.
    @DisplayName("Booking a non-existent room throws InvalidBookingException")
    // Define the test booking non existent room test case.
    void testBookingNonExistentRoom() {
        // Calculate and store the tomorrow.
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        // Assert that the enclosed call throws the expected exception.
        assertThrows(InvalidBookingException.class, () -> {
            // Create booking on booking manager.
            bookingManager.createBooking(
                // Provide the next argument for create booking.
                "R999", "U001", tomorrow,
                // Provide the next argument for create booking.
                LocalTime.of(9, 0), LocalTime.of(11, 0), "Invalid Room"
            // Provide the remaining arguments needed to finish create booking.
            );
        });
    }

    // Apply the Test annotation to the next declaration.
    @Test
    // Apply the Order annotation to the next declaration.
    @Order(4)
    @DisplayName("Booking with end time before start time throws InvalidBookingException")
    // Define the test invalid time range test case.
    void testInvalidTimeRange() {
        // Calculate and store the tomorrow.
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        // Assert that the enclosed call throws the expected exception.
        assertThrows(InvalidBookingException.class, () -> {
            // Create booking on booking manager.
            bookingManager.createBooking(
                // Provide the next argument for create booking.
                "R001", "U001", tomorrow,
                LocalTime.of(14, 0), LocalTime.of(12, 0), "Invalid Time"
            // Provide the remaining arguments needed to finish create booking.
            );
        });
    }

    // Apply the Test annotation to the next declaration.
    @Test
    // Apply the Order annotation to the next declaration.
    @Order(5)
    // Apply the DisplayName annotation to the next declaration.
    @DisplayName("Booking a deactivated room throws InvalidBookingException")
    // Define the test booking deactivated room test case.
    void testBookingDeactivatedRoom() {
        // Deactivate room on room manager.
        roomManager.deactivateRoom("R002");
        // Calculate and store the tomorrow.
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        // Assert that the enclosed call throws the expected exception.
        assertThrows(InvalidBookingException.class, () -> {
            // Create booking on booking manager.
            bookingManager.createBooking(
                // Provide the next argument for create booking.
                "R002", "U001", tomorrow,
                // Provide the next argument for create booking.
                LocalTime.of(9, 0), LocalTime.of(11, 0), "Deactivated Room"
            // Provide the remaining arguments needed to finish create booking.
            );
        });
    }

    // Apply the Test annotation to the next declaration.
    @Test
    // Apply the Order annotation to the next declaration.
    @Order(6)
    // Apply the DisplayName annotation to the next declaration.
    @DisplayName("Cancelling a booking changes status to CANCELLED")
    // Define the test cancel booking test case.
    void testCancelBooking() throws InvalidBookingException {
        // Calculate and store the tomorrow.
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        // Execute: Booking booking = bookingManager.createBooking(
        Booking booking = bookingManager.createBooking(
            // Execute: "R001", "U001", tomorrow,
            "R001", "U001", tomorrow,
            // Of on local time.
            LocalTime.of(9, 0), LocalTime.of(11, 0), "To Cancel"
        // Provide the remaining arguments needed to finish of.
        );

        // Cancel booking on booking manager.
        bookingManager.cancelBooking(booking.getId(), "U001");
        // Assert that the actual value matches the expected value.
        assertEquals(BookingStatus.CANCELLED, booking.getStatus());
    }

    // Apply the Test annotation to the next declaration.
    @Test
    // Apply the Order annotation to the next declaration.
    @Order(7)
    // Apply the DisplayName annotation to the next declaration.
    @DisplayName("Cancelling an already cancelled booking throws InvalidBookingException")
    // Define the test double cancellation test case.
    void testDoubleCancellation() throws InvalidBookingException {
        // Calculate and store the tomorrow.
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        // Execute: Booking booking = bookingManager.createBooking(
        Booking booking = bookingManager.createBooking(
            // Execute: "R001", "U001", tomorrow,
            "R001", "U001", tomorrow,
            // Of on local time.
            LocalTime.of(9, 0), LocalTime.of(11, 0), "To Cancel Twice"
        // Provide the remaining arguments needed to finish of.
        );
        // Cancel booking on booking manager.
        bookingManager.cancelBooking(booking.getId(), "U001");

        // Assert that the enclosed call throws the expected exception.
        assertThrows(InvalidBookingException.class, () -> {
            // Cancel booking on booking manager.
            bookingManager.cancelBooking(booking.getId(), "U001");
        });
    }

    // Apply the Test annotation to the next declaration.
    @Test
    // Apply the Order annotation to the next declaration.
    @Order(8)
    // Apply the DisplayName annotation to the next declaration.
    @DisplayName("Booking on a past date throws InvalidBookingException")
    // Define the test booking past date test case.
    void testBookingPastDate() {
        // Calculate and store the yesterday.
        LocalDate yesterday = LocalDate.now().minusDays(1);
        // Assert that the enclosed call throws the expected exception.
        assertThrows(InvalidBookingException.class, () -> {
            // Create booking on booking manager.
            bookingManager.createBooking(
                // Provide the next argument for create booking.
                "R001", "U001", yesterday,
                // Provide the next argument for create booking.
                LocalTime.of(9, 0), LocalTime.of(11, 0), "Past Date Booking"
            // Provide the remaining arguments needed to finish create booking.
            );
        });
    }

    // Apply the Test annotation to the next declaration.
    @Test
    // Apply the Order annotation to the next declaration.
    @Order(9)
    // Apply the DisplayName annotation to the next declaration.
    @DisplayName("Active booking count tracks correctly")
    // Define the test active booking count test case.
    void testActiveBookingCount() throws InvalidBookingException {
        // Calculate and store the tomorrow.
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        // Create booking on booking manager.
        bookingManager.createBooking(
            // Provide the next argument for create booking.
            "R001", "U001", tomorrow,
            // Provide the next argument for create booking.
            LocalTime.of(9, 0), LocalTime.of(11, 0), "Booking 1"
        // Provide the remaining arguments needed to finish create booking.
        );
        // Execute: Booking b2 = bookingManager.createBooking(
        Booking b2 = bookingManager.createBooking(
            // Execute: "R002", "U002", tomorrow,
            "R002", "U002", tomorrow,
            // Of on local time.
            LocalTime.of(9, 0), LocalTime.of(11, 0), "Booking 2"
        // Provide the remaining arguments needed to finish of.
        );

        // Assert that the actual value matches the expected value.
        assertEquals(2, bookingManager.getActiveBookingCount());

        // Cancel booking on booking manager.
        bookingManager.cancelBooking(b2.getId(), "U002");
        // Assert that the actual value matches the expected value.
        assertEquals(1, bookingManager.getActiveBookingCount());
    // End the current code block.
    }

    // Apply the Test annotation to the next declaration.
    @Test
    // Apply the Order annotation to the next declaration.
    @Order(10)
    // Apply the DisplayName annotation to the next declaration.
    @DisplayName("Cancelled booking frees the time slot for new bookings")
    // Define the test rebook after cancellation test case.
    void testRebookAfterCancellation() throws InvalidBookingException {
        // Calculate and store the tomorrow.
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        // Execute: Booking original = bookingManager.createBooking(
        Booking original = bookingManager.createBooking(
            // Execute: "R001", "U001", tomorrow,
            "R001", "U001", tomorrow,
            // Of on local time.
            LocalTime.of(9, 0), LocalTime.of(11, 0), "Original Booking"
        // Provide the remaining arguments needed to finish of.
        );
        // Cancel booking on booking manager.
        bookingManager.cancelBooking(original.getId(), "U001");

        // Execute: Booking rebooking = bookingManager.createBooking(
        Booking rebooking = bookingManager.createBooking(
            // Execute: "R001", "U002", tomorrow,
            "R001", "U002", tomorrow,
            // Of on local time.
            LocalTime.of(9, 0), LocalTime.of(11, 0), "Rebooked"
        // Provide the remaining arguments needed to finish of.
        );
        // Assert that the value is not null.
        assertNotNull(rebooking);
        // Assert that the actual value matches the expected value.
        assertEquals(BookingStatus.CONFIRMED, rebooking.getStatus());
    }
}

