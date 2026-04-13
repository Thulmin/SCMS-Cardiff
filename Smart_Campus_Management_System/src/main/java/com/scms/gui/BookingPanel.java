// Declare that this file belongs to the com.scms.gui package.
package com.scms.gui;

// Import com.scms.exception.InvalidBookingException so it can be used in this file.
import com.scms.exception.InvalidBookingException;
import com.scms.facade.CampusManagementFacade;
import com.scms.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

// Declare the booking panel class.
public class BookingPanel extends JPanel {
    // Declare the facade.
    private CampusManagementFacade facade;
    // Declare the booking table.
    private JTable bookingTable;
    // Declare the table model.
    private DefaultTableModel tableModel;

    // Construct a new booking panel instance.
    public BookingPanel(CampusManagementFacade facade) {
        // Set facade using the computed value.
        this.facade = facade;
        // Set layout.
        setLayout(new BorderLayout());
        // Set background.
        setBackground(UIConstants.BG_MAIN);
        // Init components.
        initComponents();
    }

    // Define the init components method.
    private void initComponents() {
        // Create and store the main panel.
        JPanel mainPanel = new JPanel(new BorderLayout(0, 15));
        // Set background on main panel.
        mainPanel.setBackground(UIConstants.BG_MAIN);
        // Set border on main panel.
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // Header
        // Create and store the header panel.
        JPanel headerPanel = new JPanel(new BorderLayout());
        // Set background on header panel.
        headerPanel.setBackground(UIConstants.BG_MAIN);

        // Create and store the title.
        JLabel title = new JLabel("Room Bookings");
        // Set font on title.
        title.setFont(UIConstants.FONT_TITLE);
        // Set foreground on title.
        title.setForeground(UIConstants.TEXT_PRIMARY);
        // Add on header panel.
        headerPanel.add(title, BorderLayout.WEST);

        // Create and store the button panel.
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        // Set background on button panel.
        buttonPanel.setBackground(UIConstants.BG_MAIN);

        // Calculate and store the book button.
        JButton bookBtn = UIConstants.createStyledButton("New Booking", UIConstants.SUCCESS);
        // Attach an action listener on book button.
        bookBtn.addActionListener(e -> showBookingDialog());
        // Add on button panel.
        buttonPanel.add(bookBtn);

        // Calculate and store the refresh button.
        JButton refreshBtn = UIConstants.createStyledButton("Refresh", UIConstants.ACCENT);
        // Attach an action listener on refresh button.
        refreshBtn.addActionListener(e -> refresh());
        // Add on button panel.
        buttonPanel.add(refreshBtn);

        // Add on header panel.
        headerPanel.add(buttonPanel, BorderLayout.EAST);
        // Add on main panel.
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Table
        // Calculate and store the columns.
        String[] columns = {"Booking ID", "Room", "Date", "Time Slot", "Purpose", "Status", "Booked By"};
        // Create and customize the table model.
        tableModel = new DefaultTableModel(columns, 0) {
            // Apply the Override annotation to the next declaration.
            @Override
            // Define the check for whether cell editable.
            public boolean isCellEditable(int row, int column) { return false; }
        // End the anonymous type definition and finish the assignment.
        };
        // Set booking table using the computed value.
        bookingTable = UIConstants.createStyledTable();
        // Set model on booking table.
        bookingTable.setModel(tableModel);

        // Create and store the scroll pane.
        JScrollPane scrollPane = new JScrollPane(bookingTable);
        // Set border on scroll pane.
        scrollPane.setBorder(BorderFactory.createLineBorder(UIConstants.BORDER_COLOR));
        // Add on main panel.
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Cancel button
        // Create and store the action panel.
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        // Set background on action panel.
        actionPanel.setBackground(UIConstants.BG_MAIN);

        // Calculate and store the cancel button.
        JButton cancelBtn = UIConstants.createStyledButton("Cancel Booking", UIConstants.DANGER);
        // Attach an action listener on cancel button.
        cancelBtn.addActionListener(e -> cancelSelectedBooking());
        // Add on action panel.
        actionPanel.add(cancelBtn);

        // Add on main panel.
        mainPanel.add(actionPanel, BorderLayout.SOUTH);
        // Add.
        add(mainPanel, BorderLayout.CENTER);
    }

