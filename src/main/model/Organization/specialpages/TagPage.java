package model.organization.specialpages;

import model.organization.Tag;
import model.habit.Habit;

import java.util.ArrayList;
import java.util.List;

/*
A page for all habits of the same tag
 */
public class TagPage {
    private String title;

    private List<Habit> habits;

    /*
    EFFECTS:
    Instantiates TagPage such that
        title = tag.getTitle()
        habits = new ArrayList<>()
     */
    public TagPage(Tag tag) {
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
