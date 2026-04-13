// Declare that this file belongs to the com.scms.test package.
package com.scms.test;

// Import com.scms.exception.DuplicateDataException so it can be used in this file.
import com.scms.exception.DuplicateDataException;
import com.scms.exception.UnauthorizedActionException;
import com.scms.manager.MaintenanceManager;
import com.scms.manager.UserManager;
import com.scms.model.*;
import com.scms.pattern.NotificationService;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

// Apply the TestMethodOrder annotation to the next declaration.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// Declare the maintenance manager test class.
public class MaintenanceManagerTest {
    // Declare the maintenance manager.
    private MaintenanceManager maintenanceManager;
    // Declare the notification service.
    private NotificationService notificationService;
    // Declare the admin.
    private Administrator admin;
    // Declare the staff.
    private StaffMember staff;
    // Declare the student.
    private Student student;

    // Apply the BeforeEach annotation to the next declaration.
    @BeforeEach
    // Define the setter for up.
    void setUp() throws DuplicateDataException {
        // Reset the singleton instance on user manager.
        UserManager.resetInstance();
        // Calculate and store the user manager.
        UserManager userManager = UserManager.getInstance();

        // Initialize notification service with a new value.
        notificationService = new NotificationService();
        // Initialize maintenance manager with a new value.
        maintenanceManager = new MaintenanceManager(notificationService);

        // Initialize admin with a new value.
        admin = new Administrator("U001", "Admin", "admin@test.com", "pass");
        // Initialize staff with a new value.
        staff = new StaffMember("U002", "Staff", "staff@test.com", "pass", "CS");
        // Initialize student with a new value.
        student = new Student("U003", "Student", "student@test.com", "pass", "CS", 1);
        // Add user on user manager.
        userManager.addUser(admin);
        userManager.addUser(staff);
        userManager.addUser(student);
    }

    // Apply the Test annotation to the next declaration.
    @Test
    // Apply the Order annotation to the next declaration.
    @Order(1)
    // Apply the DisplayName annotation to the next declaration.
    @DisplayName("Creating a maintenance request returns request with PENDING status")
    // Define the test create request test case.
    void testCreateRequest() {
        // Execute: MaintenanceRequest request = maintenanceManager.createRequest(
        MaintenanceRequest request = maintenanceManager.createRequest(
            // Execute: "R001", "U002", "Projector broken", Urgency.HIGH
            "R001", "U002", "Projector broken", Urgency.HIGH
        // Execute: );
        );

        // Assert that the value is not null.
        assertNotNull(request);
        // Assert that the actual value matches the expected value.
        assertEquals(RequestStatus.PENDING, request.getStatus());
        // Assert that the actual value matches the expected value.
        assertEquals("R001", request.getRoomId());
        // Assert that the actual value matches the expected value.
        assertEquals("Projector broken", request.getDescription());
        // Assert that the actual value matches the expected value.
        assertEquals(Urgency.HIGH, request.getUrgency());
    }

    // Apply the Test annotation to the next declaration.
    @Test
    // Apply the Order annotation to the next declaration.
    @Order(2)
    // Apply the DisplayName annotation to the next declaration.
    @DisplayName("Admin can assign a maintenance request")
    // Define the test assign request test case.
    void testAssignRequest() throws UnauthorizedActionException {
        // Execute: MaintenanceRequest request = maintenanceManager.createRequest(
        MaintenanceRequest request = maintenanceManager.createRequest(
            // Execute: "R001", "U002", "Broken window", Urgency.MEDIUM
            "R001", "U002", "Broken window", Urgency.MEDIUM
        // Execute: );
        );

        // Assign request on maintenance manager.
        maintenanceManager.assignRequest(request.getId(), "U002", admin);

        // Assert that the actual value matches the expected value.
        assertEquals(RequestStatus.ASSIGNED, request.getStatus());
        assertEquals("U002", request.getAssigneeId());
    }

    // Apply the Test annotation to the next declaration.
    @Test
    // Apply the Order annotation to the next declaration.
    @Order(3)
    // Apply the DisplayName annotation to the next declaration.
    @DisplayName("Non-admin cannot assign maintenance requests - throws UnauthorizedActionException")
    // Define the test unauthorized assign test case.
    void testUnauthorizedAssign() {
        // Execute: MaintenanceRequest request = maintenanceManager.createRequest(
        MaintenanceRequest request = maintenanceManager.createRequest(
            // Execute: "R001", "U003", "Light flickering", Urgency.LOW
            "R001", "U003", "Light flickering", Urgency.LOW
        // Execute: );
        );

        // Assert that the enclosed call throws the expected exception.
        assertThrows(UnauthorizedActionException.class, () -> {
            // Assign request on maintenance manager.
            maintenanceManager.assignRequest(request.getId(), "U002", staff);
        });
    }

    // Apply the Test annotation to the next declaration.
    @Test
    // Apply the Order annotation to the next declaration.
    @Order(4)
    // Apply the DisplayName annotation to the next declaration.
    @DisplayName("Admin can mark a request as completed")
    // Define the test complete request test case.
    void testCompleteRequest() throws UnauthorizedActionException {
        // Execute: MaintenanceRequest request = maintenanceManager.createRequest(
        MaintenanceRequest request = maintenanceManager.createRequest(
            // Execute: "R001", "U002", "Door handle loose", Urgency.LOW
            "R001", "U002", "Door handle loose", Urgency.LOW
        // Execute: );
        );
        // Assign request on maintenance manager.
        maintenanceManager.assignRequest(request.getId(), "U002", admin);
        // Complete request on maintenance manager.
        maintenanceManager.completeRequest(request.getId(), admin);

        // Assert that the actual value matches the expected value.
        assertEquals(RequestStatus.COMPLETED, request.getStatus());
    }

