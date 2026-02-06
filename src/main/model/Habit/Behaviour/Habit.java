package model.Habit.Behaviour;

import java.util.ArrayList;
import java.util.List;

/*
An abstract class for BuildHabit and BreakHabit
Features basic getters and setters
Leaves most of the more complex logic for derived classes, which have class specific behaviour
 */
public abstract class Habit {
    private int currentAmount; // Current amount. E.g. 100 mL or 1,000 steps
    private int incrementAmount; // How much one wants to increment by
    private int goal; // A goal amount
    private int overloadAmount; // How much a habit is overloaded
    private int progressPercentage; // Amount of progress made towards a goal

    private ProgressType progressType; // Progress relative to the goal
    private enum ProgressType {
        UNDERDONE, // User has yet to reach their goal
        DONE, // User has reached their goal
        OVERLOADED // User is going above and beyond their goal
    }
    private ViewMode viewMode; // How one can view the habit
    private enum ViewMode {
        BAR, // The default way to view a habit and how one can interact with it
        HEATMAP // The heatmap to view history
    }

    private String unit; // Type of units. E.g. mL or steps

    /*
    EFFECTS:
    Instantiates a habit such that
        this.incrementAmount = incrementAmount
        this.goal = goal
        this.unit = unit
            With surrounding whitespace trimmed

        currentAmount = 0
        overloadAmount = 0
        progressPercentage = 0
        progressType = UNDERDONE
        viewMode = BAR
        history = new ArrayList<>()
    */
    public Habit(int incrementAmount, int goal, String unit) {
        // !!!
    }

    public abstract void progressByIncrement();
    public abstract void calculateProgressPercentage(int currentAmount, int goal);
    public abstract void calculateOverloadAmount(int currentAmount, int goal);

    public abstract void setCurrentAmount(int currentAmount);
    public abstract void setGoal(int goal);

    public void setOverloadAmount(int overloadAmount) {
        this.overloadAmount = overloadAmount;
    }

    public void setProgressPercentage(int progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public void setProgressType(ProgressType progressType) {
        this.progressType = progressType;
    }

    public void setViewMode(ViewMode viewMode) {
        this.viewMode = viewMode;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
    
    public int getCurrentAmount() {
        return this.currentAmount;
    }

    public int getIncrementAmount() {
        return this.incrementAmount;
    }

    public int getGoal() {
        return this.goal;
    }

    public int getOverloadAmount() {
        return this.overloadAmount;
    }

    public int getProgressPercentage() {
        return this.progressPercentage;
    }

    public ProgressType getProgressType() {
        return this.progressType;
    }

    public ViewMode getViewMode() {
        return this.viewMode;
    }

    public String getUnit() {
        return this.unit;
    }
}
