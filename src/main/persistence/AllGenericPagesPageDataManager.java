package persistence;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import model.exceptions.HabitNotFoundException;
import model.habit.Habit;
import model.organization.centralization.AllGenericPagesPage;
import model.organization.centralization.AllHabitsPage;
import model.organization.tree.Page;
import model.organization.tree.Page.Order;

// Reads and writes data of AllGenericPages from a destination
public class AllGenericPagesPageDataManager {
    private static final int TAB = 4; // Identation

    private String destination; // destination to write to

    private AllGenericPagesPage allGenericPagesPage; // Where all the pages are

    /*
     * Instantiates AllGenericPagesDataManager such that
     * this.destination = destination
     * this.allGenericPagesPage = allGenericPagesPage
     */
    public AllGenericPagesPageDataManager(String destination, AllGenericPagesPage allGenericPagesPage) {
        this.destination = destination;
        this.allGenericPagesPage = allGenericPagesPage;
    }

    /*
     * MODIFIES:
     * allGenericPagesPage
     * EFFECTS:
     * Reconstructs allGenericPagesPage from file
     * Throws IOException upon failure to properly read
     * Throws HabitNotFoundException if habit could not be found in allHabitsPage
     */
    public void readFromFile(AllHabitsPage allHabitsPage) throws IOException, HabitNotFoundException {
        allGenericPagesPage.getPages().clear();

        JSONObject fileJson = new JSONObject(Files.readString(Path.of(destination)));

        parseFileJson(fileJson, allHabitsPage);
    }

    /*
     * MODIFIES:
     * allHabitsPage
     * EFFECTS:
     * Reads fileJson to reconstruct allGenericPagesPage
     * Throws HabitNotFoundException if habit could not be found in allHabitsPage
     */
    private void parseFileJson(JSONObject fileJson, AllHabitsPage allHabitsPage) throws HabitNotFoundException {
        JSONArray pagesJson = fileJson.getJSONArray("pages");

        if (pagesJson.length() == 0) {
            return;
        }

        for (Object objectJson : pagesJson) {
            JSONObject pageJson = (JSONObject) objectJson;
            buildPage(pageJson, allHabitsPage);
        }
    }

    /*
     * MODIFIES:
     * allHabitsPage
     * EFFECTS:
     * Creates a page from pageJson and adds it to allGenericPagesPage
     * Throws HabitNotFoundException if habit could not be found in allHabitsPage
     */
    private void buildPage(JSONObject pageJson, AllHabitsPage allHabitsPage) throws HabitNotFoundException {
        Page page = new Page("");

        buildTitle(pageJson, page);
        buildOrder(pageJson, page);
        buildHabits(pageJson, allHabitsPage, page);

        allGenericPagesPage.addToPages(page);
    }

    // MODIFIES: page
    // EFFECTS: Given pageJson, sets the title for page
    private void buildTitle(JSONObject pageJson, Page page) {
        String title = pageJson.getString("title");
        page.setTitle(title);
    }

    // MODIFIES: page
    // EFFECTS: Given pageJson, sets the order for page
    private void buildOrder(JSONObject pageJson, Page page) {
        String order = pageJson.getString("order");
        switch (order) {
            case "ALPHABETICAL":
                page.setOrder(Order.ALPHABETICAL);
                break;
            default:
                page.setOrder(Order.MANUAL);
        }
    }

    /*
     * MODIFIES:
     * page
     * allHabitsPage
     * EFFECTS:
     * Builds the list of habits for page
     * Throws HabitNotFoundException if habit could not be found in allHabitsPage
     */
    private void buildHabits(JSONObject pageJson, AllHabitsPage allHabitsPage, Page page)
            throws HabitNotFoundException {
        JSONArray habitsJson = pageJson.getJSONArray("habits");
        for (Object objectJson : habitsJson) {
            JSONObject habitJson = (JSONObject) objectJson;
            page.addHabit(findHabit(habitJson, allHabitsPage, page));
        }
    }

    /*
     * MODIFIES:
     * page
     * allHabitsPage
     * EFFECTS:
     * Given habitJson, attempts to return the corresponding reference in
     * allHabitsPage
     * Throws HabitNotFoundException if habit could not be found in allHabitsPage
     */
    private Habit findHabit(JSONObject habitJson, AllHabitsPage allHabitsPage, Page page)
            throws HabitNotFoundException {
        String title = habitJson.getString("title");

        for (Habit habit : allHabitsPage.getHabits()) {
            if (habit.getTitle().equals(title)) {
                return habit;
            }
        }

        throw new HabitNotFoundException();
    }

    /*
     * MODIFIES:
     * this
     * EFFECTS:
     * Writes allGenericPagesPage to file as JSON
     * Throws IOException upon failure to properly write
     */
    public void writeToFile() throws IOException {
        PrintWriter writer = new PrintWriter(new File(destination));
        writer.print(convertAllGenericPagesPageToJson(allGenericPagesPage).toString(TAB));
        writer.close();
    }

    // !!!
    private JSONObject convertAllGenericPagesPageToJson(AllGenericPagesPage allGenericPagesPage) {
        JSONObject allGenericPagesPageJson = new JSONObject();

        allGenericPagesPageJson.put("pages", convertPagesToJson(allGenericPagesPage.getPages()));

        return allGenericPagesPageJson;
    }

    // !!!
    private JSONArray convertPagesToJson(List<Page> pages) {
        JSONArray pagesJson = new JSONArray();

        for (Page page : pages) {
            pagesJson.put(convertPageToJson(page));
        }

        return pagesJson;
    }

    // !!!
    private JSONObject convertPageToJson(Page page) {
        JSONObject pageJson = new JSONObject();

        pageJson.put("title", page.getTitle());
        pageJson.put("order", page.getOrder().toString());
        pageJson.put("habits", convertHabitsToJson(page.getHabits()));

        return pageJson;
    }

    // !!!
    private JSONArray convertHabitsToJson(List<Habit> habits) {
        JSONArray habitsJson = new JSONArray();

        for (Habit habit : habits) {
            JSONObject habitJson = new JSONObject();
            habitJson.put("title", habit.getTitle());
            habitsJson.put(habitJson);
        }
        return habitsJson;
    }

    public String getDestination() {
        return destination;
    }

    public AllGenericPagesPage getAllGenericPagesPage() {
        return allGenericPagesPage;
    }
}
