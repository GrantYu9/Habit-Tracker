package model.habit;

import java.text.Collator;
import java.time.Duration;

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
import java.util.Locale;

import model.organization.Tag;
import model.organization.Tag.TagType;
import model.organization.centralization.AllTagPagesPage;
import model.organization.specialpages.FavouritesPage;
import model.organization.specialpages.HomePage;
import model.organization.specialpages.TagPage;

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
    User must add this to AllHabitsPage
    EFFECTS:
    Instantiates a habit such that
        this.goal = goal
        this.startingAmount = startingAmount
        this.stepAmount = stepAmount
        this.title = title, with surrounding whitespace trimmed
        this.cycleTime = cycleTime
        this.currentDay = curentDay

        this.currentAmount = startingAmount
        overloadAmount = 0
        viewMode = BAR
        progressType = UNDERDONE
        unit = ""
        history = new ArrayList
        tags = new ArrayList
    Calculate progressPercentage and set this.progressPercentage to the output

    Calls HabitCycleManager to set nextCycleTime and execute cycleHabitWhileRunning at nextCycleTime
    */
    public Habit(
        int goal, 
        int startingAmount, 
        int stepAmount,
        String title,
        LocalTime cycleTime,
        LocalDate currentDay,

        HabitCycleManager habitCycleManager
    ) {
        this.goal = goal;
        this.startingAmount = startingAmount;
        this.stepAmount = stepAmount;
        this.title = title.strip();
        this.cycleTime = cycleTime;
        this.currentDay = currentDay;

        this.currentAmount = this.startingAmount;
        overloadAmount = 0;
        viewMode = ViewMode.BAR;
        progressType = ProgressType.UNDERDONE;
        unit = "";
        
        history = new ArrayList<>();
        tags = new ArrayList<>();

        this.nextCycleTime = habitCycleManager.calculateNextCycleTime(this, LocalDateTime.now());

        habitCycleManager.scheduleHabit(this, Duration.between(LocalDateTime.now(), nextCycleTime), 
            LocalDateTime.now());
    }

    public abstract int calculateOverloadAmount(int currentAmount, int goal);
    public abstract int calculateProgressPercentage(int startingAmount, int currentAmount, int goal);
    public abstract void progressByStepAmount();
    public abstract void setCurrentAmountLogic(int currentAmount);

    @Override
    /*
    REQUIRES:
    The object must be the same type as this
    EFFECTS:
    Checks equality between two objects
    If the objects point to the same memory address, return true
    Casts the object into Habit and returns whether all the fields are the same
     */
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }

        if (this == object) {
            return true;
        }

        Habit habit = (Habit) object;

        return this.currentAmount == habit.getCurrentAmount() &&
            this.goal == habit.getGoal() &&
            this.overloadAmount == habit.getOverloadAmount() &&
            this.progressPercentage == habit.getProgressPercentage() &&
            this.startingAmount == habit.getStartingAmount() &&
            this.stepAmount == habit.getStepAmount() &&
            this.progressType == habit.getProgressType() &&
            this.title.equals(habit.getTitle()) &&
            this.unit.equals(habit.getUnit()) &&
            this.cycleTime.equals(habit.getCycleTime()) &&
            this.currentDay.equals(habit.getCurrentDay()) &&
            this.nextCycleTime.isEqual(habit.getNextCycleTime());
    }

    /*
    REQUIRES:
    There can neither be two home tags nor two favourite tags
    MODIFIES:
    this, homePage, favouritesPage
    EFFECTS:
    If tag not in tags, appends tag to tags
    Special cases
        If tag.getTagType == FAVOURITE, adds habit to FavouritePage
        If tag.getTagType == HOME, adds habit to HomePage
    If not a special tag, instantiates an instance of tagPage
    Sorts tags by alphabetical order, with Favourites tag being first, and Home tag being second, if they exist
     */
    public void addTagAndSortTags(Tag tag, HomePage homePage, FavouritesPage favouritesPage, 
        AllTagPagesPage allTagPagesPage) {
        if (!tags.contains(tag)) {
            tags.add(tag);
        }

        if (tag.getTagType() == TagType.FAVOURITE) {
            favouritesPage.addToFavouritesPage(this);
            tags.remove(tag);
            tags.add(0, tag);
        } else if (tag.getTagType() == TagType.HOME) {
            homePage.addToHomePage(this);
            if (tags.get(0).getTitle().equals("Favourite")) {
                tags.remove(tag);
                tags.add(1, tag);
            } else {
                tags.remove(tag);
                tags.add(0, tag);
            }
        } else {
            TagPage tagPage = new TagPage(tag);
            tagPage.addToTagPage(this);
        }

        Collator dictionary = Collator.getInstance(Locale.ENGLISH);

        tags.sort(Comparator.comparing((Tag t) -> t.getTagType()).thenComparing(t -> t.getTitle(), dictionary));
    }

    // EFFECTS: Determines if tags contains a tag of type tagType
    public boolean containsTagType(TagType tagType) {
        for (Tag tag : tags) {
            if (tag.getTagType() == tagType) {
                return true;
            }
        }

        return false;
    }

    // MODIFIES: this
    // EFFECTS: Appends HabitSnapshot to history
    public void addToHistory(HabitSnapshot habitSnapshot) {
        history.add(habitSnapshot);
    }

    // REQUIRES: stepAmount > 0
    public void setStepAmount(int incrementAmount) {
        this.stepAmount = incrementAmount;
    }

    // REQUIRES: title has at least one character
    // EFFECTS: this.title = title, with surrounding whitespace trimmed
    public void setTitle(String title) {
        this.title = title.strip();
    }

    // REQUIRES: unit has at least one character
    // EFFECTS: this.unit = unit, with surrounding whitespace trimmed
    public void setUnit(String unit) {
        this.unit = unit.strip();
    }

    public void setCurrentAmountNoLogic(int currentAmount) {
        this.currentAmount = currentAmount;
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
