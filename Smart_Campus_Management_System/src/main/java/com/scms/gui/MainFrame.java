// Declare that this file belongs to the com.scms.gui package.
package com.scms.gui;

// Import com.scms.facade.CampusManagementFacade so it can be used in this file.
import com.scms.facade.CampusManagementFacade;
import com.scms.model.User;
import com.scms.model.UserRole;
import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

// Declare the main frame class.
public class MainFrame extends JFrame {
    // Declare the facade.
    private CampusManagementFacade facade;
    // Declare the card layout.
    private CardLayout cardLayout;
    // Declare the main content.
    private JPanel mainContent;
    // Declare the sidebar panel.
    private JPanel sidebarPanel;
    // Declare the login panel.
    private LoginPanel loginPanel;
    // Declare the dashboard panel.
    private DashboardPanel dashboardPanel;
    // Declare the room panel.
    private RoomManagementPanel roomPanel;
    // Declare the booking panel.
    private BookingPanel bookingPanel;
    // Declare the maintenance panel.
    private MaintenancePanel maintenancePanel;
    // Declare the notification panel.
    private NotificationPanel notificationPanel;
    // Declare the user info label.
    private JLabel userInfoLabel;
    // Declare the role info label.
    private JLabel roleInfoLabel;
    // Declare the nav buttons.
    private Map<String, JButton> navButtons;
    // Store the active nav value.
    private String activeNav = "Dashboard";

    // Construct a new main frame instance.
    public MainFrame(CampusManagementFacade facade) {
        // Set facade using the computed value.
        this.facade = facade;
        // Initialize nav buttons with a new value.
        this.navButtons = new LinkedHashMap<>();
        // Set title.
        setTitle("Smart Campus Management System - Cardiff Metropolitan University");
        // Set default close operation.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set minimum size.
        setMinimumSize(new Dimension(1200, 750));
        // Set location relative to.
        setLocationRelativeTo(null);
        // Init components.
        initComponents();
    }

    // Define the init components method.
    private void initComponents() {
        // Initialize card layout with a new value.
        cardLayout = new CardLayout();
        // Initialize main content with a new value.
        mainContent = new JPanel(cardLayout);

        // Login panel
        // Initialize login panel with a new value.
        loginPanel = new LoginPanel(this, facade);
        // Add on main content.
        mainContent.add(loginPanel, "LOGIN");

        // App panel (sidebar + content)
        // Create and store the app panel.
        JPanel appPanel = new JPanel(new BorderLayout());
        // Set sidebar panel using the computed value.
        sidebarPanel = createSidebar();
        // Add on app panel.
        appPanel.add(sidebarPanel, BorderLayout.WEST);

        // Create and store the content area.
        JPanel contentArea = new JPanel(new CardLayout());
        // Initialize dashboard panel with a new value.
        dashboardPanel = new DashboardPanel(facade);
        // Initialize room panel with a new value.
        roomPanel = new RoomManagementPanel(facade);
        // Initialize booking panel with a new value.
        bookingPanel = new BookingPanel(facade);
        // Initialize maintenance panel with a new value.
        maintenancePanel = new MaintenancePanel(facade);
        // Initialize notification panel with a new value.
        notificationPanel = new NotificationPanel(facade);

        // Add on content area.
        contentArea.add(dashboardPanel, "Dashboard");
        // Add on content area.
        contentArea.add(roomPanel, "Rooms");
        // Add on content area.
        contentArea.add(bookingPanel, "Bookings");
        // Add on content area.
        contentArea.add(maintenancePanel, "Maintenance");
        contentArea.add(notificationPanel, "Notifications");

        // Add on app panel.
        appPanel.add(contentArea, BorderLayout.CENTER);
        // Add on main content.
        mainContent.add(appPanel, "APP");

        // Set content pane.
        setContentPane(mainContent);
        // Show on card layout.
        cardLayout.show(mainContent, "LOGIN");
    }

