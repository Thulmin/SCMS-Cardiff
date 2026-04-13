// Declare that this file belongs to the com.scms.test package.
package com.scms.test;

// Import com.scms.exception.DuplicateDataException so it can be used in this file.
import com.scms.exception.DuplicateDataException;
import com.scms.manager.UserManager;
import com.scms.model.*;
import com.scms.pattern.NotificationService;
import com.scms.pattern.Observer;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Apply the TestMethodOrder annotation to the next declaration.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// Declare the notification service test class.
public class NotificationServiceTest {
    // Declare the notification service.
    private NotificationService notificationService;

    // Apply the BeforeEach annotation to the next declaration.
    @BeforeEach
    // Define the setter for up.
    void setUp() {
        // Reset the singleton instance on user manager.
        UserManager.resetInstance();
        // Initialize notification service with a new value.
        notificationService = new NotificationService();
    }

    // Apply the Test annotation to the next declaration.
    @Test
    // Apply the Order annotation to the next declaration.
    @Order(1)
    // Apply the DisplayName annotation to the next declaration.
    @DisplayName("Global subscribers receive notifications via notifyAll")
    // Define the test global notification test case.
    void testGlobalNotification() {
        // Create and store the received.
        List<String> received = new ArrayList<>();
        // Calculate and store the observer.
        Observer observer = message -> received.add(message);

        // Subscribe on notification service.
        notificationService.subscribe(observer);
        // Notify all on notification service.
        notificationService.notifyAll("System maintenance at 10pm");

        // Assert that the actual value matches the expected value.
        assertEquals(1, received.size());
        assertEquals("System maintenance at 10pm", received.get(0));
    // End the current code block.
    }

    // Apply the Test annotation to the next declaration.
    @Test
    // Apply the Order annotation to the next declaration.
    @Order(2)
    // Apply the DisplayName annotation to the next declaration.
    @DisplayName("Multiple subscribers all receive the same notification")
    void testMultipleSubscribers() {
        // Create and store the received1.
        List<String> received1 = new ArrayList<>();
        // Create and store the received2.
        List<String> received2 = new ArrayList<>();
        // Calculate and store the obs1.
        Observer obs1 = message -> received1.add(message);
        // Calculate and store the obs2.
        Observer obs2 = message -> received2.add(message);

        // Subscribe on notification service.
        notificationService.subscribe(obs1);
        notificationService.subscribe(obs2);
        // Notify all on notification service.
        notificationService.notifyAll("Broadcast message");

        // Assert that the actual value matches the expected value.
        assertEquals(1, received1.size());
        assertEquals(1, received2.size());
        // Assert that the actual value matches the expected value.
        assertEquals("Broadcast message", received1.get(0));
        // Assert that the actual value matches the expected value.
        assertEquals("Broadcast message", received2.get(0));
    }

    // Apply the Test annotation to the next declaration.
    @Test
    // Apply the Order annotation to the next declaration.
    @Order(3)
    // Apply the DisplayName annotation to the next declaration.
    @DisplayName("Unsubscribed observer does not receive notifications")
    // Define the test unsubscribe test case.
    void testUnsubscribe() {
        // Create and store the received.
        List<String> received = new ArrayList<>();
        // Calculate and store the observer.
        Observer observer = message -> received.add(message);

        // Subscribe on notification service.
        notificationService.subscribe(observer);
        // Unsubscribe on notification service.
        notificationService.unsubscribe(observer);
        // Notify all on notification service.
        notificationService.notifyAll("Should not receive this");

        // Assert that the condition is true.
        assertTrue(received.isEmpty());
    }

    // Apply the Test annotation to the next declaration.
    @Test
    // Apply the Order annotation to the next declaration.
    @Order(4)
    // Apply the DisplayName annotation to the next declaration.
    @DisplayName("Topic-based notifications reach only topic subscribers")
    // Define the test topic notification test case.
    void testTopicNotification() {
        // Create and store the booking messages.
        List<String> bookingMessages = new ArrayList<>();
        // Create and store the maintenance messages.
        List<String> maintenanceMessages = new ArrayList<>();
        // Calculate and store the booking obs.
        Observer bookingObs = message -> bookingMessages.add(message);
        // Calculate and store the maintenance obs.
        Observer maintenanceObs = message -> maintenanceMessages.add(message);

        // Subscribe on notification service.
        notificationService.subscribe("booking", bookingObs);
        // Subscribe on notification service.
        notificationService.subscribe("maintenance", maintenanceObs);

        // Notify topic on notification service.
        notificationService.notifyTopic("booking", "Room booked successfully");
        // Notify topic on notification service.
        notificationService.notifyTopic("maintenance", "New maintenance request");

        // Assert that the actual value matches the expected value.
        assertEquals(1, bookingMessages.size());
        assertEquals("Room booked successfully", bookingMessages.get(0));
        assertEquals(1, maintenanceMessages.size());
        assertEquals("New maintenance request", maintenanceMessages.get(0));
        // Ensure no cross-contamination
        // Assert that the condition is false.
        assertFalse(bookingMessages.contains("New maintenance request"));
    }

