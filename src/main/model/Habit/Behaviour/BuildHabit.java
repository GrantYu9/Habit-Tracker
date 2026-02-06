package model.Habit.Behaviour;

/*
A habit that increments in positive integer steps towards a goal
 */
public class BuildHabit extends Habit {
    /*
    REQUIRES:
    !!!
    0 < increment amount <= goal
    goal > 0
    unit has at least one alphabetical character
    EFFECTS:
    Instatiates a habit such that
        !!!
     */
    public BuildHabit(int incrementAmount, int goal, String unit) {
        super(incrementAmount, goal, unit);
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
        does not calculateProgressPercentage()
    calculateProgressPercentage()
     */
    public void progressByIncrement() {
        // !!!
    }

    @Override
    /*
    !!!
     */
    public void calculateProgressPercentage(int currentAmount, int goal) {
        // !!!
    }

    @Override
    /*
    !!!
     */
    public void calculateOverloadAmount(int currentAmount, int goal) {
        // !!!
    }

    @Override
    // REQUIRES: currentAmount > 0
    public void setCurrentAmount(int currentAmount) {
        // !!!
    }

    @Override
    // REQUIRES: goal > 0
    public void setGoal(int goal) {
        // !!!
    }
}