    // Define the refresh method.
    public void refresh() {
        // Set row count on table model.
        tableModel.setRowCount(0);
        // Calculate and store the current.
        User current = facade.getCurrentUser();
        // Execute: if (current == null) return;
        if (current == null) return;

        // Declare the bookings.
        List<Booking> bookings;
        // Check whether current.getRole() is UserRole.ADMINISTRATOR.
        if (current.getRole() == UserRole.ADMINISTRATOR) {
            // Set bookings using the computed value.
            bookings = facade.getAllBookings();
        // Execute: } else {
        } else {
            // Set bookings using the computed value.
            bookings = facade.getUserBookings(current.getId());
        }

        // Loop using Booking b : bookings.
        for (Booking b : bookings) {
            // Calculate and store the room.
            Room room = facade.getRoomById(b.getRoomId());
            // Calculate and store the room name.
            String roomName = room != null ? room.getName() : b.getRoomId();
            // Calculate and store the user.
            User user = facade.getUserById(b.getUserId());
            // Calculate and store the user name.
            String userName = user != null ? user.getName() : b.getUserId();

            // Add row on table model.
            tableModel.addRow(new Object[]{
                // Add the next value to the current array literal.
                b.getId(),
                // Add the next value to the current array literal.
                roomName,
                // Add the next value to the current array literal.
                b.getDate().format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
                // Add the next value to the current array literal.
                b.getTimeSlot(),
                // Add the next value to the current array literal.
                b.getPurpose(),
                // Add the next value to the current array literal.
                b.getStatus().getDisplayName(),
                // Add the next value to the current array literal.
                userName
            // Finish the current array literal.
            });
        }
    }

