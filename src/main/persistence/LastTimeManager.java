package persistence;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import org.json.JSONObject;

import model.exceptions.EmptyLastTimeFileException;

// Manages the last time the app was open
public class LastTimeManager {
    private static final int TAB = 4; // Identation for JSON

    private String destination; // Where the manager writes to

    // EFFECTS: Instantiates LastTimeManager such that this.destination =
    // destination
    public LastTimeManager(String destination) {
        this.destination = destination;
    }

    /*
     * EFFECTS:
     * Returns the last time from destination
     * Throws IOException upon failure to properly read
     * Throws EmptyLastTimeFileException if value is empty
     */
    public LocalDateTime readFromFile() throws IOException, EmptyLastTimeFileException {
        String lastTime = new JSONObject(Files.readString(Path.of(destination))).getString("lastTime");

        if (lastTime.length() == 0) {
            throw new EmptyLastTimeFileException();
        }

        return LocalDateTime.parse(lastTime);
    }

    /*
     * MODIFIES:
     * this
     * EFFECTS:
     * Returns the last time from destination
     * Throws IOException upon failure to properly write
     */
    public void writeToFile(LocalDateTime lastTime) throws IOException {
        PrintWriter writer = new PrintWriter(new File(destination));
        JSONObject lastTimeJson = new JSONObject();
        lastTimeJson.put("lastTime", lastTime.toString());
        writer.print(lastTimeJson.toString(TAB));
        writer.close();
    }

    public String getDestination() {
        return destination;
    }
}
