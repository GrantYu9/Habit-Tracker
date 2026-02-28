package persistence;

import java.io.IOException;
import java.nio.file.Path;

import org.json.JSONObject;

import model.organization.centralization.AllHabitsPage;

// Reads and writes data of AllHabitsPage from a destination
public class AllHabitsPageDataManager {
    private AllHabitsPage allHabitsPage;

    private String destination;

    /*
    EFFECTS:
    Instantiates AllHabitsPageDataManger such that
        this.allHabitsPage = allHabitsPage

        this.destination = destination
     */
    public AllHabitsPageDataManager(AllHabitsPage allHabitsPage, String destination) {
        // !!!
    }

    /*
    MODIFIES:
    allHabitsPage
    EFFECTS:
    Reads destination and reconstructs AllHabitsPage
    Throws IOException if error occurred while trying to read the file
     */
    public void readFromFile() throws IOException {
        // !!!
    }

    // EFFECTS: Converts JSON in destination to a string
    private String parseDestination() {
        return null;
    }

    // MODIFIES: allHabitsPage
    // EFFECTS: Reconstructs AllHabitsPage
    private void parseJsonObject(JSONObject jsonObject) {
        // !!!
    }

    /*
    EFFECTS:
    Writes allHabitsPage to destination as JSON
    Throws IOException if error occurred while trying to write
     */
    public void writeToFile(AllHabitsPage allHabitsPage) throws IOException {
        // !!!
    }

    public AllHabitsPage getAllHabitsPage() {
        return allHabitsPage;
    }

    public String getDestination() {
        return destination;
    }
}
