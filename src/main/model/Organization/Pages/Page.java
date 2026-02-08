package model.organization.pages;

import model.habit.Habit;

import java.util.ArrayList;
import java.util.List;

/*
A general use, customizable habit page to organize habits with
Acts as a container for habits
 */
public class Page {
    private List<Habit> habits;

    // EFFECTS: initializes Page such that habits = new ArrayList<>()
    public Page() {
        // !!!
    }

    public List<Habit> getHabits() {
        return habits;
    }

}
