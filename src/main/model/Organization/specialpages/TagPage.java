package model.organization.specialpages;

import model.organization.Tag;
import model.organization.centralization.AllTagPagesPage;
import model.habit.Habit;

import java.util.ArrayList;
import java.util.List;

// A page for all habits of the same tag
public class TagPage {
    private String title;
    
    private Tag tag;

    private List<Habit> habits;

    /*
    REQUIRES:
    Tag is not of type HOME or FAVOURITE
    EFFECTS:
    Instantiates TagPage such that
        title = tag.getTitle()
        this.tag = tag;
        habits = new ArrayList<>()
    Adds TagPage to AllTagPages
     */
    public TagPage(Tag tag, AllTagPagesPage allTagPages) {
        // !!!
    }

    // MODIFIES: this
    // EFFECTS: appends habit to habits
    public void addToTagPage(Habit habit) {
        // !!!
    }

    public String getTitle() {
        return title;
    }

    public Tag getTag() {
        return tag;
    }

    public List<Habit> getHabits() {
        return habits;
    }
}
