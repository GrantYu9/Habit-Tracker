package model.habit;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
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
    private ViewMode viewMode; // How one can view the habit
    private enum ViewMode {
        BAR, // The default way to view a habit and how one can interact with it
        HEATMAP // The heatmap to view history
    }

    private String title; // A title for the habit
    private String unit; // Type of units. E.g. mL or steps

    private List<Habit> history; // A record of past data of the habit
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

        this.currentAmount = startingAmount
        overloadAmount = 0
        progressPercentage = 0
        viewMode = BAR
        progressType = UNDERDONE
        history = new ArrayList<>()
        tags = new ArrayList<>()
    Adds this to AllHabitsPages
    */
    public Habit(
        int goal, 
        int startingAmount, 
        int stepAmount,
        String title,
        String unit,
        AllHabitsPage allHabitsPage
    ) {
        this.goal = goal;
        this.startingAmount = startingAmount;
        this.stepAmount = stepAmount;
        this.title = title.strip();
        this.unit = unit.strip();

        this.currentAmount = startingAmount;
        overloadAmount = 0;
        progressPercentage = 0;
        viewMode = ViewMode.BAR;
        progressType = ProgressType.UNDERDONE;
        history = new ArrayList<>();
        tags = new ArrayList<>();

        allHabitsPage.addToAllHabitsPage(this);
    }

    public abstract int calculateOverloadAmount(int currentAmount, int goal);
    public abstract int calculateProgressPercentage(int startingAmount, int currentAmount, int goal);
    public abstract void progressByStepAmount();

    public abstract void setCurrentAmount(int currentAmount);

    // !!!
    /*
    EFFECTS:
    Adds this to history
    Removes this from AllHabitsPage
    Calls constructor to make a new habit that acts as a resetted form of this, such that
        this.currentAmount = startingAmount
        overloadAmount = 0
        progressPercentage = 0
        progressType = UNDERDONE

        And all other fields are as is in this
     
    public Habit cycleHabit() {
        history.add(this);
        this.currentAmount = startingAmount;
        overloadAmount = 0;
        progressPercentage = 0;
        progressType = ProgressType.UNDERDONE;
    }
    */

    /*
    MODIFIES:
    this
    EFFECTS:
    appends tag to tags
    sorts tags by alphabetical order
     */
    public void addTagAndSortTags(Tag tag) {
        tags.add(tag);
        tags.sort(Comparator.comparing(t -> t.getTitle()));
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

    public List<Habit> getHistory() {
        return this.history;
    }

    public List<Tag> getTags() {
        return tags;
    }
}
