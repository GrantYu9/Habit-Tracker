package model.habit;

import java.time.LocalDate;

import model.habit.Habit.ProgressType;

/*
A snapshot of a day of a habit, for history field of Habit abstract class
A helper class of sorts for Habit abstract class
Read only
 */
public class HabitSnapshot {
    private int currentAmount; // Current amount. E.g. 100 mL or 1,000 steps
    private int goal; // A goal amount
    private int overloadAmount; // How much a habit is overloaded
    private int progressPercentage; // Amount of progress made towards a goal
    private int startingAmount; // The starting amount a user sets
    private int stepAmount; // How much one wants to advance by; stepAmount > 0

    private ProgressType progressType; // Progress relative to the goal
    
    private LocalDate day; // Day of the habit

    private String unit; // Type of units. E.g. mL or steps

    // EFFECTS: Instantiates HabitSnapshot such that the fields are the same as the respective operands
    public HabitSnapshot(
        int currentAmount,
        int goal,
        int overloadAmount,
        int progressPercentage,
        int startingAmount,
        int stepAmount,
        ProgressType progressType,
        LocalDate day,
        String unit
    ) {
        this.currentAmount = currentAmount;
        this.goal = goal;
        this.overloadAmount = overloadAmount;
        this.progressPercentage = progressPercentage;
        this.startingAmount = startingAmount;
        this.stepAmount = stepAmount;
        this.progressType = progressType;
        this.day = day;
        this.unit = unit;
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

    public LocalDate getLocalDate() {
        return day;
    }

    public String getUnit() {
        return unit;
    }
}
