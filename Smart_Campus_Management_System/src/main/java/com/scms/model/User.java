// Declare that this file belongs to the com.scms.model package.
package com.scms.model;

// Import com.scms.pattern.Observer so it can be used in this file.
import com.scms.pattern.Observer;
import java.util.ArrayList;
import java.util.List;

// Declare the user class.
public abstract class User implements Observer {
    // Declare the id.
    private String id;
    // Declare the name.
    private String name;
    // Declare the email.
    private String email;
    // Declare the password.
    private String password;
    // Declare the role.
    private UserRole role;
    // Declare the notifications.
    private List<Notification> notifications;

    // Construct a new user instance.
    public User(String id, String name, String email, String password, UserRole role) {
        // Set id using the computed value.
        this.id = id;
        // Set name using the computed value.
        this.name = name;
        // Set email using the computed value.
        this.email = email;
        // Set password using the computed value.
        this.password = password;
        // Set role using the computed value.
        this.role = role;
        // Initialize notifications with a new value.
        this.notifications = new ArrayList<>();
    }

    // Define the getter for id.
    public String getId() { return id; }
    // Define the getter for name.
    public String getName() { return name; }
    // Define the getter for email.
    public String getEmail() { return email; }
    // Define the getter for password.
    public String getPassword() { return password; }
    // Define the getter for role.
    public UserRole getRole() { return role; }
    // Define the getter for notifications.
    public List<Notification> getNotifications() { return notifications; }

    // Define the setter for name.
    public void setName(String name) { this.name = name; }
    // Define the setter for email.
    public void setEmail(String email) { this.email = email; }
    // Define the setter for password.
    public void setPassword(String password) { this.password = password; }

    // Define the add notification method.
    public void addNotification(Notification notification) {
        // Add on notifications.
        notifications.add(notification);
    }

    // Apply the Override annotation to the next declaration.
    @Override
    // Define the update method.
    public void update(String message) {
        // Start creating and assigning the notification.
        Notification notification = new Notification(
            // Provide the next value used to build notification.
            "N" + System.currentTimeMillis(),
            // Provide the next value used to build notification.
            message,
            // Provide the next value used to build notification.
            this.id
        // Provide the remaining values needed to finish notification.
        );
        // Add notification.
        addNotification(notification);
    // End the current code block.
    }

    // Define the getter for dashboard welcome.
    public abstract String getDashboardWelcome();

    // Apply the Override annotation to the next declaration.
    @Override
    // Define the to string method.
    public String toString() {
        // Return name + " (" + role.getDisplayName() + ")" to the caller.
        return name + " (" + role.getDisplayName() + ")";
    }
}

