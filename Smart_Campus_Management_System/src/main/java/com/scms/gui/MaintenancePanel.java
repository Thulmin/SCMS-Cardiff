// Declare that this file belongs to the com.scms.gui package.
package com.scms.gui;

// Import com.scms.exception.UnauthorizedActionException so it can be used in this file.
import com.scms.exception.UnauthorizedActionException;
import com.scms.facade.CampusManagementFacade;
import com.scms.model.*;
import javax.swing.*;
// Import javax.swing.table.DefaultTableModel so it can be used in this file.
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// Declare the maintenance panel class.
public class MaintenancePanel extends JPanel {
    // Declare the facade.
    private CampusManagementFacade facade;
    // Declare the request table.
    private JTable requestTable;
    // Declare the table model.
    private DefaultTableModel tableModel;
    // Declare the filter combo box.
    private JComboBox<String> filterCombo;

    // Construct a new maintenance panel instance.
    public MaintenancePanel(CampusManagementFacade facade) {
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
        JLabel title = new JLabel("Maintenance Requests");
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

        // Execute: filterCombo = UIConstants.createStyledComboBox(new String[]{
        filterCombo = UIConstants.createStyledComboBox(new String[]{
            // Execute: "All Requests", "Pending", "Assigned", "Completed"
            "All Requests", "Pending", "Assigned", "Completed"
        // End the lambda block and complete the surrounding call.
        });
        // Attach an action listener on filter combo box.
        filterCombo.addActionListener(e -> refresh());
        // Add on button panel.
        buttonPanel.add(filterCombo);

        // Calculate and store the report button.
        JButton reportBtn = UIConstants.createStyledButton("Report Issue", UIConstants.WARNING);
        // Attach an action listener on report button.
        reportBtn.addActionListener(e -> showReportDialog());
        // Add on button panel.
        buttonPanel.add(reportBtn);

        // Add on header panel.
        headerPanel.add(buttonPanel, BorderLayout.EAST);
        // Add on main panel.
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Stats bar
        // Create and store the stats bar.
        JPanel statsBar = new JPanel(new GridLayout(1, 3, 10, 0));
        // Set background on stats bar.
        statsBar.setBackground(UIConstants.BG_MAIN);
        // Set maximum size on stats bar.
        statsBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        // Add on stats bar.
        statsBar.add(UIConstants.createStatCard("Pending",
            // Provide the remaining arguments needed to finish add.
            String.valueOf(facade.getPendingMaintenance()), UIConstants.WARNING));
        // Add on stats bar.
        statsBar.add(UIConstants.createStatCard("Assigned",
            // Provide the remaining arguments needed to finish add.
            String.valueOf(facade.getAssignedMaintenance()), UIConstants.ACCENT));
        // Add on stats bar.
        statsBar.add(UIConstants.createStatCard("Completed",
            // Provide the remaining arguments needed to finish add.
            String.valueOf(facade.getCompletedMaintenance()), UIConstants.SUCCESS));

        // Create and store the center panel.
        JPanel centerPanel = new JPanel(new BorderLayout(0, 15));
        // Set background on center panel.
        centerPanel.setBackground(UIConstants.BG_MAIN);
        // Add on center panel.
        centerPanel.add(statsBar, BorderLayout.NORTH);

        // Table
        // Calculate and store the columns.
        String[] columns = {"Request ID", "Room", "Description", "Urgency", "Status", "Reported By", "Assigned To"};
        // Create and customize the table model.
        tableModel = new DefaultTableModel(columns, 0) {
            // Apply the Override annotation to the next declaration.
            @Override
            // Define the check for whether cell editable.
            public boolean isCellEditable(int row, int column) { return false; }
        };
        // Set request table using the computed value.
        requestTable = UIConstants.createStyledTable();
        // Set model on request table.
        requestTable.setModel(tableModel);

        // Create and store the scroll pane.
        JScrollPane scrollPane = new JScrollPane(requestTable);
        // Set border on scroll pane.
        scrollPane.setBorder(BorderFactory.createLineBorder(UIConstants.BORDER_COLOR));
        // Add on center panel.
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Add on main panel.
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Admin actions
        // Calculate and store the current.
        User current = facade.getCurrentUser();
        // Check whether current is not null and current.getRole() is UserRole.ADMINISTRATOR.
        if (current != null && current.getRole() == UserRole.ADMINISTRATOR) {
            // Create and store the action panel.
            JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
            // Set background on action panel.
            actionPanel.setBackground(UIConstants.BG_MAIN);

            // Calculate and store the assign button.
            JButton assignBtn = UIConstants.createStyledButton("Assign Task", UIConstants.ACCENT);
            // Attach an action listener on assign button.
            assignBtn.addActionListener(e -> showAssignDialog());
            // Add on action panel.
            actionPanel.add(assignBtn);

            // Calculate and store the complete button.
            JButton completeBtn = UIConstants.createStyledButton("Mark Complete", UIConstants.SUCCESS);
            // Attach an action listener on complete button.
            completeBtn.addActionListener(e -> markComplete());
            // Add on action panel.
            actionPanel.add(completeBtn);

            // Add on main panel.
            mainPanel.add(actionPanel, BorderLayout.SOUTH);
        }

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

        // Calculate and store the filter.
        String filter = filterCombo != null ? (String) filterCombo.getSelectedItem() : "All Requests";

        // Declare the requests.
        List<MaintenanceRequest> requests;
        // Check whether current.getRole() is UserRole.ADMINISTRATOR.
        if (current.getRole() == UserRole.ADMINISTRATOR) {
            // Define the switch method.
            requests = switch (filter) {
                // Handle this specific switch case.
                case "Pending" -> facade.getMaintenanceByStatus(RequestStatus.PENDING);
                // Handle this specific switch case.
                case "Assigned" -> facade.getMaintenanceByStatus(RequestStatus.ASSIGNED);
                // Handle this specific switch case.
                case "Completed" -> facade.getMaintenanceByStatus(RequestStatus.COMPLETED);
                // Execute: default -> facade.getAllMaintenanceRequests();
                default -> facade.getAllMaintenanceRequests();
            };
        // Execute: } else {
        } else {
            // Set requests using the computed value.
            requests = facade.getUserMaintenanceRequests(current.getId());
        }

        // Loop using MaintenanceRequest mr : requests.
        for (MaintenanceRequest mr : requests) {
            // Calculate and store the room.
            Room room = facade.getRoomById(mr.getRoomId());
            // Calculate and store the room name.
            String roomName = room != null ? room.getName() : mr.getRoomId();
            // Calculate and store the reporter.
            User reporter = facade.getUserById(mr.getReporterId());
            // Calculate and store the reporter name.
            String reporterName = reporter != null ? reporter.getName() : mr.getReporterId();
            // Calculate and store the assignee.
            User assignee = mr.getAssigneeId() != null ? facade.getUserById(mr.getAssigneeId()) : null;
            // Calculate and store the assignee name.
            String assigneeName = assignee != null ? assignee.getName() : "Unassigned";

            // Add row on table model.
            tableModel.addRow(new Object[]{
                // Add the next value to the current array literal.
                "MR-" + mr.getId(),
                roomName,
                mr.getDescription(),
                // Add the next value to the current array literal.
                mr.getUrgency().getDisplayName(),
                // Add the next value to the current array literal.
                mr.getStatus().getDisplayName(),
                reporterName,
                assigneeName
            // Finish the current array literal.
            });
        }
    }

