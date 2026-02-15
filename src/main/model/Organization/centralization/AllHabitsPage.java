package model.organization.centralization;

import model.habit.Habit;

import java.util.ArrayList;
import java.util.List;

// A page for all habits
public class AllHabitsPage {
    private List<Habit> habits;

    // EFFECTS: Instantiates AllHabitsPage such that habits = new ArrayList
    public AllHabitsPage() {
        habits = new ArrayList<>();
    }

    // REQUIRES: habit is not already in habits
    // MODIFIES: this
    // EFFECTS: appends habit to habits
    public void addToAllHabitsPage(Habit habit) {
        habits.add(habit);
    }

    public List<Habit> getHabits() {
        return habits;
    }
}
