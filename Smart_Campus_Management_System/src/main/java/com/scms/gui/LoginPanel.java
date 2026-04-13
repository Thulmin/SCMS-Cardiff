// Declare that this file belongs to the com.scms.gui package.
package com.scms.gui;

// Import com.scms.facade.CampusManagementFacade so it can be used in this file.
import com.scms.facade.CampusManagementFacade;
import com.scms.model.User;
import javax.swing.*;
import java.awt.*;

// Declare the login panel class.
public class LoginPanel extends JPanel {
    // Declare the main frame.
    private MainFrame mainFrame;
    // Declare the facade.
    private CampusManagementFacade facade;
    // Declare the email field.
    private JTextField emailField;
    // Declare the password field.
    private JPasswordField passwordField;
    // Declare the message label.
    private JLabel messageLabel;

    // Construct a new login panel instance.
    public LoginPanel(MainFrame mainFrame, CampusManagementFacade facade) {
        // Set main frame using the computed value.
        this.mainFrame = mainFrame;
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
        // Left panel - branding
        // Create and store the left panel.
        JPanel leftPanel = new JPanel();
        // Set layout on left panel.
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        // Set background on left panel.
        leftPanel.setBackground(UIConstants.PRIMARY);
        // Set preferred size on left panel.
        leftPanel.setPreferredSize(new Dimension(450, 0));
        // Set border on left panel.
        leftPanel.setBorder(BorderFactory.createEmptyBorder(80, 50, 80, 50));

        // Create and store the logo.
        JLabel logo = new JLabel(new String(Character.toChars(0x1F3EB)));
        // Set font on logo.
        logo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 72));
        // Set foreground on logo.
        logo.setForeground(Color.WHITE);
        // Set alignment X on logo.
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Add on left panel.
        leftPanel.add(Box.createVerticalGlue());
        // Add on left panel.
        leftPanel.add(logo);
        // Add on left panel.
        leftPanel.add(Box.createVerticalStrut(20));

        // Create and store the brand title.
        JLabel brandTitle = new JLabel("Smart Campus");
        // Set font on brand title.
        brandTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        // Set foreground on brand title.
        brandTitle.setForeground(Color.WHITE);
        // Set alignment X on brand title.
        brandTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Add on left panel.
        leftPanel.add(brandTitle);

        // Create and store the brand subtitle.
        JLabel brandSubtitle = new JLabel("Management System");
        // Set font on brand subtitle.
        brandSubtitle.setFont(new Font("Segoe UI Light", Font.PLAIN, 22));
        // Set foreground on brand subtitle.
        brandSubtitle.setForeground(new Color(160, 180, 220));
        // Set alignment X on brand subtitle.
        brandSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Add on left panel.
        leftPanel.add(brandSubtitle);

        // Add on left panel.
        leftPanel.add(Box.createVerticalStrut(30));

        // Create and store the univ name.
        JLabel univName = new JLabel("Cardiff Metropolitan University");
        // Set font on univ name.
        univName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        // Set foreground on univ name.
        univName.setForeground(new Color(140, 160, 200));
        // Set alignment X on univ name.
        univName.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Add on left panel.
        leftPanel.add(univName);

        // Add on left panel.
        leftPanel.add(Box.createVerticalGlue());
        // Add.
        add(leftPanel, BorderLayout.WEST);

        // Right panel - login form
        // Create and store the right panel.
        JPanel rightPanel = new JPanel(new GridBagLayout());
        // Set background on right panel.
        rightPanel.setBackground(Color.WHITE);

        // Create and store the form panel.
        JPanel formPanel = new JPanel();
        // Set layout on form panel.
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        // Set background on form panel.
        formPanel.setBackground(Color.WHITE);
        // Set maximum size on form panel.
        formPanel.setMaximumSize(new Dimension(350, 400));

        // Create and store the login title.
        JLabel loginTitle = new JLabel("Welcome Back");
        // Set font on login title.
        loginTitle.setFont(UIConstants.FONT_TITLE);
        // Set foreground on login title.
        loginTitle.setForeground(UIConstants.TEXT_PRIMARY);
        // Set alignment X on login title.
        loginTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Add on form panel.
        formPanel.add(loginTitle);

        // Create and store the login subtitle.
        JLabel loginSubtitle = new JLabel("Sign in to your account");
        // Set font on login subtitle.
        loginSubtitle.setFont(UIConstants.FONT_BODY);
        // Set foreground on login subtitle.
        loginSubtitle.setForeground(UIConstants.TEXT_SECONDARY);
        // Set alignment X on login subtitle.
        loginSubtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Add on form panel.
        formPanel.add(loginSubtitle);

        // Add on form panel.
        formPanel.add(Box.createVerticalStrut(30));

        // Create and store the email label.
        JLabel emailLabel = new JLabel("Email Address");
        // Set font on email label.
        emailLabel.setFont(UIConstants.FONT_HEADING);
        // Set foreground on email label.
        emailLabel.setForeground(UIConstants.TEXT_PRIMARY);
        // Set alignment X on email label.
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Add on form panel.
        formPanel.add(emailLabel);
        // Add on form panel.
        formPanel.add(Box.createVerticalStrut(5));

        // Set email field using the computed value.
        emailField = UIConstants.createStyledTextField();
        // Set maximum size on email field.
        emailField.setMaximumSize(new Dimension(350, 40));
        // Set alignment X on email field.
        emailField.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Add on form panel.
        formPanel.add(emailField);

        // Add on form panel.
        formPanel.add(Box.createVerticalStrut(15));

        // Create and store the pass label.
        JLabel passLabel = new JLabel("Password");
        // Set font on pass label.
        passLabel.setFont(UIConstants.FONT_HEADING);
        // Set foreground on pass label.
        passLabel.setForeground(UIConstants.TEXT_PRIMARY);
        // Set alignment X on pass label.
        passLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Add on form panel.
        formPanel.add(passLabel);
        formPanel.add(Box.createVerticalStrut(5));

        // Set password field using the computed value.
        passwordField = UIConstants.createStyledPasswordField();
        // Set maximum size on password field.
        passwordField.setMaximumSize(new Dimension(350, 40));
        // Set alignment X on password field.
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Add on form panel.
        formPanel.add(passwordField);
        formPanel.add(Box.createVerticalStrut(10));

        // Initialize message label with a new value.
        messageLabel = new JLabel(" ");
        // Set font on message label.
        messageLabel.setFont(UIConstants.FONT_SMALL);
        // Set foreground on message label.
        messageLabel.setForeground(UIConstants.DANGER);
        // Set alignment X on message label.
        messageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Add on form panel.
        formPanel.add(messageLabel);
        formPanel.add(Box.createVerticalStrut(10));

        // Create and store the login button.
        JButton loginButton = new JButton("Sign In");
        // Set font on login button.
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        // Set background on login button.
        loginButton.setBackground(UIConstants.ACCENT);
        // Set foreground on login button.
        loginButton.setForeground(Color.WHITE);
        // Set focus painted on login button.
        loginButton.setFocusPainted(false);
        // Set border painted on login button.
        loginButton.setBorderPainted(false);
        // Set opaque on login button.
        loginButton.setOpaque(true);
        // Set maximum size on login button.
        loginButton.setMaximumSize(new Dimension(350, 45));
        // Set alignment X on login button.
        loginButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Set cursor on login button.
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Attach an action listener on login button.
        loginButton.addActionListener(e -> handleLogin());
        // Add mouse listener on login button.
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            // Define the mouse entered method.
            public void mouseEntered(java.awt.event.MouseEvent e) {
                // Set background on login button.
                loginButton.setBackground(UIConstants.ACCENT_HOVER);
            }
            // Define the mouse exited method.
            public void mouseExited(java.awt.event.MouseEvent e) {
                // Set background on login button.
                loginButton.setBackground(UIConstants.ACCENT);
            }
        });
        // Add on form panel.
        formPanel.add(loginButton);

        // Add on form panel.
        formPanel.add(Box.createVerticalStrut(25));

        // Create and store the cred panel.
        JPanel credPanel = new JPanel();
        // Set layout on cred panel.
        credPanel.setLayout(new BoxLayout(credPanel, BoxLayout.Y_AXIS));
        // Set background on cred panel.
        credPanel.setBackground(new Color(240, 248, 255));
        // Set border on cred panel.
        credPanel.setBorder(BorderFactory.createCompoundBorder(
            // Provide the next argument for set border.
            BorderFactory.createLineBorder(new Color(200, 220, 245), 1),
            // Provide the next argument for set border.
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        // Provide the remaining arguments needed to finish set border.
        ));
        // Set alignment X on cred panel.
        credPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Set maximum size on cred panel.
        credPanel.setMaximumSize(new Dimension(350, 130));

        // Create and store the cred title.
        JLabel credTitle = new JLabel("Demo Credentials");
        // Set font on cred title.
        credTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
        // Set foreground on cred title.
        credTitle.setForeground(UIConstants.ACCENT);
        // Add on cred panel.
        credPanel.add(credTitle);
        // Add on cred panel.
        credPanel.add(Box.createVerticalStrut(5));

        // Start populating the creds array.
        String[] creds = {
            // Add the next value to the current array literal.
            "Admin: admin@cardiffmet.ac.uk / admin123",
            // Add the next value to the current array literal.
            "Staff: j.smith@cardiffmet.ac.uk / staff123",
            // Add the next value to the current array literal.
            "Student: s.jones@cardiffmet.ac.uk / student123"
        // Finish the current array literal.
        };
        // Loop using String cred : creds.
        for (String cred : creds) {
            // Create and store the cred label.
            JLabel credLabel = new JLabel(cred);
            // Set font on cred label.
            credLabel.setFont(UIConstants.FONT_SMALL);
            // Set foreground on cred label.
            credLabel.setForeground(UIConstants.TEXT_SECONDARY);
            // Add on cred panel.
            credPanel.add(credLabel);
        }
        // Add on form panel.
        formPanel.add(credPanel);

        // Add on right panel.
        rightPanel.add(formPanel);
        // Add.
        add(rightPanel, BorderLayout.CENTER);

        // Enter key triggers login
        // Attach an action listener on password field.
        passwordField.addActionListener(e -> handleLogin());
        // Attach an action listener on email field.
        emailField.addActionListener(e -> passwordField.requestFocus());
    }

    // Define the handle login method.
    private void handleLogin() {
        // Calculate and store the email.
        String email = emailField.getText().trim();
        // Create and store the password.
        String password = new String(passwordField.getPassword());

        // Check whether email.isEmpty() or password.isEmpty().
        if (email.isEmpty() || password.isEmpty()) {
            // Set text on message label.
            messageLabel.setText("Please enter both email and password.");
            // Return return to the caller.
            return;
        }

        // Calculate and store the user.
        User user = facade.login(email, password);
        // Check whether user is not null.
        if (user != null) {
            // Set text on message label.
            messageLabel.setText(" ");
            // On login success on main frame.
            mainFrame.onLoginSuccess(user);
        // Execute: } else {
        } else {
            // Set text on message label.
            messageLabel.setText("Invalid email or password. Please try again.");
            // Set text on password field.
            passwordField.setText("");
        }
    }

    // Define the reset method.
    public void reset() {
        // Set text on email field.
        emailField.setText("");
        // Set text on password field.
        passwordField.setText("");
        // Set text on message label.
        messageLabel.setText(" ");
    }
}

