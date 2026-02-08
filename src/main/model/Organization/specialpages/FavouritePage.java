package model.organization.specialpages;

import model.habit.Habit;

import java.util.ArrayList;
import java.util.List;

/*
Special types of pages that have limited functionality
They will appear in the taskbar on the left
 */
public class FavouritePage {
    private PageType pageType;
    private enum PageType {
        ALL_HABITS,
        HOME,
        FAVOURITE,
        TAG
    }

    private String title;

    private List<Habit> habits;

    /*
    EFFECTS: 
    Intantiates ReadOnlyPage based on the intended pageType, and brings forth
    their existence on the UI
        if pageType = ALL_Habits
            title = "All Habits"
            !!!
        if 
     */
    public FavouritePage(PageType pageType, List<Habit> habits) {
        // !!!
    }

    // !!!
    private void addHabit(Habit habit) {
        // !!!
    }
}
