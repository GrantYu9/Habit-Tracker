package model.organization.specialpages;

import model.habit.Habit;

import java.util.ArrayList;
import java.util.List;

/*
A page for all habits
 */
public class FavouritePage {
    private String title;

    private List<Habit> habits;

    /*
    EFFECTS:
    Instantiates FavouritePage such that
        title = "Favourites"
        habits = new ArrayList<>()
     */
    public FavouritePage() {
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
