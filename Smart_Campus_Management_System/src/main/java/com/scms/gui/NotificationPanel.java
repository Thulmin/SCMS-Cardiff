// Declare that this file belongs to the com.scms.gui package.
package com.scms.gui;

// Import com.scms.facade.CampusManagementFacade so it can be used in this file.
import com.scms.facade.CampusManagementFacade;
import com.scms.model.Notification;
import com.scms.model.User;
import javax.swing.*;
import java.awt.*;
import java.util.List;

// Declare the notification panel class.
public class NotificationPanel extends JPanel {
    // Declare the facade.
    private CampusManagementFacade facade;
    // Declare the notification list.
    private JPanel notificationList;
    // Declare the count label.
    private JLabel countLabel;

    // Construct a new notification panel instance.
    public NotificationPanel(CampusManagementFacade facade) {
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
        JLabel title = new JLabel("Notifications");
        // Set font on title.
        title.setFont(UIConstants.FONT_TITLE);
        // Set foreground on title.
        title.setForeground(UIConstants.TEXT_PRIMARY);
        // Add on header panel.
        headerPanel.add(title, BorderLayout.WEST);

        // Create and store the right header.
        JPanel rightHeader = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        // Set background on right header.
        rightHeader.setBackground(UIConstants.BG_MAIN);

        // Initialize count label with a new value.
        countLabel = new JLabel("0 notifications");
        // Set font on count label.
        countLabel.setFont(UIConstants.FONT_BODY);
        // Set foreground on count label.
        countLabel.setForeground(UIConstants.TEXT_SECONDARY);
        // Add on right header.
        rightHeader.add(countLabel);

        // Calculate and store the mark all button.
        JButton markAllBtn = UIConstants.createStyledButton("Mark All Read", UIConstants.ACCENT);
        // Attach an action listener on mark all button.
        markAllBtn.addActionListener(e -> markAllRead());
        // Add on right header.
        rightHeader.add(markAllBtn);

        // Calculate and store the refresh button.
        JButton refreshBtn = UIConstants.createStyledButton("Refresh", UIConstants.PRIMARY);
        // Attach an action listener on refresh button.
        refreshBtn.addActionListener(e -> refresh());
        // Add on right header.
        rightHeader.add(refreshBtn);

        // Add on header panel.
        headerPanel.add(rightHeader, BorderLayout.EAST);
        // Add on main panel.
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Notification list
        // Initialize notification list with a new value.
        notificationList = new JPanel();
        // Set layout on notification list.
        notificationList.setLayout(new BoxLayout(notificationList, BoxLayout.Y_AXIS));
        // Set background on notification list.
        notificationList.setBackground(UIConstants.BG_MAIN);

        // Create and store the scroll pane.
        JScrollPane scrollPane = new JScrollPane(notificationList);
        // Set border on scroll pane.
        scrollPane.setBorder(null);
        // Get vertical scroll bar on scroll pane.
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        // Add on main panel.
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Add.
        add(mainPanel, BorderLayout.CENTER);
    }

    // Define the refresh method.
    public void refresh() {
        // Remove all on notification list.
        notificationList.removeAll();
        // Calculate and store the current.
        User current = facade.getCurrentUser();
        // Execute: if (current == null) return;
        if (current == null) return;

        // Calculate and store the notifications.
        List<Notification> notifications = current.getNotifications();
        // Set text on count label.
        countLabel.setText(notifications.size() + " notification" + (notifications.size() != 1 ? "s" : ""));

        // Check whether notifications.isEmpty().
        if (notifications.isEmpty()) {
            // Create and store the empty panel.
            JPanel emptyPanel = new JPanel(new GridBagLayout());
            // Set background on empty panel.
            emptyPanel.setBackground(UIConstants.BG_MAIN);

            // Create and store the empty label.
            JLabel emptyLabel = new JLabel("No notifications yet");
            // Set font on empty label.
            emptyLabel.setFont(UIConstants.FONT_SUBTITLE);
            // Set foreground on empty label.
            emptyLabel.setForeground(UIConstants.TEXT_SECONDARY);
            // Add on empty panel.
            emptyPanel.add(emptyLabel);

            // Create and store the empty sub label.
            JLabel emptySubLabel = new JLabel("You'll see notifications here when actions occur");
            // Set font on empty sub label.
            emptySubLabel.setFont(UIConstants.FONT_BODY);
            // Set foreground on empty sub label.
            emptySubLabel.setForeground(UIConstants.TEXT_SECONDARY);

            // Create and store the wrapper.
            JPanel wrapper = new JPanel();
            // Set layout on wrapper.
            wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
            // Set background on wrapper.
            wrapper.setBackground(UIConstants.BG_MAIN);
            // Add on wrapper.
            wrapper.add(Box.createVerticalStrut(50));
            // Set alignment X on empty label.
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            // Set alignment X on empty sub label.
            emptySubLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            // Add on wrapper.
            wrapper.add(emptyLabel);
            // Add on wrapper.
            wrapper.add(Box.createVerticalStrut(5));
            // Add on wrapper.
            wrapper.add(emptySubLabel);

            // Add on notification list.
            notificationList.add(wrapper);
        // Execute: } else {
        } else {
            // Show newest first
            // Loop using int i = notifications.size() - 1; i is at least 0; i--.
            for (int i = notifications.size() - 1; i >= 0; i--) {
                // Calculate and store the n.
                Notification n = notifications.get(i);
                // Add on notification list.
                notificationList.add(createNotificationCard(n));
                // Add on notification list.
                notificationList.add(Box.createVerticalStrut(8));
            // End the current code block.
            }
        }

        // Revalidate on notification list.
        notificationList.revalidate();
        // Repaint on notification list.
        notificationList.repaint();
    }

