package persistence;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import model.habit.Habit;
import model.habit.HabitCycleManager;
import model.habit.HabitIncrement;
import model.habit.HabitSnapshot;
import model.habit.Habit.ProgressType;
import model.organization.Tag;
import model.organization.centralization.AllHabitsPage;
import model.organization.centralization.AllTagPagesPage;
import model.organization.specialpages.FavouritesPage;
import model.organization.specialpages.HomePage;

// Reads and writes data of AllHabitsPage from a destination
public class AllHabitsPageDataManager {
    private static final int TAB = 4; // Identation for JSON

    private String destination; // Where the manager writes to

    private AllHabitsPage allHabitsPage; // Where all the habits are

    /*
     * EFFECTS:
     * Instantiates AllHabitsPageDataManger such that
     * this.destination = destination
     * 
     * this.allHabitsPage = allHabitsPage
     */
    public AllHabitsPageDataManager(AllHabitsPage allHabitsPage, String destination) {
        this.destination = destination;
        this.allHabitsPage = allHabitsPage;
    }

    /*
     * MODIFIES:
     * allHabitsPage
     * homePage
     * favouritesPage
     * allTagPages
     * EFFECTS:
     * Reads destination and reconstructs AllHabitsPage
     * Throws IOException if error occurred while trying to read the file
     */
    public void readFromFile(HomePage homePage, FavouritesPage favouritesPage, AllTagPagesPage allTagPagesPage)
            throws IOException {
        allHabitsPage.getHabits().clear();

        JSONObject jsonObject = new JSONObject(Files.readString(Path.of(destination)));

        parseJsonObject(jsonObject, homePage, favouritesPage, allTagPagesPage);
    }

    // MODIFIES: allHabitsPage
    // EFFECTS: For every object in the JSONArray habits, rebuilds a habit and adds
    // it to allHabitsPage
    private void parseJsonObject(JSONObject jsonObject, HomePage homePage, FavouritesPage favouritesPage,
            AllTagPagesPage allTagPagesPage) {
        JSONArray habitsJson = jsonObject.getJSONArray("habits");

        if (habitsJson.length() == 0) {
            return;
        }

        for (Object json : habitsJson) {
            JSONObject habitJson = (JSONObject) json;
            buildHabit(habitJson, homePage, favouritesPage, allTagPagesPage);
        }
    }

    // MODIFIES: allHabitsPage
    // EFFECTS: For a habitJson, creates a habit and adds it to allHabitsPage
    private void buildHabit(JSONObject habitJson, HomePage homePage, FavouritesPage favouritesPage,
            AllTagPagesPage allTagPagesPage) {
        Habit habit = new HabitIncrement(1, 0, 1, "", LocalTime.now(), LocalDate.now(), LocalDateTime.now(),
                new HabitCycleManager(allHabitsPage, LocalDateTime.now()));

        buildTitle(habitJson, habit);
        buildCurrentAmount(habitJson, habit);
        buildGoal(habitJson, habit);
        buildOverloadAmount(habitJson, habit);
        buildProgressPercentage(habitJson, habit);
        buildStartingAmount(habitJson, habit);
        buildStepAmount(habitJson, habit);
        buildProgressType(habitJson, habit);
        buildUnit(habitJson, habit);
        buildCycleTime(habitJson, habit);
        buildCurrentDay(habitJson, habit);
        buildNextCycleTime(habitJson, habit);
        buildHistory(habitJson, habit);
        buildTags(habitJson, habit, homePage, favouritesPage, allTagPagesPage);

        allHabitsPage.addToAllHabitsPage(habit);
    }

    // MODIFIES: habit
    // EFFECTS: Fetches value at title key from habitJson and sets it as the title
    // of habit
    private void buildTitle(JSONObject habitJson, Habit habit) {
        String title = habitJson.getString("title");
        habit.setTitle(title);
    }

    // MODIFIES: habit
    // EFFECTS: Fetches value at currentAmount key from habitJson and sets it as the
    // currentAmount of habit
    private void buildCurrentAmount(JSONObject habitJson, Habit habit) {
        int currentAmount = habitJson.getInt("currentAmount");
        habit.setCurrentAmountLogic(currentAmount);
    }

    // MODIFIES: habit
    // EFFECTS: Fetches value at currentAmount key from habitJson and sets it as the
    // goal of habit
    private void buildGoal(JSONObject habitJson, Habit habit) {
        int goal = habitJson.getInt("goal");
        habit.setGoal(goal);
    }

