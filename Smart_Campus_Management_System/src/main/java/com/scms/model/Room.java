// Declare that this file belongs to the com.scms.model package.
package com.scms.model;

// Import java.util.ArrayList so it can be used in this file.
import java.util.ArrayList;
import java.util.List;

// Declare the room class.
public class Room {
    // Declare the id.
    private String id;
    // Declare the name.
    private String name;
    // Declare the capacity.
    private int capacity;
    // Declare the type.
    private RoomType type;
    // Declare the equipment.
    private List<String> equipment;
    // Declare the active.
    private boolean active;

    // Construct a new room instance.
    public Room(String id, String name, int capacity, RoomType type, List<String> equipment) {
        // Set id using the computed value.
        this.id = id;
        // Set name using the computed value.
        this.name = name;
        // Set capacity using the computed value.
        this.capacity = capacity;
        // Set type using the computed value.
        this.type = type;
        // Set equipment using the computed value.
        this.equipment = equipment != null ? new ArrayList<>(equipment) : new ArrayList<>();
        // Set active using the computed value.
        this.active = true;
    }

    // Define the getter for id.
    public String getId() { return id; }
    // Define the getter for name.
    public String getName() { return name; }
    // Define the getter for capacity.
    public int getCapacity() { return capacity; }
    // Define the getter for type.
    public RoomType getType() { return type; }
    // Define the getter for equipment.
    public List<String> getEquipment() { return equipment; }
    // Define the check for whether active.
    public boolean isActive() { return active; }

    // Define the setter for name.
    public void setName(String name) { this.name = name; }
    // Define the setter for capacity.
    public void setCapacity(int capacity) { this.capacity = capacity; }
    // Define the setter for type.
    public void setType(RoomType type) { this.type = type; }
    // Define the setter for equipment.
    public void setEquipment(List<String> equipment) { this.equipment = equipment; }
    // Define the setter for active.
    public void setActive(boolean active) { this.active = active; }

    // Define the getter for equipment string.
    public String getEquipmentString() {
        // Return String.join(", ", equipment) to the caller.
        return String.join(", ", equipment);
    }

    // Apply the Override annotation to the next declaration.
    @Override
    // Define the to string method.
    public String toString() {
        // Return name + " (" + type.getDisplayName() + ") - Capacity: " + capacity to the caller.
        return name + " (" + type.getDisplayName() + ") - Capacity: " + capacity;
    }
}

