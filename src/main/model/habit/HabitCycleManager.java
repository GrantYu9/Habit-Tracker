package model.habit;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import model.organization.centralization.AllHabitsPage;

// Manages when the habits need to cycle
public class HabitCycleManager {
    List<Habit> habits; // List of habits to manage

    LocalDateTime lastTime; // Last time program was up

    /*
    EFFECTS:
    Instantiates HabitManager such that 
        habits = allHabitsPage
        this.lastTime = lastTime
     */
    public HabitCycleManager(AllHabitsPage allHabitsPage, LocalDateTime lastTime) {
        // !!!
    }

    // MODIFIES: this
    // EFFECTS: For each habit, calls calculateDelay and scheduleHabit
    public void scheduleAllHabits() {
        // !!!
    }

    // MODIFIES: this
    // EFFECTS: For each habit, calls cycleHabitAtStartup
    public void cycleAllHabitsAtStartup() {
        // !!!
    }

    // EFFECTS: Sets a ScheduledExecutor for cycleHabitWhileRunning with the given delay
    public void scheduleHabit(Habit habit, Duration duration) {
        // !!!
    }

    /*
    MODIFIES:
    habit
    EFFECTS:
    If nextCycleTime was earlier than now by < 2 days, calls resetHabit
    Else if nextCycleTime was earlier than now by >= 2 days, calls updateHabitHistory
    Calls updateHabitTimes
     */
    public void cycleHabitAtStartup(Habit habit) {
        // !!!
    }

    // MODIFIES: habit
    // EFFECTS: Calls resetHabit, updateHabitTimes, and scheduleHabit
    public void cycleHabitWhileRunning(Habit habit) {
        // !!!
    }

    /*
    MODIFIES:
    habit
    EFFECTS:
    Instantiates HabitSnapshot and adds it to history
    Resets this such that
        currentDay is incremented by one day
        this.currentAmount = startingAmount
        overloadAmount = 0
        progressPercentage = 0
        progressType = UNDERDONE
     */
    public void resetHabit(Habit habit) {
        // !!!
    }

    /*
    REQUIRES:
    The time gap between currentDay and now in habit must be greater than a day
    MODIFIES:
    habit
    EFFECTS:
    For the first day, calls resetHabit
    For each subsequent day, inserts a blank HabitSnapshot, incrementing the day appropriately for each one
     */
    public void updateHabit(Habit habit) {
        // !!!
    }

    /*
    MODIFIES:
    habit
    EFFECTS:
    Calls calculateNextCycleTime
    Sets nextCycleTime to the new one
    Sets currentDay to today
     */
    public void updateHabitTimes(Habit habit) {
        // !!!
    }

    // EFFECTS: Calculates time difference between now and nextCycleTime
    public Duration calculateDelay(Habit habit) {
        // !!!
        return Duration.ZERO; // stub
    }

    // MODIFIES: this
    // EFFECTS: Based off of now, calculates the next time the habit should cycle
    public LocalDateTime calculateNextCycleTime(Habit habit) {
        // !!!
        return LocalDateTime.now(); // stub
    }

    public void setLastTime(LocalDateTime lastTime) {
        this.lastTime = lastTime;
    }

    public LocalDateTime getLastTime() {
        return lastTime;
    }

    public List<Habit> getHabits() {
        return habits;
    }
}
