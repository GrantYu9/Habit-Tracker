package persistence;

import java.time.LocalDateTime;

// Manages the last time the app was open
public class LastTimeManager {
    private String destination; // Where the manager writes to

    // EFFECTS: Instantiates LastTimeManager such that this.destination =
    // destination
    public LastTimeManager(String destination) {
        // !!!
    }

    // EFFECTS: Returns the last time from destination
    public LocalDateTime readFromFile() {
        // !!!
        return null; // stub
    }

    // EFFECTS: Writes lastTime to destination
    public void writeToFile(LocalDateTime lastTime) {
        // !!!
    }

    public String getDestination() {
        return destination;
    }
}
