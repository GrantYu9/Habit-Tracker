package model.Habit.Behaviour;

/*
A habit that increments in positive integer steps towards a goal
 */
public class HabitIncrement extends Habit {
    private ProgressType progressType; // Progress relative to the goal
    private enum ProgressType {
        UNDERDONE, // !!!
        DONE, // !!!
        OVERLOADED // !!!
    }
    /*
    REQUIRES:
    0 < increment amount <= goal
    goal > 0
    unit has at least one alphabetical character
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
    increases this.currentAmount by this.incrementAmount
    if currentAmount == goal, progressType = DONE
    if currentAmount > goal, progressType > OVERDONE
        calculateOverloadAmount()
            !!!
        does not calculateProgressPercentage()
    calculateProgressPercentage()
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
