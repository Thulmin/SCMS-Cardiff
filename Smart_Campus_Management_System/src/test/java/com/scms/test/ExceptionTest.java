// Declare that this file belongs to the com.scms.test package.
package com.scms.test;

// Import com.scms.exception.* so it can be used in this file.
import com.scms.exception.*;
import com.scms.manager.BookingManager;
import com.scms.manager.MaintenanceManager;
import com.scms.manager.RoomManager;
import com.scms.manager.UserManager;
import com.scms.model.*;
import com.scms.pattern.NotificationService;
import com.scms.pattern.RoomFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

// Apply the TestMethodOrder annotation to the next declaration.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// Declare the exception test class.
public class ExceptionTest {
    // Declare the user manager.
    private UserManager userManager;
    // Declare the room manager.
    private RoomManager roomManager;
    // Declare the booking manager.
    private BookingManager bookingManager;
    // Declare the maintenance manager.
    private MaintenanceManager maintenanceManager;
    // Declare the notification service.
    private NotificationService notificationService;

    // Apply the BeforeEach annotation to the next declaration.
    @BeforeEach
    // Define the setter for up.
    void setUp() throws DuplicateDataException {
        // Reset the singleton instance on user manager.
        UserManager.resetInstance();
        // Set user manager using the computed value.
        userManager = UserManager.getInstance();
        // Initialize room manager with a new value.
        roomManager = new RoomManager();
        // Initialize notification service with a new value.
        notificationService = new NotificationService();
        // Initialize booking manager with a new value.
        bookingManager = new BookingManager(roomManager, notificationService);
        // Initialize maintenance manager with a new value.
        maintenanceManager = new MaintenanceManager(notificationService);

        // Calculate and store the room.
        Room room = RoomFactory.createLectureHall("R001", "Test Hall", 100);
        // Add room on room manager.
        roomManager.addRoom(room);

        // Create and store the admin.
        Administrator admin = new Administrator("U001", "Admin", "admin@test.com", "pass");
        // Create and store the staff.
        StaffMember staff = new StaffMember("U002", "Staff", "staff@test.com", "pass", "CS");
        // Create and store the student.
        Student student = new Student("U003", "Student", "student@test.com", "pass", "CS", 1);
        // Add user on user manager.
        userManager.addUser(admin);
        // Add user on user manager.
        userManager.addUser(staff);
        // Add user on user manager.
        userManager.addUser(student);
    }

    // --- InvalidBookingException Tests ---

    // Apply the Test annotation to the next declaration.
    @Test
    // Apply the Order annotation to the next declaration.
    @Order(1)
    // Apply the DisplayName annotation to the next declaration.
    @DisplayName("InvalidBookingException contains descriptive message for non-existent room")
    // Define the test invalid booking exception message test case.
    void testInvalidBookingExceptionMessage() {
        // Execute: InvalidBookingException ex = assertThrows(InvalidBookingException.class, () -> {
        InvalidBookingException ex = assertThrows(InvalidBookingException.class, () -> {
            // Create booking on booking manager.
            bookingManager.createBooking("INVALID", "U001",
                // Provide the next argument for create booking.
                LocalDate.now().plusDays(1),
                // Provide the remaining arguments needed to finish create booking.
                LocalTime.of(9, 0), LocalTime.of(11, 0), "Test");
        });
        // Assert that the condition is true.
        assertTrue(ex.getMessage().contains("does not exist"));
    }

    // Apply the Test annotation to the next declaration.
    @Test
    // Apply the Order annotation to the next declaration.
    @Order(2)
    // Apply the DisplayName annotation to the next declaration.
    @DisplayName("InvalidBookingException for time conflict includes room and time details")
    // Define the test double booking exception message test case.
    void testDoubleBookingExceptionMessage() throws InvalidBookingException {
        // Calculate and store the tomorrow.
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        // Create booking on booking manager.
        bookingManager.createBooking("R001", "U001", tomorrow,
            // Provide the remaining arguments needed to finish create booking.
            LocalTime.of(9, 0), LocalTime.of(11, 0), "First");

        // Execute: InvalidBookingException ex = assertThrows(InvalidBookingException.class, () -> {
        InvalidBookingException ex = assertThrows(InvalidBookingException.class, () -> {
            // Create booking on booking manager.
            bookingManager.createBooking("R001", "U002", tomorrow,
                // Provide the remaining arguments needed to finish create booking.
                LocalTime.of(10, 0), LocalTime.of(12, 0), "Overlap");
        });
        // Assert that the condition is true.
        assertTrue(ex.getMessage().contains("conflict"));
    }

