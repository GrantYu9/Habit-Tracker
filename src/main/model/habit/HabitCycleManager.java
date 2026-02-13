package model.habit;


import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import model.organization.specialpages.AllHabitsPage;

// Manages when the habits need to cycle
public class HabitCycleManager {
    List<Habit> habits;

    /*
    Effects:
    Instantiates HabitManager such that
    For each habit, 
     */
    public HabitCycleManager() {
        // !!!
    }

    /*
    REQUIRES:
    The app must be closed and reopened again
    MODIFIES:
    habit
    EFFECTS:
    Determines if we should reset the habit and if so, by how many times
    If nextCycleTime was earlier than now
        Finds the time difference from lastTime and now and adds the appropriate amount of HabitSnapshots in history
    Regardless,
        Calculates nextCycleTime and sets this.nextCycleTime to the new one
        Also sets day to new, appropriate day
     */
    public void cycleHabitAtStartup(Habit habit, LocalDateTime lastTime) {
        // !!!
    }

        /*
    REQUIRES:
    The app must be running
    MODIFIES:
    habit
    EFFECTS:
    Instantiates an instance of HabitSnapshot with certain data of this
        currentAmount
        goal
        overloadAmount
        progressPercentage
        startingAmount
        progressType
        unit
    Adds the instance to history
    Resets this such that
        this.currentAmount = startingAmount
        overloadAmount = 0
        progressPercentage = 0
        progressType = UNDERDONE
    Calculates nextCycleTime and sets this.nextCycleTime to the new one
     */
    public void cycleHabitWhileRunning(Habit habit) {
        // !!!
    }

    /*
    MODIFIES:
    this
    EFFECTS:
    Based off of now, calculates the next time the habit should cycle
     */
    public LocalDateTime calculateNextCycleTime() {
        // !!!
        return LocalDateTime.now(); // stub
    }

    // MODIFIES: this
    // EFFECTS: Appends habit to habits
    public void addToHabit(Habit habit) {
        habits.add(habit);
    }

    public List<Habit> getHabits() {
        return habits;
    }
}