    // Define the show report dialog method.
    private void showReportDialog() {
        // Calculate and store the current.
        User current = facade.getCurrentUser();
        // Execute: if (current == null) return;
        if (current == null) return;

        // Calculate and store the rooms.
        List<Room> rooms = facade.getAllRooms();
        // Check whether rooms.isEmpty().
        if (rooms.isEmpty()) {
            // Show a dialog to the user.
            JOptionPane.showMessageDialog(this, "No rooms available.",
                // Provide the remaining arguments needed to finish show message dialog.
                "Error", JOptionPane.WARNING_MESSAGE);
            // Return return to the caller.
            return;
        }

        // Create and store the panel.
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
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

        // Create and store the desc area.
        JTextArea descArea = new JTextArea(3, 20);
        // Set font on desc area.
        descArea.setFont(UIConstants.FONT_BODY);
        // Set line wrap on desc area.
        descArea.setLineWrap(true);
        // Set wrap style word on desc area.
        descArea.setWrapStyleWord(true);
        // Set border on desc area.
        descArea.setBorder(BorderFactory.createCompoundBorder(
            // Provide the next argument for set border.
            BorderFactory.createLineBorder(UIConstants.BORDER_COLOR),
            // Provide the next argument for set border.
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        // Provide the remaining arguments needed to finish set border.
        ));

        // Calculate and store the urgency combo box.
        JComboBox<Urgency> urgencyCombo = UIConstants.createStyledComboBox(Urgency.values());

        // Add on panel.
        panel.add(new JLabel("Room:"));
        panel.add(roomCombo);
        panel.add(new JLabel("Description:"));
        panel.add(new JScrollPane(descArea));
        panel.add(new JLabel("Urgency:"));
        panel.add(urgencyCombo);

        // Execute: int result = JOptionPane.showConfirmDialog(this, panel, "Report Maintenance Issue",
        int result = JOptionPane.showConfirmDialog(this, panel, "Report Maintenance Issue",
            // Execute: JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // Check whether result is JOptionPane.OK_OPTION.
        if (result == JOptionPane.OK_OPTION) {
            // Calculate and store the selected room.
            String selectedRoom = (String) roomCombo.getSelectedItem();
            // Calculate and store the room id.
            String roomId = selectedRoom.split(" - ")[0];
            // Calculate and store the description.
            String description = descArea.getText().trim();
            // Calculate and store the urgency.
            Urgency urgency = (Urgency) urgencyCombo.getSelectedItem();

            // Check whether description.isEmpty().
            if (description.isEmpty()) {
                // Show a dialog to the user.
                JOptionPane.showMessageDialog(this, "Please enter a description.",
                    // Provide the remaining arguments needed to finish show message dialog.
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                // Return return to the caller.
                return;
            }

            // Report maintenance through the facade.
            facade.reportMaintenance(roomId, current.getId(), description, urgency);
            // Refresh.
            refresh();
            // Show a dialog to the user.
            JOptionPane.showMessageDialog(this, "Maintenance request submitted!",
                // Provide the remaining arguments needed to finish show message dialog.
                "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Define the show assign dialog method.
    private void showAssignDialog() {
        // Calculate and store the row.
        int row = requestTable.getSelectedRow();
        // Check whether row < 0.
        if (row < 0) {
            // Show a dialog to the user.
            JOptionPane.showMessageDialog(this, "Please select a request to assign.",
                // Provide the remaining arguments needed to finish show message dialog.
                "No Selection", JOptionPane.WARNING_MESSAGE);
            // Return return to the caller.
            return;
        }

        // Calculate and store the request id.
        String requestId = ((String) tableModel.getValueAt(row, 0)).replace("MR-", "");

        // Calculate and store the staff list.
        List<User> staffList = facade.getAllUsers();
        // Check whether staffList.isEmpty().
        if (staffList.isEmpty()) {
            // Show a dialog to the user.
            JOptionPane.showMessageDialog(this, "No staff available.",
                // Provide the remaining arguments needed to finish show message dialog.
                "Error", JOptionPane.WARNING_MESSAGE);
            // Return return to the caller.
            return;
        }

        // Execute: String[] staffNames = staffList.stream()
        String[] staffNames = staffList.stream()
            // Execute: .map(u -> u.getId() + " - " + u.getName() + " (" + u.getRole().getDisplayName() + ")")
            .map(u -> u.getId() + " - " + u.getName() + " (" + u.getRole().getDisplayName() + ")")
            // Execute: .toArray(String[]::new);
            .toArray(String[]::new);
        // Calculate and store the staff combo box.
        JComboBox<String> staffCombo = UIConstants.createStyledComboBox(staffNames);

        // Create and store the panel.
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        // Set border on panel.
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // Add on panel.
        panel.add(new JLabel("Assign to:"), BorderLayout.NORTH);
        // Add on panel.
        panel.add(staffCombo, BorderLayout.CENTER);

        // Execute: int result = JOptionPane.showConfirmDialog(this, panel, "Assign Maintenance Task",
        int result = JOptionPane.showConfirmDialog(this, panel, "Assign Maintenance Task",
            // Execute: JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // Check whether result is JOptionPane.OK_OPTION.
        if (result == JOptionPane.OK_OPTION) {
            // Start a try block to handle possible exceptions.
            try {
                // Calculate and store the selected.
                String selected = (String) staffCombo.getSelectedItem();
                // Calculate and store the assignee id.
                String assigneeId = selected.split(" - ")[0];
                // Assign maintenance through the facade.
                facade.assignMaintenance(requestId, assigneeId);
                // Refresh.
                refresh();
                // Show a dialog to the user.
                JOptionPane.showMessageDialog(this, "Task assigned successfully!",
                    // Provide the remaining arguments needed to finish show message dialog.
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            // Catch any exception thrown in the preceding try block.
            } catch (UnauthorizedActionException e) {
                // Show a dialog to the user.
                JOptionPane.showMessageDialog(this, e.getMessage(),
                    // Provide the remaining arguments needed to finish show message dialog.
                    "Unauthorized", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Define the mark complete method.
    private void markComplete() {
        // Calculate and store the row.
        int row = requestTable.getSelectedRow();
        // Check whether row < 0.
        if (row < 0) {
            // Show a dialog to the user.
            JOptionPane.showMessageDialog(this, "Please select a request to complete.",
                // Provide the remaining arguments needed to finish show message dialog.
                "No Selection", JOptionPane.WARNING_MESSAGE);
            // Return return to the caller.
            return;
        }

        // Calculate and store the request id.
        String requestId = ((String) tableModel.getValueAt(row, 0)).replace("MR-", "");
        // Calculate and store the status.
        String status = (String) tableModel.getValueAt(row, 4);

        // Check whether "Completed".equals(status).
        if ("Completed".equals(status)) {
            // Show a dialog to the user.
            JOptionPane.showMessageDialog(this, "This request is already completed.",
                // Provide the remaining arguments needed to finish show message dialog.
                "Already Completed", JOptionPane.INFORMATION_MESSAGE);
            // Return return to the caller.
            return;
        }

        // Start a try block to handle possible exceptions.
        try {
            // Complete maintenance through the facade.
            facade.completeMaintenance(requestId);
            // Refresh.
            refresh();
            // Show a dialog to the user.
            JOptionPane.showMessageDialog(this, "Request marked as completed!",
                // Provide the remaining arguments needed to finish show message dialog.
                "Success", JOptionPane.INFORMATION_MESSAGE);
        // Catch any exception thrown in the preceding try block.
        } catch (UnauthorizedActionException e) {
            // Show a dialog to the user.
            JOptionPane.showMessageDialog(this, e.getMessage(),
                // Provide the remaining arguments needed to finish show message dialog.
                "Unauthorized", JOptionPane.ERROR_MESSAGE);
        }
    }
}

