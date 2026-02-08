package model.organization.specialpages;

import model.habit.Habit;

import java.util.ArrayList;
import java.util.List;

/*
A page for favourite habits
 */
public class FavouritesPage {
    private String title;

    private List<Habit> habits;

    /*
    EFFECTS:
    Instantiates FavouritePage such that
        title = "Favourites"
        habits = new ArrayList<>()
     */
    public FavouritesPage() {
        // !!!
    }

    // MODIFIES: this
    // EFFECTS: appends habit to habits
    public void addToFavouritesPage(Habit habit) {
        // !!!
    }

    public List<Habit> getHabits() {
        return habits;
    }
}
