package model.organization.specialpages;

import model.habit.Habit;

import java.util.ArrayList;
import java.util.List;

// A page for all habits
public class AllHabitsPage {
    private String title;

    private List<Habit> habits;

    /*
    EFFECTS:
    Instantiates AllHabitsPage such that
        title = "All Habits"
        habits = new ArrayList<>()
     */
    public AllHabitsPage() {
        // !!!
    }

    // MODIFIES: this
    // EFFECTS: appends habit to habits
    public void addToAllHabitsPage(Habit habit) {
        // !!!
    }

    public List<Habit> getHabits() {
        return habits;
    }
}
