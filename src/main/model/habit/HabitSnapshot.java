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
        if (this == object) {
            return true;
        }

        HabitSnapshot habitSnapshot = (HabitSnapshot) object;

        return this.currentAmount == habitSnapshot.getCurrentAmount() &&
            this.goal == habitSnapshot.getGoal() &&
            this.overloadAmount == habitSnapshot.getOverloadAmount() &&
            this.progressPercentage == habitSnapshot.getProgressPercentage() &&
            this.startingAmount == habitSnapshot.getStartingAmount() &&
            this.stepAmount == habitSnapshot.getStepAmount() &&
            this.progressType == habitSnapshot.getProgressType() &&
            this.unit.equals(habitSnapshot.getUnit()) &&
            this.day.equals(habitSnapshot.getDay());
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

    public LocalDate getDay() {
        return day;
    }

    public String getUnit() {
        return unit;
    }
}
