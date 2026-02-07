package model.Habit.Behaviour;

/*
!!!
 */
public class HabitDecrement extends Habit{
    private ProgressType progressType; // Progress relative to the goal
    private enum ProgressType {
        UNDERDONE, // !!!
        DONE, // !!!
        OVERLOADED // !!!
    }
   
    /*
    !!!
     */
    public HabitDecrement() {
        super();
        // !!!
    }

    @Override
    /*
    !!!
     */
    public void progressByIncrement() {
        // !!!
    }

    @Override
    // REQUIRES: !!!
    // EFFECTS: calculates how much progress has been made towards the goal
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
