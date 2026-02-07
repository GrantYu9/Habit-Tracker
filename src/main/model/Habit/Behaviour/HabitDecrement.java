package model.Habit.Behaviour;

/*
A habit that advances in negative integer steps towards a goal
 */
public class HabitDecrement extends Habit {
    private ProgressType progressType; // Progress relative to the goal
    private enum ProgressType {
        UNDERDONE, // currentAmount > goal
        DONE, // currentAmount == goal
        OVERLOADED // currentAmount < goal
    }
   
    /*
    REQUIRES:
    Inherits requirements of parent class
    startingAmount > goal
    EFFECTS:
    Instantiates a habit in accordance with the Habit abstract class constructor
     */
    public HabitDecrement(int goal, int startingAmount, int stepAmount, String unit) {
        super(goal, startingAmount, stepAmount, unit);
        // !!!
    }

    @Override
    /*
    MODIFIES:
    this
    EFFECTS:
    this.currentAmount -= this.stepAmount
    if currentAmount < goal
        calculates and sets new progress percentage
    else if currentAmount == goal
        progressType = DONE
        sets new progress percentage to 100
    else, currentAmount > goal
        progressType = OVERLOADED
        calculates and sets new overload amount
     */
    public void progressByStepAmount() {
        // !!!
    }

    @Override
    // REQUIRES: currentAmount < goal
    // EFFECTS: calculutes how much currentAmount is below goal, as a natural number
    public int calculateOverloadAmount(int currentAmount, int goal) {
        return 0;
    }

    @Override
    // REQUIRES: goal <= currentAmount < startingAmount
    // EFFECTS: calculates how much progress has been made towards the goal as a natural number percentage
    public int calculateProgressPercentage(int startingAmount, int currentAmount, int goal) {
        return 0;
    }

    @Override
    /*
    REQUIRES: currentAmount <= startingAmount
    EFFECTS:
    if currentAmount == startingAmount
        sets new progress percentage to 0
    else if currentAmount > goal
        calculates and sets new progress percentage
    else if currentAmount == goal
        progressType = DONE
        sets new progress percentage to 100
    else, currentAmount < goal
        progressType = OVERLOADED
        calculates and sets new overload amount
     */
    public void setCurrentAmount(int currentAmount) {
        // !!!
    }

    public void setProgressType(ProgressType progressType) {
        this.progressType = progressType;
    }

    public ProgressType getProgressType() {
        return this.progressType;
    }
}