    // Define the show booking dialog method.
    private void showBookingDialog() {
        // Calculate and store the current.
        User current = facade.getCurrentUser();
        // Execute: if (current == null) return;
        if (current == null) return;

        // Calculate and store the rooms.
        List<Room> rooms = facade.getActiveRooms();
        // Check whether rooms.isEmpty().
        if (rooms.isEmpty()) {
            // Show a dialog to the user.
            JOptionPane.showMessageDialog(this, "No active rooms available.",
                // Provide the remaining arguments needed to finish show message dialog.
                "No Rooms", JOptionPane.WARNING_MESSAGE);
            // Return return to the caller.
            return;
        }

        // Create and store the panel.
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        // Set border on panel.
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Execute: String[] roomNames = rooms.stream()
        String[] roomNames = rooms.stream()
            // Execute: .map(r -> r.getId() + " - " + r.getName())
            .map(r -> r.getId() + " - " + r.getName())
            // Execute: .toArray(String[]::new);
            .toArray(String[]::new);
        // Calculate and store the room combo box.
        JComboBox<String> roomCombo = UIConstants.createStyledComboBox(roomNames);

        // Calculate and store the date field.
        JTextField dateField = UIConstants.createStyledTextField();
        // Set text on date field.
        dateField.setText(LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        // Start populating the time slots array.
        String[] timeSlots = {
            // Add the next value to the current array literal.
            "08:00", "09:00", "10:00", "11:00", "12:00",
            // Add the next value to the current array literal.
            "13:00", "14:00", "15:00", "16:00", "17:00"
        // Finish the current array literal.
        };
        // Calculate and store the start combo box.
        JComboBox<String> startCombo = UIConstants.createStyledComboBox(timeSlots);
        // Calculate and store the end combo box.
        JComboBox<String> endCombo = UIConstants.createStyledComboBox(timeSlots);
        // Set selected index on end combo box.
        endCombo.setSelectedIndex(1);

        // Calculate and store the purpose field.
        JTextField purposeField = UIConstants.createStyledTextField();

        // Add on panel.
        panel.add(new JLabel("Room:"));
        panel.add(roomCombo);
        panel.add(new JLabel("Date (dd/MM/yyyy):"));
        panel.add(dateField);
        panel.add(new JLabel("Start Time:"));
        panel.add(startCombo);
        panel.add(new JLabel("End Time:"));
        panel.add(endCombo);
        panel.add(new JLabel("Purpose:"));
        panel.add(purposeField);

        // Execute: int result = JOptionPane.showConfirmDialog(this, panel, "Book a Room",
        int result = JOptionPane.showConfirmDialog(this, panel, "Book a Room",
            // Execute: JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // Check whether result is JOptionPane.OK_OPTION.
        if (result == JOptionPane.OK_OPTION) {
            // Start a try block to handle possible exceptions.
            try {
                // Calculate and store the selected room.
                String selectedRoom = (String) roomCombo.getSelectedItem();
                // Calculate and store the room id.
                String roomId = selectedRoom.split(" - ")[0];

                // Execute: LocalDate date = LocalDate.parse(dateField.getText().trim(),
                LocalDate date = LocalDate.parse(dateField.getText().trim(),
                    // Of pattern on date time formatter.
                    DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                // Calculate and store the start time.
                LocalTime startTime = LocalTime.parse((String) startCombo.getSelectedItem());
                // Calculate and store the end time.
                LocalTime endTime = LocalTime.parse((String) endCombo.getSelectedItem());
                // Calculate and store the purpose.
                String purpose = purposeField.getText().trim();

                // Check whether purpose.isEmpty().
                if (purpose.isEmpty()) {
                    // Show a dialog to the user.
                    JOptionPane.showMessageDialog(this, "Please enter a purpose.",
                        // Provide the remaining arguments needed to finish show message dialog.
                        "Validation Error", JOptionPane.WARNING_MESSAGE);
                    // Return return to the caller.
                    return;
                }

                // Book room through the facade.
                facade.bookRoom(roomId, current.getId(), date, startTime, endTime, purpose);
                // Refresh.
                refresh();
                // Show a dialog to the user.
                JOptionPane.showMessageDialog(this, "Room booked successfully!",
                    // Provide the remaining arguments needed to finish show message dialog.
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            // Catch any exception thrown in the preceding try block.
            } catch (DateTimeParseException e) {
                // Show a dialog to the user.
                JOptionPane.showMessageDialog(this,
                    // Provide the next argument for show message dialog.
                    "Invalid date format. Please use dd/MM/yyyy.",
                    // Provide the remaining arguments needed to finish show message dialog.
                    "Date Error", JOptionPane.ERROR_MESSAGE);
            // Catch any exception thrown in the preceding try block.
            } catch (InvalidBookingException e) {
                // Show a dialog to the user.
                JOptionPane.showMessageDialog(this, e.getMessage(),
                    // Provide the remaining arguments needed to finish show message dialog.
                    "Booking Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Define the cancel selected booking method.
    private void cancelSelectedBooking() {
        // Calculate and store the row.
        int row = bookingTable.getSelectedRow();
        // Check whether row < 0.
        if (row < 0) {
            // Show a dialog to the user.
            JOptionPane.showMessageDialog(this, "Please select a booking to cancel.",
                // Provide the remaining arguments needed to finish show message dialog.
                "No Selection", JOptionPane.WARNING_MESSAGE);
            // Return return to the caller.
            return;
        }

        // Calculate and store the booking id.
        String bookingId = (String) tableModel.getValueAt(row, 0);
        // Calculate and store the status.
        String status = (String) tableModel.getValueAt(row, 5);

        // Check whether "Cancelled".equals(status).
        if ("Cancelled".equals(status)) {
            // Show a dialog to the user.
            JOptionPane.showMessageDialog(this, "This booking is already cancelled.",
                // Provide the remaining arguments needed to finish show message dialog.
                "Already Cancelled", JOptionPane.INFORMATION_MESSAGE);
            // Return return to the caller.
            return;
        }

        // Execute: int confirm = JOptionPane.showConfirmDialog(this,
        int confirm = JOptionPane.showConfirmDialog(this,
            // Execute: "Are you sure you want to cancel booking " + bookingId + "?",
            "Are you sure you want to cancel booking " + bookingId + "?",
            // Execute: "Confirm Cancellation", JOptionPane.YES_NO_OPTION);
            "Confirm Cancellation", JOptionPane.YES_NO_OPTION);

        // Check whether confirm is JOptionPane.YES_OPTION.
        if (confirm == JOptionPane.YES_OPTION) {
            // Start a try block to handle possible exceptions.
            try {
                // Calculate and store the current.
                User current = facade.getCurrentUser();
                // Cancel booking through the facade.
                facade.cancelBooking(bookingId, current != null ? current.getId() : "");
                // Refresh.
                refresh();
                // Show a dialog to the user.
                JOptionPane.showMessageDialog(this, "Booking cancelled successfully.",
                    // Provide the remaining arguments needed to finish show message dialog.
                    "Cancelled", JOptionPane.INFORMATION_MESSAGE);
            // Catch any exception thrown in the preceding try block.
            } catch (InvalidBookingException e) {
                // Show a dialog to the user.
                JOptionPane.showMessageDialog(this, e.getMessage(),
                    // Provide the remaining arguments needed to finish show message dialog.
                    "Error", JOptionPane.ERROR_MESSAGE);
            // End the current code block.
            }
        }
    }
}

