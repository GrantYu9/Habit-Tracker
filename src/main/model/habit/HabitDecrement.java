package model.habit;

import model.organization.specialpages.AllHabitsPage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import model.habit.Habit.ProgressType;

/*
A habit that advances in negative integer steps towards a goal
 */
public class HabitDecrement extends Habit {
    /*
    private enum ProgressType {
        UNDERDONE, currentAmount > goal
        DONE, currentAmount == goal
        OVERLOADED currentAmount < goal
    }
     */
   
    /*
    REQUIRES:
    Inherits requirements of parent class
    startingAmount > goal
    EFFECTS:
    Instantiates a habit in accordance with the Habit abstract class constructor
     */
    public HabitDecrement(
        int goal, 
        int startingAmount, 
        int stepAmount, 
        String title, 
        String unit, 
        LocalTime cycleTime,
        LocalDate currentDay,
        AllHabitsPage allHabitsPage,
        HabitCycleManager habitCycleManager
    ) {
        super(goal, startingAmount, stepAmount, title, unit, cycleTime, currentDay, allHabitsPage, 
            habitCycleManager);
    }

    @Override
    /*
    MODIFIES:
    this
    EFFECTS:
    this.currentAmount -= this.stepAmount
    if currentAmount < goal
        Calculates and sets new progress percentage
    else if currentAmount == goal
        progressType = DONE
        Sets new progress percentage to 100
    else, currentAmount > goal
        progressType = OVERLOADED
        Calculates and sets new overload amount
     */
    public void progressByStepAmount() {
        // !!!
    }

    @Override
    // REQUIRES: currentAmount < goal
    // EFFECTS: Calculutes how much currentAmount is below goal, as a natural number
    public int calculateOverloadAmount(int currentAmount, int goal) {
        return 0;
    }

    @Override
    // REQUIRES: goal <= currentAmount <= startingAmount
    // EFFECTS: Calculates how much progress has been made towards the goal as a natural number percentage
    public int calculateProgressPercentage(int startingAmount, int currentAmount, int goal) {
        return 0;
    }

    @Override
    /*
    REQUIRES: currentAmount <= startingAmount
    EFFECTS:
    if currentAmount == startingAmount
        Sets new progress percentage to 0
    else if currentAmount > goal
        Calculates and sets new progress percentage
    else if currentAmount == goal
        progressType = DONE
        Sets new progress percentage to 100
    else, currentAmount < goal
        progressType = OVERLOADED
        Calculates and sets new overload amount
     */
    public void setCurrentAmount(int currentAmount) {
        // !!!
    }
}
