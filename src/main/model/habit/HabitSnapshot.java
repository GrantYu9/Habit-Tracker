package model.habit;

import java.util.List;

import model.organization.Tag;

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
        String unit
    ) {
        // !!!
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

    public String getUnit() {
        return unit;
    }
}
