// Declare that this file belongs to the com.scms.gui package.
package com.scms.gui;

// Import com.scms.facade.CampusManagementFacade so it can be used in this file.
import com.scms.facade.CampusManagementFacade;
import com.scms.model.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

// Declare the dashboard panel class.
public class DashboardPanel extends JPanel {
    // Declare the facade.
    private CampusManagementFacade facade;
    // Declare the content panel.
    private JPanel contentPanel;

    // Construct a new dashboard panel instance.
    public DashboardPanel(CampusManagementFacade facade) {
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
        // Initialize content panel with a new value.
        contentPanel = new JPanel();
        // Set layout on content panel.
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        // Set background on content panel.
        contentPanel.setBackground(UIConstants.BG_MAIN);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // Create and store the scroll pane.
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        // Set border on scroll pane.
        scrollPane.setBorder(null);
        // Get vertical scroll bar on scroll pane.
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        // Add.
        add(scrollPane, BorderLayout.CENTER);
    }

    // Define the refresh method.
    public void refresh() {
        // Remove all on content panel.
        contentPanel.removeAll();

        // Calculate and store the current user.
        User currentUser = facade.getCurrentUser();
        // Execute: if (currentUser == null) return;
        if (currentUser == null) return;

        // Welcome header
        // Create and store the welcome label.
        JLabel welcomeLabel = new JLabel(currentUser.getDashboardWelcome());
        // Set font on welcome label.
        welcomeLabel.setFont(UIConstants.FONT_TITLE);
        // Set foreground on welcome label.
        welcomeLabel.setForeground(UIConstants.TEXT_PRIMARY);
        // Set alignment X on welcome label.
        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Add on content panel.
        contentPanel.add(welcomeLabel);

        // Create and store the role label.
        JLabel roleLabel = new JLabel("Role: " + currentUser.getRole().getDisplayName());
        // Set font on role label.
        roleLabel.setFont(UIConstants.FONT_BODY);
        // Set foreground on role label.
        roleLabel.setForeground(UIConstants.TEXT_SECONDARY);
        // Set alignment X on role label.
        roleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Add on content panel.
        contentPanel.add(roleLabel);

        // Add on content panel.
        contentPanel.add(Box.createVerticalStrut(25));

        // Statistics cards
        // Create and store the stats row.
        JPanel statsRow = new JPanel(new GridLayout(1, 4, 15, 0));
        // Set background on stats row.
        statsRow.setBackground(UIConstants.BG_MAIN);
        // Set alignment X on stats row.
        statsRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Set maximum size on stats row.
        statsRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        // Add on stats row.
        statsRow.add(UIConstants.createStatCard("Total Rooms",
            // Provide the remaining arguments needed to finish add.
            String.valueOf(facade.getTotalRooms()), UIConstants.ACCENT));
        // Add on stats row.
        statsRow.add(UIConstants.createStatCard("Active Bookings",
            // Provide the remaining arguments needed to finish add.
            String.valueOf(facade.getActiveBookings()), UIConstants.SUCCESS));
        // Add on stats row.
        statsRow.add(UIConstants.createStatCard("Pending Maintenance",
            // Provide the remaining arguments needed to finish add.
            String.valueOf(facade.getPendingMaintenance()), UIConstants.WARNING));
        // Add on stats row.
        statsRow.add(UIConstants.createStatCard("Total Users",
            // Provide the remaining arguments needed to finish add.
            String.valueOf(facade.getTotalUsers()), UIConstants.PRIMARY));

        // Add on content panel.
        contentPanel.add(statsRow);
        contentPanel.add(Box.createVerticalStrut(25));

        // Two-column layout for details
        // Create and store the details row.
        JPanel detailsRow = new JPanel(new GridLayout(1, 2, 15, 0));
        // Set background on details row.
        detailsRow.setBackground(UIConstants.BG_MAIN);
        // Set alignment X on details row.
        detailsRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Set maximum size on details row.
        detailsRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));

        // Recent Bookings card
        // Calculate and store the bookings card.
        JPanel bookingsCard = UIConstants.createCard("Recent Bookings");
        // Create and store the bookings list.
        JPanel bookingsList = new JPanel();
        // Set layout on bookings list.
        bookingsList.setLayout(new BoxLayout(bookingsList, BoxLayout.Y_AXIS));
        // Set background on bookings list.
        bookingsList.setBackground(UIConstants.BG_CARD);

        // Execute: List<Booking> bookings = currentUser.getRole() == UserRole.ADMINISTRATOR
        List<Booking> bookings = currentUser.getRole() == UserRole.ADMINISTRATOR
            // Execute: ? facade.getAllBookings()
            ? facade.getAllBookings()
            // Execute: : facade.getUserBookings(currentUser.getId());
            : facade.getUserBookings(currentUser.getId());

        // Check whether bookings.isEmpty().
        if (bookings.isEmpty()) {
            // Create and store the no data.
            JLabel noData = new JLabel("No bookings found.");
            // Set font on no data.
            noData.setFont(UIConstants.FONT_BODY);
            // Set foreground on no data.
            noData.setForeground(UIConstants.TEXT_SECONDARY);
            // Add on bookings list.
            bookingsList.add(noData);
        // Execute: } else {
        } else {
            // Store the count value.
            int count = 0;
            // Loop using Booking b : bookings.
            for (Booking b : bookings) {
                // Execute: if (count >= 5) break;
                if (count >= 5) break;
                // Calculate and store the room.
                Room room = facade.getRoomById(b.getRoomId());
                // Calculate and store the room name.
                String roomName = room != null ? room.getName() : b.getRoomId();

                // Create and store the item.
                JPanel item = new JPanel(new BorderLayout(5, 0));
                // Set background on item.
                item.setBackground(UIConstants.BG_CARD);
                // Set border on item.
                item.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UIConstants.BORDER_COLOR));
                // Set maximum size on item.
                item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

                // Create and store the info.
                JLabel info = new JLabel(roomName + " - " + b.getDate() + " " + b.getTimeSlot());
                // Set font on info.
                info.setFont(UIConstants.FONT_BODY);
                // Set foreground on info.
                info.setForeground(UIConstants.TEXT_PRIMARY);
                // Add on item.
                item.add(info, BorderLayout.CENTER);

                // Create and store the status label.
                JLabel statusLabel = new JLabel(b.getStatus().getDisplayName());
                // Set font on status label.
                statusLabel.setFont(UIConstants.FONT_SMALL);
                // Set foreground on status label.
                statusLabel.setForeground(b.getStatus() == BookingStatus.CONFIRMED
                    // Provide the remaining arguments needed to finish set foreground.
                    ? UIConstants.SUCCESS : UIConstants.DANGER);
                // Add on item.
                item.add(statusLabel, BorderLayout.EAST);

                // Add on bookings list.
                bookingsList.add(item);
                // Increment count.
                count++;
            }
        // End the current code block.
        }
        // Add on bookings card.
        bookingsCard.add(bookingsList, BorderLayout.CENTER);
        // Add on details row.
        detailsRow.add(bookingsCard);

        // Maintenance Overview card
        // Calculate and store the maintenance card.
        JPanel maintenanceCard = UIConstants.createCard("Maintenance Overview");
        // Create and store the maint list.
        JPanel maintList = new JPanel();
        // Set layout on maint list.
        maintList.setLayout(new BoxLayout(maintList, BoxLayout.Y_AXIS));
        // Set background on maint list.
        maintList.setBackground(UIConstants.BG_CARD);

        // Execute: List<MaintenanceRequest> requests = currentUser.getRole() == UserRole.ADMINISTRATOR
        List<MaintenanceRequest> requests = currentUser.getRole() == UserRole.ADMINISTRATOR
            // Execute: ? facade.getAllMaintenanceRequests()
            ? facade.getAllMaintenanceRequests()
            // Execute: : facade.getUserMaintenanceRequests(currentUser.getId());
            : facade.getUserMaintenanceRequests(currentUser.getId());

        // Check whether requests.isEmpty().
        if (requests.isEmpty()) {
            // Create and store the no data.
            JLabel noData = new JLabel("No maintenance requests.");
            // Set font on no data.
            noData.setFont(UIConstants.FONT_BODY);
            // Set foreground on no data.
            noData.setForeground(UIConstants.TEXT_SECONDARY);
            // Add on maint list.
            maintList.add(noData);
        // Execute: } else {
        } else {
            // Store the count value.
            int count = 0;
            // Loop using MaintenanceRequest mr : requests.
            for (MaintenanceRequest mr : requests) {
                // Execute: if (count >= 5) break;
                if (count >= 5) break;

                // Create and store the item.
                JPanel item = new JPanel(new BorderLayout(5, 0));
                // Set background on item.
                item.setBackground(UIConstants.BG_CARD);
                // Set border on item.
                item.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UIConstants.BORDER_COLOR));
                // Set maximum size on item.
                item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

                // Create and store the info.
                JLabel info = new JLabel("MR-" + mr.getId() + ": " + mr.getDescription());
                // Set font on info.
                info.setFont(UIConstants.FONT_BODY);
                // Set foreground on info.
                info.setForeground(UIConstants.TEXT_PRIMARY);
                // Add on item.
                item.add(info, BorderLayout.CENTER);

                // Create and store the status label.
                JLabel statusLabel = new JLabel(mr.getStatus().getDisplayName());
                // Set font on status label.
                statusLabel.setFont(UIConstants.FONT_SMALL);
                // Define the switch method.
                Color statusColor = switch (mr.getStatus()) {
                    // Handle this specific switch case.
                    case PENDING -> UIConstants.WARNING;
                    // Handle this specific switch case.
                    case ASSIGNED -> UIConstants.ACCENT;
                    // Handle this specific switch case.
                    case COMPLETED -> UIConstants.SUCCESS;
                };
                // Set foreground on status label.
                statusLabel.setForeground(statusColor);
                // Add on item.
                item.add(statusLabel, BorderLayout.EAST);

                // Add on maint list.
                maintList.add(item);
                // Increment count.
                count++;
            }
        }
        // Add on maintenance card.
        maintenanceCard.add(maintList, BorderLayout.CENTER);
        // Add on details row.
        detailsRow.add(maintenanceCard);

        // Add on content panel.
        contentPanel.add(detailsRow);

        // Analytics section for admin
        // Check whether currentUser.getRole() is UserRole.ADMINISTRATOR.
        if (currentUser.getRole() == UserRole.ADMINISTRATOR) {
            // Add on content panel.
            contentPanel.add(Box.createVerticalStrut(25));

            // Create and store the analytics row.
            JPanel analyticsRow = new JPanel(new GridLayout(1, 2, 15, 0));
            // Set background on analytics row.
            analyticsRow.setBackground(UIConstants.BG_MAIN);
            // Set alignment X on analytics row.
            analyticsRow.setAlignmentX(Component.LEFT_ALIGNMENT);
            // Set maximum size on analytics row.
            analyticsRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

            // Most booked rooms
            // Calculate and store the booked card.
            JPanel bookedCard = UIConstants.createCard("Most Booked Rooms");
            // Create and store the booked list.
            JPanel bookedList = new JPanel();
            // Set layout on booked list.
            bookedList.setLayout(new BoxLayout(bookedList, BoxLayout.Y_AXIS));
            // Set background on booked list.
            bookedList.setBackground(UIConstants.BG_CARD);
            // Calculate and store the most booked.
            Map<String, Integer> mostBooked = facade.getMostBookedRooms();
            // Check whether mostBooked.isEmpty().
            if (mostBooked.isEmpty()) {
                // Create and store the no data.
                JLabel noData = new JLabel("No booking data available.");
                // Set font on no data.
                noData.setFont(UIConstants.FONT_BODY);
                // Set foreground on no data.
                noData.setForeground(UIConstants.TEXT_SECONDARY);
                // Add on booked list.
                bookedList.add(noData);
            // Execute: } else {
            } else {
                // Entry set on most booked.
                mostBooked.entrySet().stream()
                    // Provide the next argument for entry set.
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    // Provide the next argument for entry set.
                    .limit(5)
                    // Provide the next argument for entry set.
                    .forEach(entry -> {
                        // Provide the remaining arguments needed to finish entry set.
                        Room room = facade.getRoomById(entry.getKey());
                        // Calculate and store the name.
                        String name = room != null ? room.getName() : entry.getKey();
                        // Create and store the item.
                        JPanel item = new JPanel(new BorderLayout());
                        // Set background on item.
                        item.setBackground(UIConstants.BG_CARD);
                        // Create and store the label.
                        JLabel label = new JLabel(name);
                        // Set font on label.
                        label.setFont(UIConstants.FONT_BODY);
                        // Add on item.
                        item.add(label, BorderLayout.CENTER);
                        // Create and store the count label.
                        JLabel countLabel = new JLabel(entry.getValue() + " bookings");
                        // Set font on count label.
                        countLabel.setFont(UIConstants.FONT_SMALL);
                        // Set foreground on count label.
                        countLabel.setForeground(UIConstants.ACCENT);
                        // Add on item.
                        item.add(countLabel, BorderLayout.EAST);
                        // Add on booked list.
                        bookedList.add(item);
                    });
            }
            // Add on booked card.
            bookedCard.add(bookedList, BorderLayout.CENTER);
            // Add on analytics row.
            analyticsRow.add(bookedCard);

            // Urgency breakdown
            // Calculate and store the urgency card.
            JPanel urgencyCard = UIConstants.createCard("Maintenance by Urgency");
            // Create and store the urgency list.
            JPanel urgencyList = new JPanel();
            // Set layout on urgency list.
            urgencyList.setLayout(new BoxLayout(urgencyList, BoxLayout.Y_AXIS));
            // Set background on urgency list.
            urgencyList.setBackground(UIConstants.BG_CARD);
            // Calculate and store the urgency breakdown.
            Map<Urgency, Integer> urgencyBreakdown = facade.getMaintenanceUrgencyBreakdown();
            // Check whether urgencyBreakdown.isEmpty().
            if (urgencyBreakdown.isEmpty()) {
                // Create and store the no data.
                JLabel noData = new JLabel("No maintenance data.");
                // Set font on no data.
                noData.setFont(UIConstants.FONT_BODY);
                // Set foreground on no data.
                noData.setForeground(UIConstants.TEXT_SECONDARY);
                // Add on urgency list.
                urgencyList.add(noData);
            // Execute: } else {
            } else {
                // Loop using Urgency u : Urgency.values().
                for (Urgency u : Urgency.values()) {
                    // Calculate and store the count.
                    int count = urgencyBreakdown.getOrDefault(u, 0);
                    // Create and store the item.
                    JPanel item = new JPanel(new BorderLayout());
                    // Set background on item.
                    item.setBackground(UIConstants.BG_CARD);
                    // Create and store the label.
                    JLabel label = new JLabel(u.getDisplayName());
                    // Set font on label.
                    label.setFont(UIConstants.FONT_BODY);
                    // Add on item.
                    item.add(label, BorderLayout.CENTER);
                    // Create and store the count label.
                    JLabel countLabel = new JLabel(String.valueOf(count));
                    // Set font on count label.
                    countLabel.setFont(UIConstants.FONT_SMALL);
                    // Define the switch method.
                    Color urgColor = switch (u) {
                        // Handle this specific switch case.
                        case LOW -> UIConstants.SUCCESS;
                        // Handle this specific switch case.
                        case MEDIUM -> UIConstants.ACCENT;
                        // Handle this specific switch case.
                        case HIGH -> UIConstants.WARNING;
                        // Handle this specific switch case.
                        case CRITICAL -> UIConstants.DANGER;
                    // End the anonymous type definition and finish the assignment.
                    };
                    // Set foreground on count label.
                    countLabel.setForeground(urgColor);
                    // Add on item.
                    item.add(countLabel, BorderLayout.EAST);
                    // Add on urgency list.
                    urgencyList.add(item);
                }
            }
            // Add on urgency card.
            urgencyCard.add(urgencyList, BorderLayout.CENTER);
            // Add on analytics row.
            analyticsRow.add(urgencyCard);

            // Add on content panel.
            contentPanel.add(analyticsRow);
        }

        // Revalidate on content panel.
        contentPanel.revalidate();
        // Repaint on content panel.
        contentPanel.repaint();
    }
}