    // Apply the Test annotation to the next declaration.
    @Test
    // Apply the Order annotation to the next declaration.
    @Order(5)
    // Apply the DisplayName annotation to the next declaration.
    @DisplayName("Student cannot complete maintenance requests - throws UnauthorizedActionException")
    // Define the test unauthorized complete test case.
    void testUnauthorizedComplete() {
        // Execute: MaintenanceRequest request = maintenanceManager.createRequest(
        MaintenanceRequest request = maintenanceManager.createRequest(
            // Execute: "R001", "U003", "Window crack", Urgency.HIGH
            "R001", "U003", "Window crack", Urgency.HIGH
        // Execute: );
        );

        // Assert that the enclosed call throws the expected exception.
        assertThrows(UnauthorizedActionException.class, () -> {
            // Complete request on maintenance manager.
            maintenanceManager.completeRequest(request.getId(), student);
        });
    }

    // Apply the Test annotation to the next declaration.
    @Test
    // Apply the Order annotation to the next declaration.
    @Order(6)
    // Apply the DisplayName annotation to the next declaration.
    @DisplayName("Pending count updates correctly after assignment")
    // Define the test pending count test case.
    void testPendingCount() throws UnauthorizedActionException {
        // Create request on maintenance manager.
        maintenanceManager.createRequest("R001", "U002", "Issue 1", Urgency.LOW);
        // Calculate and store the req2.
        MaintenanceRequest req2 = maintenanceManager.createRequest("R002", "U003", "Issue 2", Urgency.MEDIUM);

        // Assert that the actual value matches the expected value.
        assertEquals(2, maintenanceManager.getPendingCount());

        // Assign request on maintenance manager.
        maintenanceManager.assignRequest(req2.getId(), "U002", admin);
        // Assert that the actual value matches the expected value.
        assertEquals(1, maintenanceManager.getPendingCount());
        assertEquals(1, maintenanceManager.getAssignedCount());
    }

    // Apply the Test annotation to the next declaration.
    @Test
    // Apply the Order annotation to the next declaration.
    @Order(7)
    // Apply the DisplayName annotation to the next declaration.
    @DisplayName("Requests can be filtered by status")
    // Define the test filter by status test case.
    void testFilterByStatus() throws UnauthorizedActionException {
        // Create request on maintenance manager.
        maintenanceManager.createRequest("R001", "U002", "Issue A", Urgency.LOW);
        // Calculate and store the req B.
        MaintenanceRequest reqB = maintenanceManager.createRequest("R001", "U002", "Issue B", Urgency.HIGH);
        // Assign request on maintenance manager.
        maintenanceManager.assignRequest(reqB.getId(), "U002", admin);

        // Assert that the actual value matches the expected value.
        assertEquals(1, maintenanceManager.getRequestsByStatus(RequestStatus.PENDING).size());
        assertEquals(1, maintenanceManager.getRequestsByStatus(RequestStatus.ASSIGNED).size());
        assertEquals(0, maintenanceManager.getRequestsByStatus(RequestStatus.COMPLETED).size());
    }

    // Apply the Test annotation to the next declaration.
    @Test
    // Apply the Order annotation to the next declaration.
    @Order(8)
    // Apply the DisplayName annotation to the next declaration.
    @DisplayName("Requests can be filtered by reporter")
    // Define the test filter by reporter test case.
    void testFilterByReporter() {
        // Create request on maintenance manager.
        maintenanceManager.createRequest("R001", "U002", "Staff Issue", Urgency.LOW);
        maintenanceManager.createRequest("R001", "U003", "Student Issue", Urgency.MEDIUM);
        maintenanceManager.createRequest("R002", "U002", "Another Staff Issue", Urgency.HIGH);

        // Assert that the actual value matches the expected value.
        assertEquals(2, maintenanceManager.getRequestsByReporter("U002").size());
        assertEquals(1, maintenanceManager.getRequestsByReporter("U003").size());
    }

    // Apply the Test annotation to the next declaration.
    @Test
    // Apply the Order annotation to the next declaration.
    @Order(9)
    // Apply the DisplayName annotation to the next declaration.
    @DisplayName("Urgency breakdown counts correctly")
    // Define the test urgency breakdown test case.
    void testUrgencyBreakdown() {
        // Create request on maintenance manager.
        maintenanceManager.createRequest("R001", "U002", "Low", Urgency.LOW);
        maintenanceManager.createRequest("R001", "U002", "High 1", Urgency.HIGH);
        maintenanceManager.createRequest("R002", "U003", "High 2", Urgency.HIGH);

        // Calculate and store the breakdown.
        var breakdown = maintenanceManager.getUrgencyBreakdown();
        // Assert that the actual value matches the expected value.
        assertEquals(1, breakdown.getOrDefault(Urgency.LOW, 0));
        assertEquals(2, breakdown.getOrDefault(Urgency.HIGH, 0));
        assertEquals(0, breakdown.getOrDefault(Urgency.CRITICAL, 0));
    }
}

