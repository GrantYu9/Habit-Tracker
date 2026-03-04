package model.organization.tree;

import model.habit.Habit;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

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
     * REQUIRES:
     * title has at least one character
     * User must add this to AllGenericPages
     * EFFECTS:
     * initializes Page such that
     * this.title = title, with surrounding whitespace removed
     * 
     * order = ALPHABETICAL
     * habits = new ArrayList<>()
     */
    public Page(String title) {
        this.title = title.strip();

        order = Order.ALPHABETICAL;
        habits = new ArrayList<>();
    }

    // REQUIRES: habit can not be in any other page
    // MODIFIES: this
    // EFFECTS: appends habit to habits and sorts habits as per ORDER
    public void addHabit(Habit habit) {
        habits.add(habit);

        if (order == Order.ALPHABETICAL) {
            habits.sort(Comparator.comparing(h -> h.getTitle()));
        }
    }

    // REQUIRES: title has at least one character
    // EFFECTS: this.title = title, with surrounding whitespace trimmed
    public void setTitle(String title) {
        this.title = title.strip();
    }

    // EFFECTS: If order is Order.Alphabetical, sorts habits as per Order
    public void setOrder(Order order) {
        this.order = order;

        if (order == Order.ALPHABETICAL) {
            Collator dictionary = Collator.getInstance(Locale.ENGLISH);

            habits.sort(Comparator.comparing(h -> h.getTitle(), dictionary));
        }
    }

    @Override
    // EFFECTS: Returns true if and only if this and object point to the same
    // location in memory or if their habits share the same fields
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }

        if (this == object) {
            return true;
        }

        Page page = (Page) object;

        if (!(this.title.equals(page.getTitle()) && this.order.equals(page.getOrder())
                && habits.size() == page.getHabits().size())) {
            return false;
        }

        for (int i = 0; i < habits.size(); i++) {
            if (!habits.get(i).equals(page.getHabits().get(i))) {
                return false;
            }
        }

        return true;
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
