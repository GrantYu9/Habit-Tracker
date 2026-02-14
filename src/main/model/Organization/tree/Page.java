package model.organization.tree;

import model.habit.Habit;
import model.organization.centralization.AllGenericPages;

import java.util.ArrayList;
import java.util.List;

// A generic page thats acts as a container to organize habits
public class Page {
    private Order order; // Order to sort the habits
    public enum Order {
        ALPHABETICAL, // Alphabetical order
        MANUAL // How the user wants to
    }

    private String title; // Title of the page
    
    private List<Habit> habits; // Habits in the page

    /*
    REQUIRES:
    title has at least one character
    EFFECTS: 
    initializes Page such that 
        this.title = title, with surrounding whitespace removed

        order = ALPHABETICAL
        habits = new ArrayList<>()
    Adds page to AllGenericPages
     */
    public Page(String title, AllGenericPages allGenericPages) {
        // !!!
    }

    // EFFECTS: Sorts habits as per order
    public void sort() {
        // !!!
    }

    // REQUIRES: habit can not be in any other page
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
