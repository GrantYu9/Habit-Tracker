package model.organization;

import model.habit.Habit;

import java.util.ArrayList;
import java.util.List;

/*
A general use, customizable habit page to organize habits with
Acts as a container for habits
 */
public class Page {
    // Colour field

    private HabitOrder habitOrder; // !!!
    /*
    !!!
     */
    private enum HabitOrder {
        // !!!
    }
    /*
    !!!
     */
    private TagOrder tagOrder; // !!!
    private enum TagOrder {
        // !!!
    }

    private String title; // !!!
    
    private List<Habit> habits; // !!!
    private List<Tag> tags; // !!!

    /*
    REQUIRES:
        !!!
    EFFECTS: initializes Page such that 
        !!!
     */
    public Page(String title) {
        // !!!
    }

    public Page(String title, List<Habit> habits) {
        // !!!
    }

    public Page(String title, List<Habit> habits, List<Tag> tags) {
        // !!!
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: appends habit to habits and sorts habits as per habitOrder
    public void addHabit(Habit habit) {
        // !!!
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: appends tag to tags and sorts tags as per tagOrder
    public void addTag(Tag tag) {
        // !!!
    }

    // REQUIRES: title has at least one character
    // EFFECTS: this.title = title, with surrounding whitespace trimmed
    public void setTitle(String title) {
        // !!!
    }

    public void setHabitOrder(HabitOrder habitOrder) {
        this.habitOrder = habitOrder;
    }

    public void setTagOrder(TagOrder tagOrder) {
        this.tagOrder = tagOrder;
    }

    public String getTitle() {
        return title;
    }

    public HabitOrder getHabitOrder() {
        return habitOrder;
    }

    public TagOrder getTagOrder() {
        return tagOrder;
    }

    public List<Habit> getHabits() {
        return habits;
    }

    public List<Tag> getTags() {
        return tags;
    }
}