    // Apply the Test annotation to the next declaration.
    @Test
    // Apply the Order annotation to the next declaration.
    @Order(3)
    // Apply the DisplayName annotation to the next declaration.
    @DisplayName("Cancelling non-existent booking throws InvalidBookingException")
    // Define the test cancel non existent booking test case.
    void testCancelNonExistentBooking() {
        // Execute: InvalidBookingException ex = assertThrows(InvalidBookingException.class, () -> {
        InvalidBookingException ex = assertThrows(InvalidBookingException.class, () -> {
            // Cancel booking on booking manager.
            bookingManager.cancelBooking("BK9999", "U001");
        });
        // Assert that the condition is true.
        assertTrue(ex.getMessage().contains("not found"));
    }

    // --- UnauthorizedActionException Tests ---

    // Apply the Test annotation to the next declaration.
    @Test
    // Apply the Order annotation to the next declaration.
    @Order(4)
    // Apply the DisplayName annotation to the next declaration.
    @DisplayName("Staff member cannot assign maintenance - throws UnauthorizedActionException")
    // Define the test staff cannot assign maintenance test case.
    void testStaffCannotAssignMaintenance() {
        // Execute: MaintenanceRequest request = maintenanceManager.createRequest(
        MaintenanceRequest request = maintenanceManager.createRequest(
            // Execute: "R001", "U003", "Test issue", Urgency.LOW
            "R001", "U003", "Test issue", Urgency.LOW
        // Execute: );
        );
        // Calculate and store the staff.
        StaffMember staff = (StaffMember) userManager.getUserById("U002");

        // Execute: UnauthorizedActionException ex = assertThrows(UnauthorizedActionException.class, () -> {
        UnauthorizedActionException ex = assertThrows(UnauthorizedActionException.class, () -> {
            // Assign request on maintenance manager.
            maintenanceManager.assignRequest(request.getId(), "U002", staff);
        });
        // Assert that the condition is true.
        assertTrue(ex.getMessage().contains("Only administrators"));
    }

    // Apply the Test annotation to the next declaration.
    @Test
    // Apply the Order annotation to the next declaration.
    @Order(5)
    // Apply the DisplayName annotation to the next declaration.
    @DisplayName("Student cannot complete maintenance - throws UnauthorizedActionException")
    // Define the test student cannot complete maintenance test case.
    void testStudentCannotCompleteMaintenance() {
        // Execute: MaintenanceRequest request = maintenanceManager.createRequest(
        MaintenanceRequest request = maintenanceManager.createRequest(
            // Execute: "R001", "U003", "Test issue", Urgency.MEDIUM
            "R001", "U003", "Test issue", Urgency.MEDIUM
        // Execute: );
        );
        // Calculate and store the student.
        Student student = (Student) userManager.getUserById("U003");

        // Execute: UnauthorizedActionException ex = assertThrows(UnauthorizedActionException.class, () -> {
        UnauthorizedActionException ex = assertThrows(UnauthorizedActionException.class, () -> {
            // Complete request on maintenance manager.
            maintenanceManager.completeRequest(request.getId(), student);
        // End the lambda block and complete the surrounding call.
        });
        // Assert that the condition is true.
        assertTrue(ex.getMessage().contains("Only administrators"));
    }

    // --- DuplicateDataException Tests ---

    // Apply the Test annotation to the next declaration.
    @Test
    // Apply the Order annotation to the next declaration.
    @Order(6)
    // Apply the DisplayName annotation to the next declaration.
    @DisplayName("Adding duplicate user ID throws DuplicateDataException")
    // Define the test duplicate user id test case.
    void testDuplicateUserId() {
        // Execute: DuplicateDataException ex = assertThrows(DuplicateDataException.class, () -> {
        DuplicateDataException ex = assertThrows(DuplicateDataException.class, () -> {
            // Add user on user manager.
            userManager.addUser(new Student("U001", "Duplicate", "new@test.com", "pass", "CS", 1));
        });
        // Assert that the condition is true.
        assertTrue(ex.getMessage().contains("already exists"));
    }

