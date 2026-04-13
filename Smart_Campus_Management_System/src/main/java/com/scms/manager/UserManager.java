// Declare that this file belongs to the com.scms.manager package.
package com.scms.manager;

// Import com.scms.exception.DuplicateDataException so it can be used in this file.
import com.scms.exception.DuplicateDataException;
// Import com.scms.model.* so it can be used in this file.
import com.scms.model.*;
// Import java.util.ArrayList so it can be used in this file.
import java.util.ArrayList;
// Import java.util.HashMap so it can be used in this file.
import java.util.HashMap;
// Import java.util.List so it can be used in this file.
import java.util.List;
// Import java.util.Map so it can be used in this file.
import java.util.Map;

// Declare the user manager class.
public class UserManager {
    // Declare the instance.
    private static UserManager instance;
    // Declare the users.
    private Map<String, User> users;
    // Declare the current user.
    private User currentUser;

    // Construct a new user manager instance.
    private UserManager() {
        // Initialize users with a new value.
        users = new HashMap<>();
    }

    // Define the getter for instance.
    public static synchronized UserManager getInstance() {
        // Check whether instance is null.
        if (instance == null) {
            // Initialize instance with a new value.
            instance = new UserManager();
        }
        // Return instance to the caller.
        return instance;
    }

    // Define the reset instance method.
    public static synchronized void resetInstance() {
        // Set instance using the computed value.
        instance = null;
    }

    // Define the add user method.
    public void addUser(User user) throws DuplicateDataException {
        // Check whether users.containsKey(user.getId()).
        if (users.containsKey(user.getId())) {
            // Throw a new exception to report this error condition.
            throw new DuplicateDataException("User with ID " + user.getId() + " already exists.");
        }
        // Loop using User existing : users.values().
        for (User existing : users.values()) {
            // Check whether existing.getEmail().equalsIgnoreCase(user.getEmail()).
            if (existing.getEmail().equalsIgnoreCase(user.getEmail())) {
                // Throw a new exception to report this error condition.
                throw new DuplicateDataException("User with email " + user.getEmail() + " already exists.");
            }
        }
        // Put on users.
        users.put(user.getId(), user);
    }

    // Define the authenticate method.
    public User authenticate(String email, String password) {
        // Loop using User user : users.values().
        for (User user : users.values()) {
            // Check whether user.getEmail().equalsIgnoreCase(email) and user.getPassword().equals(password).
            if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)) {
                // Set current user using the computed value.
                currentUser = user;
                // Return user to the caller.
                return user;
            }
        }
        // Return null to the caller.
        return null;
    }

    // Define the logout method.
    public void logout() {
        // Set current user using the computed value.
        currentUser = null;
    }

    // Define the getter for current user.
    public User getCurrentUser() {
        // Return currentUser to the caller.
        return currentUser;
    }

    // Define the getter for user by id.
    public User getUserById(String id) {
        // Return users.get(id) to the caller.
        return users.get(id);
    }

    // Define the getter for user by email.
    public User getUserByEmail(String email) {
        // Loop using User user : users.values().
        for (User user : users.values()) {
            // Check whether user.getEmail().equalsIgnoreCase(email).
            if (user.getEmail().equalsIgnoreCase(email)) {
                // Return user to the caller.
                return user;
            }
        }
        // Return null to the caller.
        return null;
    }

    // Define the getter for all users.
    public List<User> getAllUsers() {
        // Return new ArrayList<>(users.values()) to the caller.
        return new ArrayList<>(users.values());
    }

    // Define the getter for users by role.
    public List<User> getUsersByRole(UserRole role) {
        // Create and store the result.
        List<User> result = new ArrayList<>();
        // Loop using User user : users.values().
        for (User user : users.values()) {
            // Check whether user.getRole() is role.
            if (user.getRole() == role) {
                // Add on result.
                result.add(user);
            // End the current code block.
            }
        }
        // Return result to the caller.
        return result;
    }

    // Define the remove user method.
    public void removeUser(String id) {
        // Remove on users.
        users.remove(id);
    }

    // Define the getter for user count.
    public int getUserCount() {
        // Return users.size() to the caller.
        return users.size();
    }
}