    // MODIFIES: habit
    // EFFECTS: Fetches value at overloadAmount key from habitJson and sets it as
    // the overloadAmount of habit
    private void buildOverloadAmount(JSONObject habitJson, Habit habit) {
        int overloadAmount = habitJson.getInt("overloadAmount");
        habit.setOverloadAmount(overloadAmount);
    }

    // MODIFIES: habit
    // EFFECTS: Fetches value at progressPercentage key from habitJson and sets it
    // as the progressPercentage of habit
    private void buildProgressPercentage(JSONObject habitJson, Habit habit) {
        int progressPercentage = habitJson.getInt("progressPercentage");
        habit.setProgressPercentage(progressPercentage);
    }

    // MODIFIES: habit
    // EFFECTS: Fetches value at startingAmount key from habitJson and sets it as
    // the startingAmount of habit
    private void buildStartingAmount(JSONObject habitJson, Habit habit) {
        int startingAmount = habitJson.getInt("startingAmount");
        habit.setStartingAmount(startingAmount);
    }

    // MODIFIES: habit
    // EFFECTS: Fetches value at stepAmount key from habitJson and sets it as the
    // stepAmount of habit
    private void buildStepAmount(JSONObject habitJson, Habit habit) {
        int stepAmount = habitJson.getInt("stepAmount");
        habit.setStepAmount(stepAmount);
    }

    // MODIFIES: habit
    // EFFECTS: Fetches value at progressType key from habitJson and sets it as the
    // progressType of habit
    private void buildProgressType(JSONObject habitJson, Habit habit) {
        String progressType = habitJson.getString("progressType");

        switch (progressType) {
            case "UNDERDONE":
                habit.setProgressType(ProgressType.UNDERDONE);
                break;
            case "DONE":
                habit.setProgressType(ProgressType.DONE);
                break;
            default:
                habit.setProgressType(ProgressType.OVERLOADED);
        }
    }

    // MODIFIES: habit
    // EFFECTS: Fetches value at unit key from habitJson and sets it as the unit of
    // habit
    private void buildUnit(JSONObject habitJson, Habit habit) {
        String unit = habitJson.getString("unit");
        habit.setUnit(unit);
    }

    // MODIFIES: habit
    // EFFECTS: Fetches value at cycleTime key from habitJson and sets it as the
    // cycleTime of habit
    private void buildCycleTime(JSONObject habitJson, Habit habit) {
        String cycleTime = habitJson.getString("cycleTime");
        habit.setCycleTime(LocalTime.parse(cycleTime));
    }

    // MODIFIES: habit
    // EFFECTS: Fetches value at currentDay key from habitJson and sets it as the
    // currentDay of habit
    private void buildCurrentDay(JSONObject habitJson, Habit habit) {
        String currentDay = habitJson.getString("currentDay");
        habit.setCurrentDay(LocalDate.parse(currentDay));
    }

    // MODIFIES: habit
    // EFFECTS: Fetches value at nextCycleTime key from habitJson and sets it as the
    // nextCycleTime of habit
    private void buildNextCycleTime(JSONObject habitJson, Habit habit) {
        String nextCycleTime = habitJson.getString("nextCycleTime");
        habit.setNextCycleTime(LocalDateTime.parse(nextCycleTime));
    }

    // MODIFIES: habit
    // EFFECTS: For every jsonObject in history key, builds a habit snapshot and
    // adds it to history in habit
    private void buildHistory(JSONObject habitJson, Habit habit) {
        JSONArray history = habitJson.getJSONArray("history");
        for (Object jsonObject : history) {
            JSONObject habitSnapShotJson = (JSONObject) jsonObject;
            buildHabitSnapshot(habitSnapShotJson, habit);
        }
    }

    // MODIFIES: habit
    // EFFECTS: Given a habitSnapShotJson, creates a habitShapshot and adds it to
    // history in habit
    private void buildHabitSnapshot(JSONObject habitSnapShotJson, Habit habit) {
        HabitSnapshot habitSnapshot = new HabitSnapshot(1, 0, 0, 0, 0, 1, ProgressType.UNDERDONE, LocalDate.now(), "");

        buildCurrentAmountHistory(habitSnapShotJson, habitSnapshot);
        buildGoalHistory(habitSnapShotJson, habitSnapshot);
        buildOverloadAmountHistory(habitSnapShotJson, habitSnapshot);
        buildProgressPercentageHistory(habitSnapShotJson, habitSnapshot);
        buildStartingAmountHistory(habitSnapShotJson, habitSnapshot);
        buildStepAmountHistory(habitSnapShotJson, habitSnapshot);
        buildProgressTypeHistory(habitSnapShotJson, habitSnapshot);
        buildUnitHistory(habitSnapShotJson, habitSnapshot);
        buildDayHistory(habitSnapShotJson, habitSnapshot);

        habit.addToHistory(habitSnapshot);
    }

