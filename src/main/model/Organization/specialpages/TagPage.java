package model.organization.specialpages;

import model.organization.Tag;
import model.organization.centralization.AllTagPages;
import model.habit.Habit;

import java.util.ArrayList;
import java.util.List;

// A page for all habits of the same tag
public class TagPage {
    private String title;

    private List<Habit> habits;

    /*
    EFFECTS:
    Instantiates TagPage such that
        title = tag.getTitle()
        habits = new ArrayList<>()
    Adds TagPage to AllTagPages, with the exception of Home and Favourite tags
     */
    public TagPage(Tag tag, AllTagPages allTagPages) {
        // !!!
    }

    // MODIFIES: this
    // EFFECTS: appends habit to habits
    public void addToTagPage(Habit habit) {
        // !!!
    }

    public List<Habit> getHabits() {
        return habits;
    }
}
