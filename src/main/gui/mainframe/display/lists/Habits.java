package gui.mainframe.display.lists;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import gui.mainframe.display.habit.HabitGUI;
import model.habit.Habit;

// Where a list of habits will be displayed
@ExcludeFromJacocoGeneratedReport
public class Habits extends JPanel {
    private List<Habit> habits; // Habits that Habits will display

    /*
     * EFFECTS:
     * Instantiates Habits such that
     * this.habits = habits;
     * 
     * The layout is BoxLayout, vertically aligned
     * 
     * Shows all habits in habits
     */
    public Habits(List<Habit> habits) {
        this.habits = habits;

        this.setLayout(new BorderLayout());

        showHabits();
    }

    // MODIFIES: this
    // EFFECTS: For each Habit habit in habits, instantiates HabitGUI with habit
    private void showHabits() {
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));

        for (Habit habit : habits) {
            innerPanel.add(new HabitGUI(habit));
        }

        JScrollPane scrollPane = new JScrollPane(innerPanel);
        this.add(scrollPane);
    }
}
