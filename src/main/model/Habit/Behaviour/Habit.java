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
    private int startingAmount; // The starting amount a user sets

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
        this.startingAmount = startingAmount
        this.unit = unit
            With surrounding whitespace trimmed

        currentAmount = 0
        overloadAmount = 0
        progressPercentage = 0
        progressType = UNDERDONE
        viewMode = BAR
    */
    public Habit() {
        // !!!
    }

    public abstract void progressByIncrement();
    public abstract int calculateOverloadAmount(int currentAmount, int goal);
    public abstract int calculateProgressPercentage(int currentAmount, int goal);

    public abstract void setCurrentAmount(int currentAmount);

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

    public int getStartingAmount() {
        return this.startingAmount;
    }

    public int getProgressPercentage() {
        return this.progressPercentage;
    }
    
    public ViewMode getViewMode() {
        return this.viewMode;
    }

    public String getUnit() {
        return this.unit;
    }
}
