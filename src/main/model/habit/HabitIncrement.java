package model.habit;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/*
A habit that advances in natural number steps towards a goal
 */
public class HabitIncrement extends Habit {
    /*
     * public enum ProgressType {
     * UNDERDONE, currentAmount < goal
     * DONE, currentAmount == goal
     * OVERLOADED currentAmount > goal
     * }
     */

    /*
     * REQUIRES:
     * Inherits requirements of parent class
     * startingAmount < goal
     * EFFECTS:
     * Instantiates a habit in accordance with the Habit abstract class constructor
     */
    public HabitIncrement(
            int goal,
            int startingAmount,
            int stepAmount,
            String title,
            LocalTime cycleTime,
            LocalDate currentDay,
            LocalDateTime marker,

            HabitCycleManager habitCycleManager) {
        super(goal, startingAmount, stepAmount, title, cycleTime, currentDay,
                marker, habitCycleManager);
    }

    @Override
    /*
     * MODIFIES:
     * this
     * EFFECTS:
     * this.currentAmount += this.stepAmount
     * if currentAmount < goal
     * Calculates and sets new progress percentage
     * else if currentAmount == goal
     * progressType = DONE
     * Sets new progress percentage to 100
     * else, currentAmount > goal
     * progressType = OVERLOADED
     * Sets new progress percentage to 100
     * Calculates and sets new overload amount
     */
    public void progressByStepAmount() {
        setCurrentAmountLogic(getCurrentAmount() + getStepAmount());
    }

    @Override
    // REQUIRES: currentAmount > goal
    // EFFECTS: Calculutes how much currentAmount is above goal, as a natural number
    public int calculateOverloadAmount(int currentAmount, int goal) {
        return Math.abs(currentAmount - goal);
    }

    @Override
    // REQUIRES: startingAmount < currentAmount < goal
    // EFFECTS: Calculates how much progress has been made towards the goal as a
    // natural number percentage
    public int calculateProgressPercentage(int startingAmount, int currentAmount, int goal) {
        return Math.abs((100 * (currentAmount - startingAmount)) / (goal - startingAmount));
    }

    @Override
    /*
     * REQUIRES: currentAmount >= startingAmount
     * EFFECTS:
     * if currentAmount == startingAmount
     * Sets new progress percentage to 0
     * Sets overloaded amount to 0
     * progressType = UNDERDONE
     * else if currentAmount < goal
     * Calculates and sets new progress percentage
     * Sets overloaded amount to 0
     * progressType = UNDERDONE
     * else if currentAmount == goal
     * progressType = DONE
     * Sets overloaded amount to 0
     * Sets new progress percentage to 100
     * else, currentAmount > goal
     * progressType = OVERLOADED
     * Calculates and sets new overload amount
     */
    public void setCurrentAmountLogic(int currentAmount) {
        setCurrentAmountNoLogic(currentAmount);

        if (getCurrentAmount() == getStartingAmount()) {
            setProgressPercentage(0);
            setOverloadAmount(0);
            setProgressType(ProgressType.UNDERDONE);
        } else if (getCurrentAmount() < getGoal()) {
            setProgressPercentage(calculateProgressPercentage(getStartingAmount(), currentAmount, getGoal()));
            setOverloadAmount(0);
            setProgressType(ProgressType.UNDERDONE);
        } else if (getCurrentAmount() == getGoal()) {
            setOverloadAmount(0);
            setProgressPercentage(100);
            setProgressType(ProgressType.DONE);
        } else {
            setProgressPercentage(100);
            setOverloadAmount(calculateOverloadAmount(currentAmount, getGoal()));
            setProgressType(ProgressType.OVERLOADED);
        }
    }
}
