package model.habit;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import model.organization.Tag;

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

    private ViewMode viewMode; // How one can view the habit
    private enum ViewMode {
        BAR, // The default way to view a habit and how one can interact with it
        HEATMAP // The heatmap to view history
    }
    private ProgressType progressType; // Progress relative to the goal
    /*
    Warning: The behaviour of this enum is different between the derived classes.
    As such, please read the documentation that will be provided.
    The documentation will be located near the tops of each derived class, where the
    variables would be declared.
     */
    private enum ProgressType {
        UNDERDONE,
        DONE,
        OVERLOADED
    }

    private String title; // A title for the habit
    private String unit; // Type of units. E.g. mL or steps

    private LocalDate day; // Day of the habit
    private ZonedDateTime cycleTime; // Time that the habit resets every day

    private List<Habit> history; // A record of past data of the habit
    private List<Tag> tags; // Labels that can be attached to the habit for organization

    /*
    REQUIRES:
    0 < stepAmount <= |goal|
    title has at least one character
    unit has at least one character
    EFFECTS:
    Instantiates a habit such that
        this.currentAmount = startingAmount
        this.goal = goal
        this.startingAmount = startingAmount
        this.stepAmount = stepAmount
        this.title = title, with surrounding whitespace trimmed
        this.unit = unit, with surrounding whitespace trimmed
        this.cycleTime = cycleTime

        overloadAmount = 0
        progressPercentage = 0
        viewMode = BAR
        day = LocalDate.now()
        history = new ArrayList<>()
    Adds Habit to AllHabitsPage
    Calls cycleHabit() every time cycleTime occurs
    */
    public Habit(int goal, int startingAmount, int stepAmount, String title, String unit, ZonedDateTime cycleTime) {
        // !!!
    }

    public abstract int calculateOverloadAmount(int currentAmount, int goal);
    public abstract int calculateProgressPercentage(int startingAmount, int currentAmount, int goal);
    public abstract void progressByStepAmount();

    public abstract void setCurrentAmount(int currentAmount);

    /*
    REQUIRES:
    tag has at least one alphabetical character
    MODIFIES:
    this
    EFFECTS:
    appends tag to tags, with surrounding whitespace removed
    sorts tags by alphabetical order
     */
    public void addTag(Tag tag) {
        // !!!
    }

    /*
    EFFECTS:
    Adds Habit to history
    Resets Habit such that
        this.currentAmount = startingAmount

        overloadAmount = 0
        progressPercentage = 0
     */
    public void cycleHabit() {
        // !!!
    }

    // REQUIRES: incrementAmount > 0
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

    public void setViewMode(ViewMode viewMode) {
        this.viewMode = viewMode;
    }

    public void setProgressType(ProgressType progressType) {
        this.progressType = progressType;
    }

    public void setCycleTime(ZonedDateTime cycleTime) {
        this.cycleTime = cycleTime;
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
    
    public ViewMode getViewMode() {
        return viewMode;
    }

    public ProgressType getProgressType() {
        return progressType;
    }

    public String getTitle() {
        return title;
    }

    public String getUnit() {
        return unit;
    }

    public LocalDate getDay() {
        return day;
    }

    public ZonedDateTime getCycleTime() {
        return cycleTime;
    }

    public List<Habit> getHistory() {
        return this.history;
    }

    public List<Tag> getTags() {
        return tags;
    }
}
