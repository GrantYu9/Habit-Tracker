package model.Habit.Behaviour;

/*
A habit that advances in negative integer steps towards a goal
 */
public class HabitDecrement extends Habit{
    private ProgressType progressType; // Progress relative to the goal
    private enum ProgressType {
        UNDERDONE, // currentAmount > goal
        DONE, // currentAmount == goal
        OVERLOADED // currentAmount < goal
    }
   
    /*
    REQUIRES:
    Inherits requirements of parent class
    currentAmount > goal
    EFFECTS:
    Instantiates a habit in accordance with the Habit abstract class constructor
     */
    public HabitDecrement() {
        super();
        // !!!
    }

    @Override
    /*
    !!!
     */
    public void progressByStepAmount() {
        // !!!
    }


    @Override
    // !!!
    public int calculateProgressPercentage(int currentAmount, int goal) {
        return 0;
    }

    @Override
    /*
    !!!
     */
    public int calculateOverloadAmount(int currentAmount, int goal) {
        return 0;
    }

    @Override
    // REQUIRES: currentAmount < startingAmount
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
