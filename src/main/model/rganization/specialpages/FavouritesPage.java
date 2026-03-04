package model.organization.specialpages;

import model.habit.Habit;

import java.util.ArrayList;
import java.util.List;

// A page for favourite habits
public class FavouritesPage {
    private String title;

    private List<Habit> habits;

    /*
    EFFECTS:
    Instantiates FavouritePage such that
        title = "Favourites"
        habits = new ArrayList
     */
    public FavouritesPage() {
        title = "Favourites";

        habits = new ArrayList<>();
    }

    // REQUIRES: habit not already in habits
    // MODIFIES: this
    // EFFECTS: appends habit to habits
    public void addToFavouritesPage(Habit habit) {
        habits.add(habit);
    }

    public String getTitle() {
        return title;
    }

    public List<Habit> getHabits() {
        return habits;
    }
}
