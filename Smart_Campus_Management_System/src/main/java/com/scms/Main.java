// Declare that this file belongs to the com.scms package.
package com.scms;

// Import com.scms.exception.DuplicateDataException so it can be used in this file.
import com.scms.facade.CampusManagementFacade;
import com.scms.gui.MainFrame;
import com.scms.manager.UserManager;
import com.scms.model.*;
import com.scms.pattern.RoomFactory;
import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalTime;

// Declare the main class.
public class Main {
    // Define the main method.
    public static void main(String[] args) {
        // Start a try block to handle possible exceptions.
        try {
            // Apply the system look and feel to the Swing interface.
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        // Catch any exception thrown in the preceding try block.
        } catch (Exception e) {
            // Fall back to default look and feel
        }

        // Schedule the enclosed work on the Swing event dispatch thread.
        SwingUtilities.invokeLater(() -> {
            // Create and store the facade.
            CampusManagementFacade facade = new CampusManagementFacade();
            // Load sample data.
            loadSampleData(facade);

            // Create and store the frame.
            MainFrame frame = new MainFrame(facade);
            // Make the component visible on screen.
            frame.setVisible(true);
        });
    }

    // Define the load sample data method.
    private static void loadSampleData(CampusManagementFacade facade) {
        // Start a try block to handle possible exceptions.
        try {
            // Create users
            // Start creating and assigning the admin.
            Administrator admin = new Administrator("U001", "Dr. Sarah Williams",
                // Provide the remaining values needed to finish admin.
                "admin@cardiffmet.ac.uk", "admin123");
            // Start creating and assigning the staff1.
            StaffMember staff1 = new StaffMember("U002", "Dr. John Smith",
                // Provide the remaining values needed to finish staff1.
                "j.smith@cardiffmet.ac.uk", "staff123", "Computer Science");
            // Start creating and assigning the staff2.
            StaffMember staff2 = new StaffMember("U003", "Prof. Emma Davis",
                // Provide the remaining values needed to finish staff2.
                "e.davis@cardiffmet.ac.uk", "staff123", "Engineering");
            // Start creating and assigning the student1.
            Student student1 = new Student("U004", "Sarah Jones",
                // Provide the remaining values needed to finish student1.
                "s.jones@cardiffmet.ac.uk", "student123", "BSc Computer Science", 2);
            // Start creating and assigning the student2.
            Student student2 = new Student("U005", "Michael Brown",
                // Provide the remaining values needed to finish student2.
                "m.brown@cardiffmet.ac.uk", "student123", "MSc Data Science", 1);

            // Add user through the facade.
            facade.addUser(admin);
            facade.addUser(staff1);
            facade.addUser(staff2);
            facade.addUser(student1);
            facade.addUser(student2);

            // Create rooms using Factory pattern
            // Add room through the facade.
            facade.addRoom("R001", "Main Lecture Hall A", 200, RoomType.LECTURE_HALL);
            facade.addRoom("R002", "Computer Lab 1", 40, RoomType.COMPUTER_LAB);
            facade.addRoom("R003", "Seminar Room 201", 30, RoomType.SEMINAR_ROOM);
            facade.addRoom("R004", "Meeting Room B3", 12, RoomType.MEETING_ROOM);
            facade.addRoom("R005", "Engineering Workshop", 25, RoomType.WORKSHOP);
            facade.addRoom("R006", "Lecture Hall B", 150, RoomType.LECTURE_HALL);
            facade.addRoom("R007", "Computer Lab 2", 35, RoomType.COMPUTER_LAB);

            // Create sample bookings (log in as admin temporarily)
            // Log in through the facade.
            facade.login("admin@cardiffmet.ac.uk", "admin123");

            // Calculate and store the tomorrow.
            LocalDate tomorrow = LocalDate.now().plusDays(1);
            // Calculate and store the next week.
            LocalDate nextWeek = LocalDate.now().plusDays(7);

            // Book room through the facade.
            facade.bookRoom("R001", "U002", tomorrow,
                LocalTime.of(9, 0), LocalTime.of(11, 0), "Advanced Programming Lecture");
            facade.bookRoom("R002", "U002", tomorrow,
                LocalTime.of(14, 0), LocalTime.of(16, 0), "Java Lab Session");
            // Book room through the facade.
            facade.bookRoom("R003", "U003", nextWeek,
                LocalTime.of(10, 0), LocalTime.of(12, 0), "Engineering Seminar");
            facade.bookRoom("R004", "U004", tomorrow,
                LocalTime.of(13, 0), LocalTime.of(14, 0), "Group Study Session");
            facade.bookRoom("R001", "U003", nextWeek,
                LocalTime.of(14, 0), LocalTime.of(16, 0), "Guest Lecture");

            // Create sample maintenance requests
            // Report maintenance through the facade.
            facade.reportMaintenance("R002", "U002",
                // Provide the remaining arguments needed to finish report maintenance.
                "Projector not working - displays blue screen", Urgency.HIGH);
            facade.reportMaintenance("R001", "U004",
                "Air conditioning making loud noise", Urgency.MEDIUM);
            facade.reportMaintenance("R005", "U003",
                "Safety light needs replacement", Urgency.CRITICAL);
            facade.reportMaintenance("R003", "U002",
                // Provide the remaining arguments needed to finish report maintenance.
                "Whiteboard markers are dried out", Urgency.LOW);

            // Log out through the facade.
            facade.logout();

        // Catch any exception thrown in the preceding try block.
        } catch (Exception e) {
            // Print a line on system err.
            System.err.println("Error loading sample data: " + e.getMessage());
            // Print the full stack trace for debugging.
            e.printStackTrace();
        }
    }
}

