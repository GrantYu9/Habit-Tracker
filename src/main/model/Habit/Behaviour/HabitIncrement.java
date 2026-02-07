package model.Habit.Behaviour;

/*
A habit that advances in natural number steps towards a goal
 */
public class HabitIncrement extends Habit {
    private ProgressType progressType; // Progress relative to the goal
    private enum ProgressType {
        UNDERDONE, // currentAmount < goal
        DONE, // currentAmount == goal
        OVERLOADED // currentamount > goal
    }
    /*
    REQUIRES:
    Inherits requirements of parent class
    currentAmount < goal
    EFFECTS:
    Instantiates a habit in accordance with the Habit abstract class constructor
     */
    public HabitIncrement() {
        super();
        // !!!
    }

    @Override
    /*
    MODIFIES:
    this
    EFFECTS:
    this.currentAmount += this.stepAmount
    if currentAmount < goal
        calculates and sets new progress percentage
    if currentAmount == goal
        progressType = DONE
        calculates and sets new progress percentage
    if currentAmount > goal
        progressType = OVERLOADED
        calculates and sets new overload amount
     */
    public void progressByStepAmount() {
        // !!!
    }

    @Override
    // REQUIRES: currentAmount <= goal
    // EFFECTS: calculates how much progress has been made towards the goal as an integer percentage
    public int calculateProgressPercentage(int currentAmount, int goal) {
        return 0;
    }

    @Override
    // REQUIRES: currentAmount > goal
    // EFFECTS: calculutes how much currentAmount is above goal
    public int calculateOverloadAmount(int currentAmount, int goal) {
        return 0;
    }

    @Override
    // REQUIRES: currentAmount > startingAmount
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
