package persistence;

import org.json.JSONObject;

import model.organization.centralization.AllHabitsPage;

// Reads and writes data of AllHabitsPage from a destination
public class AllHabitsPageDataManager {
    private String destination;

    private AllHabitsPage allHabitsPage;

    public AllHabitsPageDataManager() {
        
    }

    // REQUIRES: destination is valid
    // EFFECTS: Reads destination and reconstructs AllHabitsPage
    public void readFromFile() {
        // !!!
    }

    // EFFECTS: Converts JSON in destination to a string
    public String parseDestination() {
        return null;
    }

    // MODIFIES: allHabitsPage
    // EFFECTS: Reconstructs AllHabitsPage
    public void parseJsonObject(JSONObject jsonObject) {
        // !!!
    }

    // REQUIRES: destination is valid and allHabitsPage != null
    // EFFECTS: Writes allHabitsPage to destination
    public void writeToFile() {
        // !!!
    }

    // REQUIRES: destination is valid
    public void setDestination(String destination) {
        this.destination = destination;
    }

    // REQUIRES: allHabitsPage is valid
    public void allHabitsPage(AllHabitsPage allHabitsPage) {
        this.allHabitsPage = allHabitsPage;
    }

    public String getDestination() {
        return destination;
    }

    public AllHabitsPage getAllHabitsPage() {
        return allHabitsPage;
    }
}