    // MODIFIES: habitSnapshot
    // EFFECTS: Fetches value at currentAmount key from habitSnapShotJson and sets
    // it as the currentAmount of habitSnapshot
    private void buildCurrentAmountHistory(JSONObject habitSnapShotJson, HabitSnapshot habitSnapshot) {
        int currentAmount = habitSnapShotJson.getInt("currentAmount");
        habitSnapshot.setCurrentAmount(currentAmount);
    }

    // MODIFIES: habitSnapshot
    // EFFECTS: Fetches value at goal key from habitSnapShotJson and sets
    // it as the goal of habitSnapshot
    private void buildGoalHistory(JSONObject habitSnapShotJson, HabitSnapshot habitSnapshot) {
        int goal = habitSnapShotJson.getInt("goal");
        habitSnapshot.setGoal(goal);
    }

    // MODIFIES: habitSnapshot
    // EFFECTS: Fetches value at overloadAmount key from habitSnapShotJson and sets
    // it as the overloadAmount of habitSnapshot
    private void buildOverloadAmountHistory(JSONObject habitSnapShotJson, HabitSnapshot habitSnapshot) {
        int overloadAmount = habitSnapShotJson.getInt("overloadAmount");
        habitSnapshot.setOverloadAmount(overloadAmount);
    }

    // MODIFIES: habitSnapshot
    // EFFECTS: Fetches value at progressPercentage key from habitSnapShotJson and
    // sets
    // it as the progressPercentage of habitSnapshot
    private void buildProgressPercentageHistory(JSONObject habitSnapShotJson, HabitSnapshot habitSnapshot) {
        int progressPercentage = habitSnapShotJson.getInt("progressPercentage");
        habitSnapshot.setProgressPercentage(progressPercentage);
    }

    // MODIFIES: habitSnapshot
    // EFFECTS: Fetches value at startingAmount key from habitSnapShotJson and sets
    // it as the startingAmount of habitSnapshot
    private void buildStartingAmountHistory(JSONObject habitSnapShotJson, HabitSnapshot habitSnapshot) {
        int startingAmount = habitSnapShotJson.getInt("startingAmount");
        habitSnapshot.setStartingAmount(startingAmount);
    }

    // MODIFIES: habitSnapshot
    // EFFECTS: Fetches value at stepAmount key from habitSnapShotJson and sets
    // it as the stepAmount of habitSnapshot
    private void buildStepAmountHistory(JSONObject habitSnapShotJson, HabitSnapshot habitSnapshot) {
        int stepAmount = habitSnapShotJson.getInt("stepAmount");
        habitSnapshot.setStepAmount(stepAmount);
    }

    // MODIFIES: habitSnapshot
    // EFFECTS: Fetches value at progressType key from habitSnapShotJson and sets
    // it as the progressType of habitSnapshot
    private void buildProgressTypeHistory(JSONObject habitSnapShotJson, HabitSnapshot habitSnapshot) {
        String progressType = habitSnapShotJson.getString("progressType");

        switch (progressType) {
            case "UNDERDONE":
                habitSnapshot.setProgressType(ProgressType.UNDERDONE);
                break;
            case "DONE":
                habitSnapshot.setProgressType(ProgressType.DONE);
                break;
            default:
                habitSnapshot.setProgressType(ProgressType.OVERLOADED);
        }
    }

    // MODIFIES: habitSnapshot
    // EFFECTS: Fetches value at unit key from habitSnapShotJson and sets
    // it as the unit of habitSnapshot
    private void buildUnitHistory(JSONObject habitSnapShotJson, HabitSnapshot habitSnapshot) {
        String unit = habitSnapShotJson.getString("unit");
        habitSnapshot.setUnit(unit);
    }

    // MODIFIES: habitSnapshot
    // EFFECTS: Fetches value at day key from habitSnapShotJson and sets
    // it as the day of habitSnapshot
    private void buildDayHistory(JSONObject habitSnapShotJson, HabitSnapshot habitSnapshot) {
        String day = habitSnapShotJson.getString("day");
        habitSnapshot.setDay(LocalDate.parse(day));
    }

