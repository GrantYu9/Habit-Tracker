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

    @Override
    // EFFECTS: Returns true if and only if this and object point to the same
    // location in memory or if their habits share the same fields
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }

        if (this == object) {
            return true;
        }

        AllHabitsPage allHabitsPage = (AllHabitsPage) object;

        if (allHabitsPage.getHabits().size() != habits.size()) {
            return false;
        }

        for (int i = 0; i < habits.size(); i++) {
            if (!habits.get(i).equals(allHabitsPage.getHabits().get(i))) {
                return false;
            }
        }

        return true;
    }
}
