package model.habit;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import model.habit.Habit.ProgressType;
import model.organization.centralization.AllHabitsPage;

// !!! logic issues with habitsnapshots
// Manages when the habits need to cycle
public class HabitCycleManager {
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    private List<Habit> habits; // List of habits to manage

    private LocalDateTime lastTime; // Last time program was up

    /*
     * EFFECTS:
     * Instantiates HabitManager such that
     * habits = allHabitsPage.getHabits
     * this.lastTime = lastTime
     */
    public HabitCycleManager(AllHabitsPage allHabitsPage, LocalDateTime lastTime) {
        habits = allHabitsPage.getHabits();
        this.lastTime = lastTime;
    }

    // MODIFIES: this
    // EFFECTS: For each habit, calls calculateDelay and scheduleHabit
    public void scheduleAllHabits(LocalDateTime marker) {
        for (Habit habit : habits) {
            scheduleHabit(habit, calculateDelay(habit, marker), marker);
        }
    }

    // MODIFIES: this
    // EFFECTS: For each habit, calls cycleHabitAtStartup
    public void cycleAllHabitsAtStartup(LocalDateTime marker) {
        for (Habit habit : habits) {
            cycleHabitAtStartup(habit, marker);
        }
    }

    // EFFECTS: Sets a ScheduledExecutor for cycleHabitWhileRunning with the given
    // delay
    public void scheduleHabit(Habit habit, Duration duration, LocalDateTime marker) {
        executor.schedule(() -> cycleHabitWhileRunning(habit, marker), duration.toSeconds(), TimeUnit.SECONDS);
    }

    /*
     * MODIFIES:
     * habit
     * EFFECTS:
     * If nextCycleTime was earlier than a marker by >= 2 days, calls updateHabit
     * Else if nextCycleTime was earlier than a marker, calls resetHabit
     * Calls updateHabitTimes
     */
    public void cycleHabitAtStartup(Habit habit, LocalDateTime marker) {
        LocalDateTime nextCycleTime = habit.getNextCycleTime();

        if (nextCycleTime.isBefore(marker.minusDays(2))) {
            updateHabit(habit, marker);
        } else if (nextCycleTime.isBefore(marker)) {
            resetHabit(habit);
        }

        updateHabitTimes(habit, marker);
    }

    // MODIFIES: habit
    // EFFECTS: Calls resetHabit, updateHabitTimes, calculateDelay, and
    // scheduleHabit
    public void cycleHabitWhileRunning(Habit habit, LocalDateTime marker) {
        resetHabit(habit);
        updateHabitTimes(habit, marker);
        scheduleHabit(habit, calculateDelay(habit, marker), marker);
    }

    /*
     * MODIFIES:
     * habit
     * EFFECTS:
     * Instantiates HabitSnapshot and adds it to history
     * Resets this such that, of habit
     * currentDay is incremented by one day
     * currentAmount = startingAmount
     * overloadAmount = 0
     * progressPercentage = 0
     * progressType = UNDERDONE
     */
    public void resetHabit(Habit habit) {
        habit.addToHistory(new HabitSnapshot(habit.getCurrentAmount(), habit.getGoal(),
                habit.getOverloadAmount(), habit.getProgressPercentage(), habit.getStartingAmount(),
                habit.getStepAmount(), habit.getProgressType(), habit.getCurrentDay(), habit.getUnit()));

        habit.setCurrentDay(habit.getCurrentDay().plusDays(1));
        habit.setCurrentAmountLogic(habit.getStartingAmount());
        habit.setOverloadAmount(0);
        habit.setProgressPercentage(0);
        habit.setProgressType(ProgressType.UNDERDONE);
    }

    /*
     * REQUIRES:
     * The time gap between currentDay and a marker in habit must be greater than a
     * day
     * MODIFIES:
     * habit
     * EFFECTS:
     * For the first day, calls resetHabit
     * For each subsequent day, inserts a blank HabitSnapshot, incrementing the day
     * appropriately for each one
     */
    public void updateHabit(Habit habit, LocalDateTime marker) {
        long days = ChronoUnit.DAYS.between(habit.getCurrentDay(), marker.toLocalDate());

        resetHabit(habit);

        for (long i = days - 1; i > 0; i--) {
            habit.addToHistory(new HabitSnapshot(habit.getCurrentAmount(), habit.getGoal(),
                    habit.getOverloadAmount(), habit.getProgressPercentage(), habit.getStartingAmount(),
                    habit.getStepAmount(), habit.getProgressType(), habit.getCurrentDay(), habit.getUnit()));
            habit.setCurrentDay(habit.getCurrentDay().plusDays(1));
        }
    }

    /*
     * MODIFIES:
     * habit
     * EFFECTS:
     * Calls calculateNextCycleTime
     * Sets nextCycleTime to the new one
     * Sets currentDay to today
     */
    public void updateHabitTimes(Habit habit, LocalDateTime marker) {
        habit.setNextCycleTime(calculateNextCycleTime(habit, marker));
        habit.setCurrentDay(LocalDate.now());
    }

    // EFFECTS: Calculates time difference between a time and nextCycleTime
    public Duration calculateDelay(Habit habit, LocalDateTime marker) {
        return Duration.between(marker, habit.getNextCycleTime());
    }

    // MODIFIES: this
    // EFFECTS: Based off of a marker, calculates the next time the habit should
    // cycle
    public LocalDateTime calculateNextCycleTime(Habit habit, LocalDateTime marker) {
        LocalDateTime supposedCycleTime = LocalDateTime.of(marker.toLocalDate(), habit.getCycleTime());

        if (supposedCycleTime.isBefore(marker)) {
            return supposedCycleTime.plusDays(1);
        }

        return supposedCycleTime;
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