    // Define the create notification card method.
    private JPanel createNotificationCard(Notification notification) {
        // Create and store the card.
        JPanel card = new JPanel(new BorderLayout(10, 5));
        // Set background on card.
        card.setBackground(notification.isRead() ? UIConstants.BG_CARD : new Color(240, 248, 255));
        // Set border on card.
        card.setBorder(BorderFactory.createCompoundBorder(
            // Provide the next argument for set border.
            BorderFactory.createLineBorder(
                // Provide the next argument for set border.
                notification.isRead() ? UIConstants.BORDER_COLOR : UIConstants.ACCENT, 1),
            // Provide the next argument for set border.
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        // Provide the remaining arguments needed to finish set border.
        ));
        // Set maximum size on card.
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        // Left - unread indicator
        // Check whether not notification.isRead().
        if (!notification.isRead()) {
            // Create and store the indicator.
            JPanel indicator = new JPanel();
            // Set background on indicator.
            indicator.setBackground(UIConstants.ACCENT);
            // Set preferred size on indicator.
            indicator.setPreferredSize(new Dimension(4, 0));
            // Add on card.
            card.add(indicator, BorderLayout.WEST);
        }

        // Center - message
        // Create and store the center panel.
        JPanel centerPanel = new JPanel(new BorderLayout(0, 3));
        // Set background on center panel.
        centerPanel.setBackground(card.getBackground());

        // Create and store the message label.
        JLabel messageLabel = new JLabel("<html>" + notification.getMessage() + "</html>");
        // Set font on message label.
        messageLabel.setFont(notification.isRead() ? UIConstants.FONT_BODY
            // Provide the remaining arguments needed to finish set font.
            : new Font("Segoe UI", Font.BOLD, 13));
        // Set foreground on message label.
        messageLabel.setForeground(UIConstants.TEXT_PRIMARY);
        // Add on center panel.
        centerPanel.add(messageLabel, BorderLayout.CENTER);

        // Create and store the time label.
        JLabel timeLabel = new JLabel(notification.getFormattedTimestamp());
        // Set font on time label.
        timeLabel.setFont(UIConstants.FONT_SMALL);
        // Set foreground on time label.
        timeLabel.setForeground(UIConstants.TEXT_SECONDARY);
        // Add on center panel.
        centerPanel.add(timeLabel, BorderLayout.SOUTH);

        // Add on card.
        card.add(centerPanel, BorderLayout.CENTER);

        // Right - mark as read button
        // Check whether not notification.isRead().
        if (!notification.isRead()) {
            // Create and store the read button.
            JButton readBtn = new JButton("Mark Read");
            // Set font on read button.
            readBtn.setFont(UIConstants.FONT_SMALL);
            // Set foreground on read button.
            readBtn.setForeground(UIConstants.ACCENT);
            // Set background on read button.
            readBtn.setBackground(card.getBackground());
            // Set border painted on read button.
            readBtn.setBorderPainted(false);
            // Set focus painted on read button.
            readBtn.setFocusPainted(false);
            // Set cursor on read button.
            readBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            // Attach an action listener on read button.
            readBtn.addActionListener(e -> {
                // Mark as read on notification.
                notification.markAsRead();
                // Refresh.
                refresh();
            });
            // Add on card.
            card.add(readBtn, BorderLayout.EAST);
        }

        // Return card to the caller.
        return card;
    }

    // Define the mark all read method.
    private void markAllRead() {
        // Calculate and store the current.
        User current = facade.getCurrentUser();
        // Execute: if (current == null) return;
        if (current == null) return;

        // Loop using Notification n : current.getNotifications().
        for (Notification n : current.getNotifications()) {
            // Mark as read on n.
            n.markAsRead();
        }
        // Refresh.
        refresh();
    }

    // Define the getter for unread count.
    public int getUnreadCount() {
        // Calculate and store the current.
        User current = facade.getCurrentUser();
        // Execute: if (current == null) return 0;
        if (current == null) return 0;
        // Store the count value.
        int count = 0;
        // Loop using Notification n : current.getNotifications().
        for (Notification n : current.getNotifications()) {
            // Execute: if (!n.isRead()) count++;
            if (!n.isRead()) count++;
        }
        // Return count to the caller.
        return count;
    }
}

