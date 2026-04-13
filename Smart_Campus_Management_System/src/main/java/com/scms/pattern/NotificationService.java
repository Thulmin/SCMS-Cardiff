// Declare that this file belongs to the com.scms.pattern package.
package com.scms.pattern;

// Import java.util.ArrayList so it can be used in this file.
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Declare the notification service class.
public class NotificationService {
    // Declare the topic subscribers.
    private Map<String, List<Observer>> topicSubscribers;
    // Declare the global subscribers.
    private List<Observer> globalSubscribers;
    // Declare the notification log.
    private List<String> notificationLog;

    // Construct a new notification service instance.
    public NotificationService() {
        // Initialize topic subscribers with a new value.
        this.topicSubscribers = new HashMap<>();
        // Initialize global subscribers with a new value.
        this.globalSubscribers = new ArrayList<>();
        this.notificationLog = new ArrayList<>();
    }

    // Define the subscribe method.
    public void subscribe(Observer observer) {
        // Check whether not globalSubscribers.contains(observer).
        if (!globalSubscribers.contains(observer)) {
            // Add on global subscribers.
            globalSubscribers.add(observer);
        // End the current code block.
        }
    }

    // Define the unsubscribe method.
    public void unsubscribe(Observer observer) {
        // Remove on global subscribers.
        globalSubscribers.remove(observer);
    }

    // Define the subscribe method.
    public void subscribe(String topic, Observer observer) {
        // Compute if absent on topic subscribers.
        topicSubscribers.computeIfAbsent(topic, k -> new ArrayList<>());
        // Calculate and store the subscribers.
        List<Observer> subscribers = topicSubscribers.get(topic);
        // Check whether not subscribers.contains(observer).
        if (!subscribers.contains(observer)) {
            // Add on subscribers.
            subscribers.add(observer);
        }
    }

    // Define the unsubscribe method.
    public void unsubscribe(String topic, Observer observer) {
        // Calculate and store the subscribers.
        List<Observer> subscribers = topicSubscribers.get(topic);
        // Check whether subscribers is not null.
        if (subscribers != null) {
            // Remove on subscribers.
            subscribers.remove(observer);
        // End the current code block.
        }
    }

    // Define the notify all method.
    public void notifyAll(String message) {
        // Add on notification log.
        notificationLog.add(message);
        // Loop using Observer observer : globalSubscribers.
        for (Observer observer : globalSubscribers) {
            // Update on observer.
            observer.update(message);
        // End the current code block.
        }
    }

    // Define the notify topic method.
    public void notifyTopic(String topic, String message) {
        // Add on notification log.
        notificationLog.add("[" + topic + "] " + message);
        // Calculate and store the subscribers.
        List<Observer> subscribers = topicSubscribers.get(topic);
        // Check whether subscribers is not null.
        if (subscribers != null) {
            // Loop using Observer observer : subscribers.
            for (Observer observer : subscribers) {
                // Update on observer.
                observer.update(message);
            // End the current code block.
            }
        }
    }

    // Define the notify observer method.
    public void notifyObserver(Observer observer, String message) {
        // Add on notification log.
        notificationLog.add(message);
        // Update on observer.
        observer.update(message);
    }

    // Define the getter for notification log.
    public List<String> getNotificationLog() {
        // Return new ArrayList<>(notificationLog) to the caller.
        return new ArrayList<>(notificationLog);
    }

    // Define the getter for subscriber count.
    public int getSubscriberCount() {
        // Return globalSubscribers.size() to the caller.
        return globalSubscribers.size();
    }

    // Define the getter for topic subscriber count.
    public int getTopicSubscriberCount(String topic) {
        // Calculate and store the subscribers.
        List<Observer> subscribers = topicSubscribers.get(topic);
        // Return subscribers is not null ? subscribers.size() : 0 to the caller.
        return subscribers != null ? subscribers.size() : 0;
    }
}

