// Declare that this file belongs to the com.scms.pattern package.
package com.scms.pattern;

// Import com.scms.model.Room so it can be used in this file.
import com.scms.model.Room;
import com.scms.model.RoomType;
import java.util.Arrays;
import java.util.List;

// Declare the room factory class.
public class RoomFactory {

    // Define the create room method.
    public static Room createRoom(String id, String name, int capacity, RoomType type) {
        // Calculate and store the equipment.
        List<String> equipment = getDefaultEquipment(type);
        // Return new Room(id, name, capacity, type, equipment) to the caller.
        return new Room(id, name, capacity, type, equipment);
    }

    // Define the create lecture hall method.
    public static Room createLectureHall(String id, String name, int capacity) {
        // Calculate and store the equipment.
        List<String> equipment = Arrays.asList("Projector", "Microphone", "Whiteboard", "Speaker System");
        // Return new Room(id, name, capacity, RoomType.LECTURE_HALL, equipment) to the caller.
        return new Room(id, name, capacity, RoomType.LECTURE_HALL, equipment);
    }

    // Define the create computer lab method.
    public static Room createComputerLab(String id, String name, int capacity) {
        // Calculate and store the equipment.
        List<String> equipment = Arrays.asList("Computers", "Projector", "Printer", "Whiteboard");
        // Return new Room(id, name, capacity, RoomType.COMPUTER_LAB, equipment) to the caller.
        return new Room(id, name, capacity, RoomType.COMPUTER_LAB, equipment);
    }

    // Define the create seminar room method.
    public static Room createSeminarRoom(String id, String name, int capacity) {
        // Calculate and store the equipment.
        List<String> equipment = Arrays.asList("Projector", "Whiteboard", "Video Conferencing");
        // Return new Room(id, name, capacity, RoomType.SEMINAR_ROOM, equipment) to the caller.
        return new Room(id, name, capacity, RoomType.SEMINAR_ROOM, equipment);
    }

    // Define the create meeting room method.
    public static Room createMeetingRoom(String id, String name, int capacity) {
        // Calculate and store the equipment.
        List<String> equipment = Arrays.asList("TV Screen", "Whiteboard", "Video Conferencing");
        // Return new Room(id, name, capacity, RoomType.MEETING_ROOM, equipment) to the caller.
        return new Room(id, name, capacity, RoomType.MEETING_ROOM, equipment);
    }

    // Define the create workshop method.
    public static Room createWorkshop(String id, String name, int capacity) {
        // Calculate and store the equipment.
        List<String> equipment = Arrays.asList("Workbenches", "Tools", "Safety Equipment", "Whiteboard");
        // Return new Room(id, name, capacity, RoomType.WORKSHOP, equipment) to the caller.
        return new Room(id, name, capacity, RoomType.WORKSHOP, equipment);
    }

    // Define the getter for default equipment.
    private static List<String> getDefaultEquipment(RoomType type) {
        // Return switch (type) { to the caller.
        return switch (type) {
            // Handle this specific switch case.
            case LECTURE_HALL -> Arrays.asList("Projector", "Microphone", "Whiteboard");
            // Handle this specific switch case.
            case COMPUTER_LAB -> Arrays.asList("Computers", "Projector", "Printer");

            case SEMINAR_ROOM -> Arrays.asList("Projector", "Whiteboard");
            // Handle this specific switch case.
            case MEETING_ROOM -> Arrays.asList("TV Screen", "Whiteboard");
            // Handle this specific switch case.
            case WORKSHOP -> Arrays.asList("Workbenches", "Tools", "Safety Equipment");
        };
    }
}

