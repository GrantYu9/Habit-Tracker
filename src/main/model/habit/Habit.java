package model.habit;

/*
We use local time to capture when the user wants to cycle, then covert it to
local date time and add by 24 hr to cycle
We could also add in a cycle by how much feature
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import model.organization.Tag;
import model.organization.specialpages.AllHabitsPage;

/*
An abstract class for BuildHabit and BreakHabit
Features basic getters and setters
Leaves most of the more complex logic for derived classes, which have class specific behaviour
 */
public abstract class Habit {
    private int currentAmount; // Current amount. E.g. 100 mL or 1,000 steps
    private int goal; // A goal amount
    private int overloadAmount; // How much a habit is overloaded
    private int progressPercentage; // Amount of progress made towards a goal
    private int startingAmount; // The starting amount a user sets
    private int stepAmount; // How much one wants to advance by; stepAmount > 0
    private ProgressType progressType; // Progress relative to the goal
    /*
    Warning: 
    The behaviour of this enum is different between the derived classes. As such, please read the documentation that 
    will be provided near the tops of each derived classes, where the variables would be declared
     */
    public enum ProgressType {
        UNDERDONE,
        DONE,
        OVERLOADED
    }
    private ViewMode viewMode; // How one can view the habit
    public enum ViewMode {
        BAR, // The default way to view a habit and how one can interact with it
        HEATMAP // The heatmap to view history
    }

    private String title; // A title for the habit
    private String unit; // Type of units. E.g. mL or steps

    private LocalTime cycleTime; // Abstractly, when the habit needs to cycle every day as a clock time
    private LocalDate currentDay; // The day of the habit
    private LocalDateTime nextCycleTime; // Precisely when the habit needs to cycle

    private List<HabitSnapshot> history; // A record of past data of the habit
    private List<Tag> tags; // Labels that can be attached to the habit for organization

    /*
    REQUIRES:
    0 < stepAmount <= |goal|
    title has at least one character
    unit has at least one character
    EFFECTS:
    Instantiates a habit such that
        this.goal = goal
        this.startingAmount = startingAmount
        this.stepAmount = stepAmount
        this.title = title, with surrounding whitespace trimmed
        this.unit = unit, with surrounding whitespace trimmed
        this.cycleTime = cycleTime

        this.currentAmount = startingAmount
        overloadAmount = 0
        viewMode = BAR
        progressType = UNDERDONE
        this.currentDay = LocalDate.now
        history = new ArrayList
        tags = new ArrayList
    Calculate progressPercentage and set this.progressPercentage to the output
    Adds this to AllHabitsPages
    Calls HabitCycleManager to set nextCycleTime and execute cycleHabitWhileRunning at nextCycleTime
    */
    public Habit(
        int goal, 
        int startingAmount, 
        int stepAmount,
        String title,
        String unit,
        LocalTime cycleTime,
        LocalDate currentDay,
        AllHabitsPage allHabitsPage,
        HabitCycleManager habitCycleManager
    ) {
        // !!!
    }

    public abstract int calculateOverloadAmount(int currentAmount, int goal);
    public abstract int calculateProgressPercentage(int startingAmount, int currentAmount, int goal);
    public abstract void progressByStepAmount();
    public abstract void setCurrentAmount(int currentAmount);

    // MODIFIES: this
    // EFFECTS: Appends tag to tags and sorts tags by alphabetical order
    public void addTagAndSortTags(Tag tag) {
        // !!!
    }

    // MODIFIES: this
    // EFFECTS: Appends HabitSnapshot to history
    public void addToHistory(HabitSnapshot habitSnapshot) {
        // !!!
    }

    // REQUIRES: stepAmount > 0
    public void setStepAmount(int incrementAmount) {
        this.stepAmount = incrementAmount;
    }

    // REQUIRES: title has at least one character
    // EFFECTS: this.title = title, with surrounding whitespace trimmed
    public void setTitle(String title) {
        this.title = title;
    }

    // REQUIRES: unit has at least one character
    // EFFECTS: this.unit = unit, with surrounding whitespace trimmed
    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public void setOverloadAmount(int overloadAmount) {
        this.overloadAmount = overloadAmount;
    }

    public void setProgressPercentage(int progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public void setStartingAmount(int startingAmount) {
        this.startingAmount = startingAmount;
    }

    public void setProgressType(ProgressType progressType) {
        this.progressType = progressType;
    }

    public void setViewMode(ViewMode viewMode) {
        this.viewMode = viewMode;
    }

    public void setCycleTime(LocalTime cycleTime) {
        this.cycleTime = cycleTime;
    }

    public void setCurrentDay(LocalDate currentDay) {
        this.currentDay = currentDay;
    }

    public void setNextCycleTime(LocalDateTime nextCycleTime) {
        this.nextCycleTime = nextCycleTime;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public int getGoal() {
        return goal;
    }

    public int getOverloadAmount() {
        return overloadAmount;
    }

    public int getProgressPercentage() {
        return progressPercentage;
    }

    public int getStartingAmount() {
        return startingAmount;
    }

    public int getStepAmount() {
        return stepAmount;
    }

    public ProgressType getProgressType() {
        return progressType;
    }

    public ViewMode getViewMode() {
        return viewMode;
    }

    public String getTitle() {
        return title;
    }

    public String getUnit() {
        return unit;
    }

    public LocalTime getCycleTime() {
        return cycleTime;
    }

    public LocalDate getCurrentDay() {
        return currentDay;
    }

    public LocalDateTime getNextCycleTime() {
        return nextCycleTime;
    }

    public List<HabitSnapshot> getHistory() {
        return this.history;
    }

    public List<Tag> getTags() {
        return tags;
    }
}
