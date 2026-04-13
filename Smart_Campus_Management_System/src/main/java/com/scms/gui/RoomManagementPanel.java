// Declare that this file belongs to the com.scms.gui package.
package com.scms.gui;

// Import com.scms.exception.DuplicateDataException so it can be used in this file.
import com.scms.exception.DuplicateDataException;
import com.scms.facade.CampusManagementFacade;
import com.scms.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// Declare the room management panel class.
public class RoomManagementPanel extends JPanel {
    // Declare the facade.
    private CampusManagementFacade facade;
    // Declare the room table.
    private JTable roomTable;
    // Declare the table model.
    private DefaultTableModel tableModel;
    // Declare the filter combo box.
    private JComboBox<String> filterCombo;

    // Construct a new room management panel instance.
    public RoomManagementPanel(CampusManagementFacade facade) {
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
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // Header
        // Create and store the header panel.
        JPanel headerPanel = new JPanel(new BorderLayout());
        // Set background on header panel.
        headerPanel.setBackground(UIConstants.BG_MAIN);

        // Create and store the title.
        JLabel title = new JLabel("Room Management");
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
            // Execute: "All Rooms", "Active Only", "Lecture Halls", "Computer Labs",
            "All Rooms", "Active Only", "Lecture Halls", "Computer Labs",
            // Execute: "Seminar Rooms", "Meeting Rooms", "Workshops"
            "Seminar Rooms", "Meeting Rooms", "Workshops"
        });
        // Attach an action listener on filter combo box.
        filterCombo.addActionListener(e -> refresh());
        // Add on button panel.
        buttonPanel.add(filterCombo);

        // Calculate and store the current.
        User current = facade.getCurrentUser();
        // Check whether current is not null and current.getRole() is UserRole.ADMINISTRATOR.
        if (current != null && current.getRole() == UserRole.ADMINISTRATOR) {
            // Calculate and store the add button.
            JButton addBtn = UIConstants.createStyledButton("Add Room", UIConstants.SUCCESS);
            // Attach an action listener on add button.
            addBtn.addActionListener(e -> showAddRoomDialog());
            // Add on button panel.
            buttonPanel.add(addBtn);
        }

        // Add on header panel.
        headerPanel.add(buttonPanel, BorderLayout.EAST);
        // Add on main panel.
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Table
        // Calculate and store the columns.
        String[] columns = {"ID", "Name", "Type", "Capacity", "Equipment", "Status"};
        // Create and customize the table model.
        tableModel = new DefaultTableModel(columns, 0) {
            // Apply the Override annotation to the next declaration.
            @Override
            // Define the check for whether cell editable.
            public boolean isCellEditable(int row, int column) { return false; }
        };
        // Set room table using the computed value.
        roomTable = UIConstants.createStyledTable();
        // Set model on room table.
        roomTable.setModel(tableModel);

        // Create and store the scroll pane.
        JScrollPane scrollPane = new JScrollPane(roomTable);
        // Set border on scroll pane.
        scrollPane.setBorder(BorderFactory.createLineBorder(UIConstants.BORDER_COLOR));
        // Add on main panel.
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Action buttons at bottom for admin
        // Check whether current is not null and current.getRole() is UserRole.ADMINISTRATOR.
        if (current != null && current.getRole() == UserRole.ADMINISTRATOR) {
            // Create and store the action panel.
            JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
            // Set background on action panel.
            actionPanel.setBackground(UIConstants.BG_MAIN);

            // Calculate and store the edit button.
            JButton editBtn = UIConstants.createStyledButton("Edit Room", UIConstants.ACCENT);
            // Attach an action listener on edit button.
            editBtn.addActionListener(e -> showEditRoomDialog());
            // Add on action panel.
            actionPanel.add(editBtn);

            // Calculate and store the toggle button.
            JButton toggleBtn = UIConstants.createStyledButton("Toggle Status", UIConstants.WARNING);
            // Attach an action listener on toggle button.
            toggleBtn.addActionListener(e -> toggleRoomStatus());
            // Add on action panel.
            actionPanel.add(toggleBtn);

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
        // Calculate and store the filter.
        String filter = filterCombo != null ? (String) filterCombo.getSelectedItem() : "All Rooms";
        // Declare the rooms.
        List<Room> rooms;

        // Switch on filter to choose the matching branch.
        switch (filter) {
            // Handle this specific switch case.
            case "Active Only" -> rooms = facade.getActiveRooms();
            // Handle this specific switch case.
            case "Lecture Halls" -> rooms = facade.getRoomsByType(RoomType.LECTURE_HALL);
            // Handle this specific switch case.
            case "Computer Labs" -> rooms = facade.getRoomsByType(RoomType.COMPUTER_LAB);
            // Handle this specific switch case.
            case "Seminar Rooms" -> rooms = facade.getRoomsByType(RoomType.SEMINAR_ROOM);
            // Handle this specific switch case.
            case "Meeting Rooms" -> rooms = facade.getRoomsByType(RoomType.MEETING_ROOM);
            // Handle this specific switch case.
            case "Workshops" -> rooms = facade.getRoomsByType(RoomType.WORKSHOP);
            // Execute: default -> rooms = facade.getAllRooms();
            default -> rooms = facade.getAllRooms();
        }

        // Loop using Room room : rooms.
        for (Room room : rooms) {
            // Add row on table model.
            tableModel.addRow(new Object[]{
                // Add the next value to the current array literal.
                room.getId(),
                room.getName(),
                room.getType().getDisplayName(),
                room.getCapacity(),
                room.getEquipmentString(),
                room.isActive() ? "Active" : "Inactive"
            });
        }
    }

    // Define the show add room dialog method.
    private void showAddRoomDialog() {
        // Create and store the panel.
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        // Set border on panel.
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Calculate and store the id field.
        JTextField idField = UIConstants.createStyledTextField();
        // Calculate and store the name field.
        JTextField nameField = UIConstants.createStyledTextField();
        // Calculate and store the capacity field.
        JTextField capacityField = UIConstants.createStyledTextField();
        // Calculate and store the type combo box.
        JComboBox<RoomType> typeCombo = UIConstants.createStyledComboBox(RoomType.values());

        // Add on panel.
        panel.add(new JLabel("Room ID:"));
        panel.add(idField);
        panel.add(new JLabel("Room Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Capacity:"));
        panel.add(capacityField);
        panel.add(new JLabel("Room Type:"));
        panel.add(typeCombo);

        // Execute: int result = JOptionPane.showConfirmDialog(this, panel, "Add New Room",
        int result = JOptionPane.showConfirmDialog(this, panel, "Add New Room",
            // Execute: JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // Check whether result is JOptionPane.OK_OPTION.
        if (result == JOptionPane.OK_OPTION) {
            // Start a try block to handle possible exceptions.
            try {
                // Calculate and store the id.
                String id = idField.getText().trim();
                // Calculate and store the name.
                String name = nameField.getText().trim();
                // Calculate and store the capacity.
                int capacity = Integer.parseInt(capacityField.getText().trim());
                // Calculate and store the type.
                RoomType type = (RoomType) typeCombo.getSelectedItem();

                // Check whether id.isEmpty() or name.isEmpty().
                if (id.isEmpty() || name.isEmpty()) {
                    // Show a dialog to the user.
                    JOptionPane.showMessageDialog(this, "Please fill in all fields.",
                        // Provide the remaining arguments needed to finish show message dialog.
                        "Validation Error", JOptionPane.WARNING_MESSAGE);
                    // Return return to the caller.
                    return;
                }

                // Add room through the facade.
                facade.addRoom(id, name, capacity, type);
                // Refresh.
                refresh();
                // Show a dialog to the user.
                JOptionPane.showMessageDialog(this, "Room added successfully!",
                    // Provide the remaining arguments needed to finish show message dialog.
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            // Catch any exception thrown in the preceding try block.
            } catch (NumberFormatException e) {
                // Show a dialog to the user.
                JOptionPane.showMessageDialog(this, "Invalid capacity. Please enter a number.",
                    // Provide the remaining arguments needed to finish show message dialog.
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            // Catch any exception thrown in the preceding try block.
            } catch (DuplicateDataException e) {
                // Show a dialog to the user.
                JOptionPane.showMessageDialog(this, e.getMessage(),
                    // Provide the remaining arguments needed to finish show message dialog.
                    "Duplicate Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Define the show edit room dialog method.
    private void showEditRoomDialog() {
        // Calculate and store the row.
        int row = roomTable.getSelectedRow();
        // Check whether row < 0.
        if (row < 0) {
            // Show a dialog to the user.
            JOptionPane.showMessageDialog(this, "Please select a room to edit.",
                // Provide the remaining arguments needed to finish show message dialog.
                "No Selection", JOptionPane.WARNING_MESSAGE);
            // Return return to the caller.
            return;
        }

        // Calculate and store the id.
        String id = (String) tableModel.getValueAt(row, 0);
        // Calculate and store the room.
        Room room = facade.getRoomById(id);
        // Execute: if (room == null) return;
        if (room == null) return;

        // Create and store the panel.
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        // Set border on panel.
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Calculate and store the name field.
        JTextField nameField = UIConstants.createStyledTextField();
        // Set text on name field.
        nameField.setText(room.getName());
        // Calculate and store the capacity field.
        JTextField capacityField = UIConstants.createStyledTextField();
        // Set text on capacity field.
        capacityField.setText(String.valueOf(room.getCapacity()));
        // Calculate and store the type combo box.
        JComboBox<RoomType> typeCombo = UIConstants.createStyledComboBox(RoomType.values());
        // Set selected item on type combo box.
        typeCombo.setSelectedItem(room.getType());

        // Add on panel.
        panel.add(new JLabel("Room Name:"));
        // Add on panel.
        panel.add(nameField);
        panel.add(new JLabel("Capacity:"));
        panel.add(capacityField);
        panel.add(new JLabel("Room Type:"));
        panel.add(typeCombo);

        // Execute: int result = JOptionPane.showConfirmDialog(this, panel, "Edit Room: " + id,
        int result = JOptionPane.showConfirmDialog(this, panel, "Edit Room: " + id,
            // Execute: JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // Check whether result is JOptionPane.OK_OPTION.
        if (result == JOptionPane.OK_OPTION) {
            // Start a try block to handle possible exceptions.
            try {
                // Calculate and store the name.
                String name = nameField.getText().trim();
                // Calculate and store the capacity.
                int capacity = Integer.parseInt(capacityField.getText().trim());
                // Calculate and store the type.
                RoomType type = (RoomType) typeCombo.getSelectedItem();
                // Update room through the facade.
                facade.updateRoom(id, name, capacity, type);
                // Refresh.
                refresh();
                // Show a dialog to the user.
                JOptionPane.showMessageDialog(this, "Room updated successfully!",
                    // Provide the remaining arguments needed to finish show message dialog.
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            // Catch any exception thrown in the preceding try block.
            } catch (NumberFormatException e) {
                // Show a dialog to the user.
                JOptionPane.showMessageDialog(this, "Invalid capacity.",
                    // Provide the remaining arguments needed to finish show message dialog.
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Define the toggle room status method.
    private void toggleRoomStatus() {
        // Calculate and store the row.
        int row = roomTable.getSelectedRow();
        // Check whether row < 0.
        if (row < 0) {
            // Show a dialog to the user.
            JOptionPane.showMessageDialog(this, "Please select a room.",
                // Provide the remaining arguments needed to finish show message dialog.
                "No Selection", JOptionPane.WARNING_MESSAGE);
            // Return return to the caller.
            return;
        }

        // Calculate and store the id.
        String id = (String) tableModel.getValueAt(row, 0);
        // Calculate and store the status.
        String status = (String) tableModel.getValueAt(row, 5);

        // Check whether "Active".equals(status).
        if ("Active".equals(status)) {
            // Deactivate room through the facade.
            facade.deactivateRoom(id);
        // Execute: } else {
        } else {
            // Activate room through the facade.
            facade.activateRoom(id);
        // End the current code block.
        }
        // Refresh.
        refresh();
    }
}

