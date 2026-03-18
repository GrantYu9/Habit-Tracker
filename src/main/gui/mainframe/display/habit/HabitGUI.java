package gui.mainframe.display.habit;

import javax.swing.JPanel;

import gui.mainframe.display.lists.Habits;
import model.habit.Habit;

public class HabitGUI extends JPanel {
    Habit habit; // The habit this represents

    /*
     * Instantiates HabitGUI such that
     * !!!
     */
    public HabitGUI(Habit habit) {
        this.habit = habit;
        // !!! layout
    }

    // !!! display habit
    private void showBar(Habit habit, Habits habits) {
        // !!!
    }

    // !!! display habit
    private void showHeatmap(Habit habit, Habits habits) {
        // !!!
    }

    // !!!
    public void swapToBar(Habit habit, Habits habits) {
        // !!!
    }

    // !!!
    public void swapToHeatmap(Habit habit, Habits habits) {
        // !!!
    }
}
