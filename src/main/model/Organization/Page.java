package model.organization;

import model.habit.Habit;

import java.util.ArrayList;
import java.util.List;

/*
!!!
 */
public class Page {
    private String title; // !!!
    
    private List<Habit> habits; // !!!
    

    /*
    REQUIRES:
        !!!
    EFFECTS: initializes ReadWritePage such that 
        !!!
     */
    public Page(String title) {
        // !!!
    }

    public Page(String title, List<Habit> habits) {
        // !!!
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: appends habit to habits and sorts habits by alphabetical order
    public void addHabit(Habit habit) {
        // !!!
    }

    

    // REQUIRES: title has at least one character
    // EFFECTS: this.title = title, with surrounding whitespace trimmed
    public void setTitle(String title) {
        // !!!
    }

    public String getTitle() {
        return title;
    }

    public List<Habit> getHabits() {
        return habits;
    }

    
}
