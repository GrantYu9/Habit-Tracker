package model.organization;

import model.habit.Habit;

import java.util.ArrayList;
import java.util.List;

// A generic page thats acts as a container to organize habits
public class Page {
    private Order order; // Order to sort the habits
    private enum Order {
        ALPHABETICAL,
        MANUAL
    }

    private String title; // Title of the page
    
    private List<Habit> habits; // Habits in the page

    /*
    REQUIRES:
        title has at least one character
    EFFECTS: initializes Page such that 
        this.title = title, with surrounding whitespace removed

        order = ALPHABETICAL
        habits = new ArrayList<>()
     */
    public Page(String title) {
        // !!!
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: appends habit to habits and sorts habits as per ORDER
    public void addHabit(Habit habit) {
        // !!!
    }

    // REQUIRES: title has at least one character
    // EFFECTS: this.title = title, with surrounding whitespace trimmed
    public void setTitle(String title) {
        // !!!
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public String getTitle() {
        return title;
    }

    public List<Habit> getHabits() {
        return habits;
    }
}
