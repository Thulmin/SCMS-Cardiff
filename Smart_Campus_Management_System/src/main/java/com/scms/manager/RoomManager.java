// Declare that this file belongs to the com.scms.manager package.
package com.scms.manager;

// Import com.scms.exception.DuplicateDataException so it can be used in this file.
import com.scms.exception.DuplicateDataException;
import com.scms.model.Room;
import com.scms.model.RoomType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Declare the room manager class.
public class RoomManager {
    // Declare the rooms.
    private Map<String, Room> rooms;

    // Construct a new room manager instance.
    public RoomManager() {
        // Initialize rooms with a new value.
        rooms = new HashMap<>();
    }

    // Define the add room method.
    public void addRoom(Room room) throws DuplicateDataException {
        // Check whether rooms.containsKey(room.getId()).
        if (rooms.containsKey(room.getId())) {
            // Throw a new exception to report this error condition.
            throw new DuplicateDataException("Room with ID " + room.getId() + " already exists.");
        }
        // Put on rooms.
        rooms.put(room.getId(), room);
    }

    // Define the getter for room by id.
    public Room getRoomById(String id) {
        // Return rooms.get(id) to the caller.
        return rooms.get(id);
    }

    // Define the getter for all rooms.
    public List<Room> getAllRooms() {
        // Return new ArrayList<>(rooms.values()) to the caller.
        return new ArrayList<>(rooms.values());
    }

    // Define the getter for active rooms.
    public List<Room> getActiveRooms() {
        // Create and store the active.
        List<Room> active = new ArrayList<>();
        // Loop using Room room : rooms.values().
        for (Room room : rooms.values()) {
            // Check whether room.isActive().
            if (room.isActive()) {
                // Add on active.
                active.add(room);
            }
        }
        // Return active to the caller.
        return active;
    }

    // Define the getter for rooms by type.
    public List<Room> getRoomsByType(RoomType type) {
        // Create and store the result.
        List<Room> result = new ArrayList<>();
        // Loop using Room room : rooms.values().
        for (Room room : rooms.values()) {
            // Check whether room.getType() is type and room.isActive().
            if (room.getType() == type && room.isActive()) {
                // Add on result.
                result.add(room);
            }
        }
        // Return result to the caller.
        return result;
    }

    // Define the update room method.
    public void updateRoom(String id, String name, int capacity, RoomType type) {
        // Calculate and store the room.
        Room room = rooms.get(id);
        // Check whether room is not null.
        if (room != null) {
            // Set name on room.
            room.setName(name);
            // Set capacity on room.
            room.setCapacity(capacity);
            // Set type on room.
            room.setType(type);
        }
    }

    // Define the deactivate room method.
    public void deactivateRoom(String id) {
        // Calculate and store the room.
        Room room = rooms.get(id);
        // Check whether room is not null.
        if (room != null) {
            // Set active on room.
            room.setActive(false);
        }
    }

    // Define the activate room method.
    public void activateRoom(String id) {
        // Calculate and store the room.
        Room room = rooms.get(id);
        // Check whether room is not null.
        if (room != null) {
            // Set active on room.
            room.setActive(true);
        }
    }

    // Define the getter for room count.
    public int getRoomCount() {
        // Return rooms.size() to the caller.
        return rooms.size();
    }

    // Define the getter for active room count.
    public int getActiveRoomCount() {
        // Return (int) rooms.values().stream().filter(Room::isActive).count() to the caller.
        return (int) rooms.values().stream().filter(Room::isActive).count();
    }
}

