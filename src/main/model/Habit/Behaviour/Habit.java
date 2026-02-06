package model.Habit.Behaviour;

import java.util.ArrayList;

/*
// !!!
 */
public abstract class Habit {
    private String unit; // Type of units. E.g. mL or steps
    private int goal; // A goal amount. E.g. 500 mL or 10,000 steps
    private int amount; // Current amount. E.g. 100 mL or 1,000 steps
    private int progressPercentage; // Amount of progress made towards a goal
    private ViewMode viewMode; // !!!
    private enum ViewMode {
        BAR,
        HEATMAP
    } // !!!
    private ArrayList<Integer> history; // History of past percentages for heatmap

    public abstract void calculateProgressPercentage(int progressPercentage);
    public abstract void addToHistory(int progressPercentage);

    public abstract void setGoal(int goal);
    public abstract void setAmount(int amount);

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setViewMode(ViewMode viewMode) {
        this.viewMode = viewMode;
    }

    public String getUnit() {
        return this.unit;
    }

    public int getGoal() {
        return this.goal;
    }

    public int getAmount() {
        return this.amount;
    }

    public int getProgressPercentage() {
        return this.progressPercentage;
    }

    public ViewMode getViewMode() {
        return this.viewMode;
    }

    public ArrayList<Integer> getHistory() {
        return history;
    }
}