    // MODIFIES: habit
    // EFFECTS: For jsonObject in jsonArray tags, builds a tag and adds it to tags
    // in habit
    private void buildTags(JSONObject habitJson, Habit habit, HomePage homePage, FavouritesPage favouritesPage,
            AllTagPagesPage allTagPagesPage) {
        JSONArray tags = habitJson.getJSONArray("tags");

        for (Object jsonObject : tags) {
            JSONObject tagJson = (JSONObject) jsonObject;
            habit.addTagAndSortTags(new Tag(tagJson.getString("title")), homePage, favouritesPage, allTagPagesPage);
        }
    }

    /*
     * MODIFIES:
     * this
     * EFFECTS:
     * Writes allHabitsPage to destination as JSON
     * Throws IOException if error occurred while trying to write
     */
    public void writeToFile(AllHabitsPage allHabitsPage) throws IOException {
        PrintWriter writer = new PrintWriter(new File(destination));
        writer.print(convertAllHabitsPageToJson(allHabitsPage).toString(TAB));
        writer.close();
    }

    // EFFECTS: Returns allHabitsPage as a JSONObject
    private JSONObject convertAllHabitsPageToJson(AllHabitsPage allHabitsPage) {
        JSONObject allHabitsPageJson = new JSONObject();

        allHabitsPageJson.put("habits", convertHabitsToJson(allHabitsPage.getHabits()));

        return allHabitsPageJson;
    }

    // EFFECTS: Returns habits as a JSONArray
    private JSONArray convertHabitsToJson(List<Habit> habits) {
        JSONArray habitsJson = new JSONArray();

        for (Habit habit : habits) {
            habitsJson.put(convertHabitToJson(habit));
        }

        return habitsJson;
    }

    // EFFECTS: Returns habit as a JSONObject
    private JSONObject convertHabitToJson(Habit habit) {
        JSONObject habitJson = new JSONObject();

        habitJson.put("title", habit.getTitle());
        habitJson.put("currentAmount", habit.getCurrentAmount());
        habitJson.put("goal", habit.getGoal());
        habitJson.put("overloadAmount", habit.getOverloadAmount());
        habitJson.put("progressPercentage", habit.getProgressPercentage());
        habitJson.put("startingAmount", habit.getStartingAmount());
        habitJson.put("stepAmount", habit.getStepAmount());
        habitJson.put("progressType", habit.getProgressType().toString());
        habitJson.put("unit", habit.getUnit());
        habitJson.put("cycleTime", habit.getCycleTime().toString());
        habitJson.put("currentDay", habit.getCurrentDay().toString());
        habitJson.put("nextCycleTime", habit.getNextCycleTime().toString());
        habitJson.put("history", convertHistoryToJson(habit.getHistory()));
        habitJson.put("tags", convertTagsToJson(habit.getTags()));

        return habitJson;
    }

    // EFFECTS: Returns history as a JSONArray
    private JSONArray convertHistoryToJson(List<HabitSnapshot> history) {
        JSONArray historyJson = new JSONArray();

        for (HabitSnapshot habitSnapshot : history) {
            historyJson.put(convertHabitSnapshotToJson(habitSnapshot));
        }

        return historyJson;
    }

    // EFFECTS: Returns habitSnapshot as a JSONObject
    private JSONObject convertHabitSnapshotToJson(HabitSnapshot habitSnapshot) {
        JSONObject habitSnapshotJson = new JSONObject();

        habitSnapshotJson.put("currentAmount", habitSnapshot.getCurrentAmount());
        habitSnapshotJson.put("goal", habitSnapshot.getGoal());
        habitSnapshotJson.put("overloadAmount", habitSnapshot.getOverloadAmount());
        habitSnapshotJson.put("progressPercentage", habitSnapshot.getProgressPercentage());
        habitSnapshotJson.put("startingAmount", habitSnapshot.getStartingAmount());
        habitSnapshotJson.put("stepAmount", habitSnapshot.getStepAmount());
        habitSnapshotJson.put("progressType", habitSnapshot.getProgressType().toString());
        habitSnapshotJson.put("unit", habitSnapshot.getUnit());
        habitSnapshotJson.put("day", habitSnapshot.getDay().toString());

        return habitSnapshotJson;
    }

    // EFFECTS: Returns tags as a JSONArray
    private JSONArray convertTagsToJson(List<Tag> tags) {
        JSONArray tagsJson = new JSONArray();

        for (Tag tag : tags) {
            JSONObject tagJson = new JSONObject();
            tagJson.put("title", tag.getTitle());
            tagJson.put("tagType", tag.getTagType().toString());
            tagJson.put("colour", tag.getColour());

            tagsJson.put(tagJson);
        }

        return tagsJson;
    }

    public String getDestination() {
        return destination;
    }

    public AllHabitsPage getAllHabitsPage() {
        return allHabitsPage;
    }
}