    // Apply the Test annotation to the next declaration.
    @Test
    // Apply the Order annotation to the next declaration.
    @Order(7)
    // Apply the DisplayName annotation to the next declaration.
    @DisplayName("Adding duplicate email throws DuplicateDataException")
    // Define the test duplicate email test case.
    void testDuplicateEmail() {
        // Execute: DuplicateDataException ex = assertThrows(DuplicateDataException.class, () -> {
        DuplicateDataException ex = assertThrows(DuplicateDataException.class, () -> {
            // Add user on user manager.
            userManager.addUser(new Student("U099", "New User", "admin@test.com", "pass", "CS", 1));
        });
        // Assert that the condition is true.
        assertTrue(ex.getMessage().contains("already exists"));
    }

    // Apply the Test annotation to the next declaration.
    @Test
    // Apply the Order annotation to the next declaration.
    @Order(8)
    // Apply the DisplayName annotation to the next declaration.
    @DisplayName("Adding duplicate room ID throws DuplicateDataException")
    // Define the test duplicate room id test case.
    void testDuplicateRoomId() {
        // Execute: DuplicateDataException ex = assertThrows(DuplicateDataException.class, () -> {
        DuplicateDataException ex = assertThrows(DuplicateDataException.class, () -> {
            // Add room on room manager.
            roomManager.addRoom(RoomFactory.createLectureHall("R001", "Duplicate", 50));
        });
        // Assert that the condition is true.
        assertTrue(ex.getMessage().contains("already exists"));
    }


    // Apply the Test annotation to the next declaration.
    @Test
    // Apply the Order annotation to the next declaration.
    @Order(9)
    // Apply the DisplayName annotation to the next declaration.
    @DisplayName("EXPECTED FAIL: Booking with null date should throw InvalidBookingException but throws NullPointerException")
    // Define the test expected fail null date booking test case.
    void testExpectedFailNullDateBooking() {
        // Expected to FAIL: null date causes NullPointerException at
        // date.isBefore(LocalDate.now()) instead of InvalidBookingException.
        // This documents a known gap in input validation.
        // Assert that the enclosed call throws the expected exception.
        assertThrows(InvalidBookingException.class, () -> {
            // Create booking on booking manager.
            bookingManager.createBooking("R001", "U001", null,
                // Provide the remaining arguments needed to finish create booking.
                LocalTime.of(9, 0), LocalTime.of(11, 0), "Null date test");
        });
    }

    // --- General Error Handling Tests ---

    // Apply the Test annotation to the next declaration.
    @Test
    // Apply the Order annotation to the next declaration.
    @Order(10)
    // Apply the DisplayName annotation to the next declaration.
    @DisplayName("Authentication with wrong credentials returns null")
    // Define the test failed authentication test case.
    void testFailedAuthentication() {
        // Calculate and store the result.
        User result = userManager.authenticate("admin@test.com", "wrongpassword");
        // Assert null.
        assertNull(result);
    }

    // Apply the Test annotation to the next declaration.
    @Test
    // Apply the Order annotation to the next declaration.
    @Order(11)
    // Apply the DisplayName annotation to the next declaration.
    @DisplayName("Getting non-existent user returns null without exception")
    // Define the test get non existent user test case.
    void testGetNonExistentUser() {
        // Calculate and store the result.
        User result = userManager.getUserById("NONEXISTENT");
        // Assert null.
        assertNull(result);
    }

    // Apply the Test annotation to the next declaration.
    @Test
    // Apply the Order annotation to the next declaration.
    @Order(12)
    // Apply the DisplayName annotation to the next declaration.
    @DisplayName("Getting non-existent room returns null without exception")
    // Define the test get non existent room test case.
    void testGetNonExistentRoom() {
        // Calculate and store the result.
        Room result = roomManager.getRoomById("NONEXISTENT");
        // Assert null.
        assertNull(result);
    }
}

