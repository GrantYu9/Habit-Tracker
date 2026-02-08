package model.organization.specialpages;

import model.habit.Habit;

import java.util.ArrayList;
import java.util.List;

/*
A page for all habits
 */
public class HomePage {
    private String title;

    private List<Habit> habits;

    /*
    EFFECTS:
    Instantiates HomePage such that
        title = "HomePage"
        habits = new ArrayList<>()
     */
    public HomePage() {
        // !!!
    }

    // MODIFIES: this
    // EFFECTS: appends habit to habits
    public void addToHomePage(Habit habit) {
        // !!!
    }

    public List<Habit> getHabits() {
        return habits;
    }
}