    // Apply the Test annotation to the next declaration.
    @Test
    // Apply the Order annotation to the next declaration.
    @Order(5)
    // Apply the DisplayName annotation to the next declaration.
    @DisplayName("Individual observer notification works correctly")
    // Define the test notify individual test case.
    void testNotifyIndividual() {
        // Create and store the received.
        List<String> received = new ArrayList<>();
        // Calculate and store the observer.
        Observer observer = message -> received.add(message);

        // Notify observer on notification service.
        notificationService.notifyObserver(observer, "Personal message");

        // Assert that the actual value matches the expected value.
        assertEquals(1, received.size());
        assertEquals("Personal message", received.get(0));
    }

    // Apply the Test annotation to the next declaration.
    @Test
    // Apply the Order annotation to the next declaration.
    @Order(6)
    // Apply the DisplayName annotation to the next declaration.
    @DisplayName("Notification log records all sent notifications")
    // Define the test notification log test case.
    void testNotificationLog() {
        // Calculate and store the dummy.
        Observer dummy = message -> {};
        // Subscribe on notification service.
        notificationService.subscribe(dummy);

        // Notify all on notification service.
        notificationService.notifyAll("Message 1");
        // Notify all on notification service.
        notificationService.notifyAll("Message 2");
        // Notify topic on notification service.
        notificationService.notifyTopic("alerts", "Alert 1");

        // Calculate and store the log.
        List<String> log = notificationService.getNotificationLog();
        // Assert that the actual value matches the expected value.
        assertEquals(3, log.size());
    }

    // Apply the Test annotation to the next declaration.
    @Test
    // Apply the Order annotation to the next declaration.
    @Order(7)
    // Apply the DisplayName annotation to the next declaration.
    @DisplayName("User model receives notifications through Observer pattern")
    // Define the test user as observer test case.
    void testUserAsObserver() throws DuplicateDataException {
        // Calculate and store the user manager.
        UserManager userManager = UserManager.getInstance();
        // Create and store the student.
        Student student = new Student("U001", "Test Student", "test@test.com", "pass", "CS", 1);
        // Add user on user manager.
        userManager.addUser(student);

        // Subscribe on notification service.
        notificationService.subscribe(student);
        // Notify all on notification service.
        notificationService.notifyAll("Campus announcement");

        // Assert that the actual value matches the expected value.
        assertEquals(1, student.getNotifications().size());
        // Assert that the actual value matches the expected value.
        assertEquals("Campus announcement", student.getNotifications().get(0).getMessage());
    }

    // Apply the Test annotation to the next declaration.
    @Test
    // Apply the Order annotation to the next declaration.
    @Order(8)
    // Apply the DisplayName annotation to the next declaration.
    @DisplayName("Subscriber count is tracked correctly")
    // Define the test subscriber count test case.
    void testSubscriberCount() {
        // Calculate and store the obs1.
        Observer obs1 = message -> {};
        // Calculate and store the obs2.
        Observer obs2 = message -> {};

        // Assert that the actual value matches the expected value.
        assertEquals(0, notificationService.getSubscriberCount());
        // Subscribe on notification service.
        notificationService.subscribe(obs1);
        // Assert that the actual value matches the expected value.
        assertEquals(1, notificationService.getSubscriberCount());
        // Subscribe on notification service.
        notificationService.subscribe(obs2);
        assertEquals(2, notificationService.getSubscriberCount());
        // Unsubscribe on notification service.
        notificationService.unsubscribe(obs1);
        // Assert that the actual value matches the expected value.
        assertEquals(1, notificationService.getSubscriberCount());
    }

    // Apply the Test annotation to the next declaration.
    @Test
    // Apply the Order annotation to the next declaration.
    @Order(9)
    // Apply the DisplayName annotation to the next declaration.
    @DisplayName("Duplicate subscription does not add observer twice")
    // Define the test duplicate subscription test case.
    void testDuplicateSubscription() {
        // Create and store the received.
        List<String> received = new ArrayList<>();
        // Calculate and store the observer.
        Observer observer = message -> received.add(message);

        // Subscribe on notification service.
        notificationService.subscribe(observer);
        // Subscribe on notification service.
        notificationService.subscribe(observer);
        // Notify all on notification service.
        notificationService.notifyAll("Test");

        // Assert that the actual value matches the expected value.
        assertEquals(1, received.size());
        // Assert that the actual value matches the expected value.
        assertEquals(1, notificationService.getSubscriberCount());
    }
}