    // Define the create sidebar method.
    private JPanel createSidebar() {
        // Create and store the sidebar.
        JPanel sidebar = new JPanel();
        // Set layout on sidebar.
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        // Set background on sidebar.
        sidebar.setBackground(UIConstants.SIDEBAR_BG);
        // Set preferred size on sidebar.
        sidebar.setPreferredSize(new Dimension(UIConstants.SIDEBAR_WIDTH, 0));
        // Set border on sidebar.
        sidebar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // Logo area
        // Create and store the logo panel.
        JPanel logoPanel = new JPanel();
        // Set layout on logo panel.
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
        // Set background on logo panel.
        logoPanel.setBackground(new Color(15, 30, 65));
        // Set border on logo panel.
        logoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        // Set maximum size on logo panel.
        logoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        // Create and store the logo text.
        JLabel logoText = new JLabel("SCMS");
        // Set font on logo text.
        logoText.setFont(new Font("Segoe UI", Font.BOLD, 24));
        // Set foreground on logo text.
        logoText.setForeground(Color.WHITE);
        // Set alignment X on logo text.
        logoText.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Add on logo panel.
        logoPanel.add(logoText);

        // Create and store the logo sub text.
        JLabel logoSubText = new JLabel("Smart Campus System");
        // Set font on logo sub text.
        logoSubText.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        // Set foreground on logo sub text.
        logoSubText.setForeground(UIConstants.SIDEBAR_TEXT);
        // Set alignment X on logo sub text.
        logoSubText.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Add on logo panel.
        logoPanel.add(logoSubText);

        // Add on sidebar.
        sidebar.add(logoPanel);

        // User info
        // Create and store the user panel.
        JPanel userPanel = new JPanel();
        // Set layout on user panel.
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        // Set background on user panel.
        userPanel.setBackground(UIConstants.SIDEBAR_BG);
        // Set border on user panel.
        userPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        // Set maximum size on user panel.
        userPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

        // Initialize user info label with a new value.
        userInfoLabel = new JLabel("Not logged in");
        // Set font on user info label.
        userInfoLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        // Set foreground on user info label.
        userInfoLabel.setForeground(Color.WHITE);
        // Set alignment X on user info label.
        userInfoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Add on user panel.
        userPanel.add(userInfoLabel);

        // Initialize role info label with a new value.
        roleInfoLabel = new JLabel("");
        // Set font on role info label.
        roleInfoLabel.setFont(UIConstants.FONT_SMALL);
        // Set foreground on role info label.
        roleInfoLabel.setForeground(UIConstants.SIDEBAR_TEXT);
        // Set alignment X on role info label.
        roleInfoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Add on user panel.
        userPanel.add(roleInfoLabel);

        // Add on sidebar.
        sidebar.add(userPanel);

        // Divider
        // Create and store the sep.
        JSeparator sep = new JSeparator();
        // Set foreground on sep.
        sep.setForeground(new Color(60, 80, 120));
        // Set maximum size on sep.
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        // Add on sidebar.
        sidebar.add(sep);

        // Add on sidebar.
        sidebar.add(Box.createVerticalStrut(10));

        // Navigation section label
        // Create and store the nav label.
        JLabel navLabel = new JLabel("  NAVIGATION");
        // Set font on nav label.
        navLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
        // Set foreground on nav label.
        navLabel.setForeground(new Color(100, 120, 160));
        // Set alignment X on nav label.
        navLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Set border on nav label.
        navLabel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 0));
        // Add on sidebar.
        sidebar.add(navLabel);

        // Add on sidebar.
        sidebar.add(Box.createVerticalStrut(5));

        // Nav buttons
        // Add nav button.
        addNavButton(sidebar, "Dashboard", "\u25A0");
        // Add nav button.
        addNavButton(sidebar, "Rooms", "\u25A0");
        // Add nav button.
        addNavButton(sidebar, "Bookings", "\u25A0");
        // Add nav button.
        addNavButton(sidebar, "Maintenance", "\u25A0");
        // Add nav button.
        addNavButton(sidebar, "Notifications", "\u25A0");

        // Add on sidebar.
        sidebar.add(Box.createVerticalGlue());

        // Logout button
        // Create and store the logout button.
        JButton logoutBtn = new JButton("    Sign Out");
        // Set font on logout button.
        logoutBtn.setFont(UIConstants.FONT_SIDEBAR);
        // Set foreground on logout button.
        logoutBtn.setForeground(new Color(230, 130, 130));
        // Set background on logout button.
        logoutBtn.setBackground(UIConstants.SIDEBAR_BG);
        // Set border painted on logout button.
        logoutBtn.setBorderPainted(false);
        // Set focus painted on logout button.
        logoutBtn.setFocusPainted(false);
        // Set opaque on logout button.
        logoutBtn.setOpaque(true);
        // Set horizontal alignment on logout button.
        logoutBtn.setHorizontalAlignment(SwingConstants.LEFT);
        // Set maximum size on logout button.
        logoutBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        // Set cursor on logout button.
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Attach an action listener on logout button.
        logoutBtn.addActionListener(e -> handleLogout());
        // Add mouse listener on logout button.
        logoutBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            // Define the mouse entered method.
            public void mouseEntered(java.awt.event.MouseEvent e) {
                // Set background on logout button.
                logoutBtn.setBackground(new Color(80, 30, 30));
            // End the current code block.
            }
            // Define the mouse exited method.
            public void mouseExited(java.awt.event.MouseEvent e) {
                // Set background on logout button.
                logoutBtn.setBackground(UIConstants.SIDEBAR_BG);
            // End the current code block.
            }
        });
        // Add on sidebar.
        sidebar.add(logoutBtn);
        // Add on sidebar.
        sidebar.add(Box.createVerticalStrut(15));

        // Return sidebar to the caller.
        return sidebar;
    }

    // Define the add nav button method.
    private void addNavButton(JPanel sidebar, String name, String icon) {
        // Create and store the button.
        JButton btn = new JButton("    " + icon + "  " + name);
        // Set font on button.
        btn.setFont(UIConstants.FONT_SIDEBAR);
        // Set foreground on button.
        btn.setForeground(UIConstants.SIDEBAR_TEXT);
        // Set background on button.
        btn.setBackground(UIConstants.SIDEBAR_BG);
        // Set border painted on button.
        btn.setBorderPainted(false);
        // Set focus painted on button.
        btn.setFocusPainted(false);
        // Set opaque on button.
        btn.setOpaque(true);
        // Set horizontal alignment on button.
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        // Set maximum size on button.
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        // Set cursor on button.
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Attach an action listener on button.
        btn.addActionListener(e -> navigateTo(name));
        // Add mouse listener on button.
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            // Define the mouse entered method.
            public void mouseEntered(java.awt.event.MouseEvent e) {
                // Check whether not name.equals(activeNav).
                if (!name.equals(activeNav)) {
                    // Set background on button.
                    btn.setBackground(UIConstants.SIDEBAR_HOVER);
                }
            }
            // Define the mouse exited method.
            public void mouseExited(java.awt.event.MouseEvent e) {
                // Check whether not name.equals(activeNav).
                if (!name.equals(activeNav)) {
                    // Set background on button.
                    btn.setBackground(UIConstants.SIDEBAR_BG);
                }
            }
        });

        // Put on nav buttons.
        navButtons.put(name, btn);
        // Add on sidebar.
        sidebar.add(btn);
    }

    // Define the navigate to method.
    private void navigateTo(String name) {
        // Set active nav using the computed value.
        activeNav = name;

        // Update button styles
        // Loop using Map.Entry<String, JButton> entry : navButtons.entrySet().
        for (Map.Entry<String, JButton> entry : navButtons.entrySet()) {
            // Check whether entry.getKey().equals(name).
            if (entry.getKey().equals(name)) {
                // Get value on entry.
                entry.getValue().setBackground(UIConstants.SIDEBAR_ACTIVE);
                // Get value on entry.
                entry.getValue().setForeground(Color.WHITE);
            // Execute: } else {
            } else {
                // Get value on entry.
                entry.getValue().setBackground(UIConstants.SIDEBAR_BG);
                // Get value on entry.
                entry.getValue().setForeground(UIConstants.SIDEBAR_TEXT);
            }
        }

        // Refresh the target panel
        // Switch on name to choose the matching branch.
        switch (name) {
            // Handle this specific switch case.
            case "Dashboard" -> dashboardPanel.refresh();
            // Handle this specific switch case.
            case "Rooms" -> roomPanel.refresh();
            // Handle this specific switch case.
            case "Bookings" -> bookingPanel.refresh();
            // Handle this specific switch case.
            case "Maintenance" -> maintenancePanel.refresh();
            // Handle this specific switch case.
            case "Notifications" -> notificationPanel.refresh();
        }

        // Switch content
        // Calculate and store the parent.
        Container parent = dashboardPanel.getParent();
        // Check whether parent is not null and parent.getLayout() instanceof CardLayout cl.
        if (parent != null && parent.getLayout() instanceof CardLayout cl) {
            // Show on cl.
            cl.show(parent, name);
        }
    }

    // Define the on login success method.
    public void onLoginSuccess(User user) {
        // Set text on user info label.
        userInfoLabel.setText(user.getName());
        // Set text on role info label.
        roleInfoLabel.setText(user.getRole().getDisplayName());
        // Show on card layout.
        cardLayout.show(mainContent, "APP");
        // Navigate to.
        navigateTo("Dashboard");
    }

    // Define the handle logout method.
    private void handleLogout() {
        // Execute: int confirm = JOptionPane.showConfirmDialog(this,
        int confirm = JOptionPane.showConfirmDialog(this,
            // Execute: "Are you sure you want to sign out?",
            "Are you sure you want to sign out?",
            // Execute: "Sign Out", JOptionPane.YES_NO_OPTION);
            "Sign Out", JOptionPane.YES_NO_OPTION);
        // Check whether confirm is JOptionPane.YES_OPTION.
        if (confirm == JOptionPane.YES_OPTION) {
            // Log out through the facade.
            facade.logout();
            // Reset on login panel.
            loginPanel.reset();
            // Set active nav using the computed value.
            activeNav = "Dashboard";
            // Show on card layout.
            cardLayout.show(mainContent, "LOGIN");
        }
    }
}

