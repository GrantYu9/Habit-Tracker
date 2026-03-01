package persistence;

import java.io.IOException;
import java.time.LocalDateTime;

import model.exceptions.EmptyLastTimeFileException;

// Manages the last time the app was open
public class LastTimeManager {
    private String destination; // Where the manager writes to

    // EFFECTS: Instantiates LastTimeManager such that this.destination =
    // destination
    public LastTimeManager(String destination) {
        // !!!
    }

    /*
     * EFFECTS:
     * Returns the last time from destination
     * Throws IOException upon failure to properly read
     * Throws EmptyLastTimeFileException if file is empty
     */
    public LocalDateTime readFromFile() throws IOException, EmptyLastTimeFileException {
        // !!!
        return null; // stub
    }

    /*
     * EFFECTS:
     * Returns the last time from destination
     * Throws IOException upon failure to properly write
     */
    public void writeToFile(LocalDateTime lastTime) throws IOException {
        // !!!
    }

    public String getDestination() {
        return destination;
    }
}
