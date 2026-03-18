package gui.window.display;

import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.habit.Habit;

// Where a list of habits will be displayed
@ExcludeFromJacocoGeneratedReport
public class Habits extends JPanel {
    private List<Habit> habits; // !!!

    // scrollpane !!!

    /*
    EFFECTS:
    Instantiates Habits such that
    this.habits = habits;

    The layout is BoxLayout, vertically aligned

    Shows all habits in habits
     */
    public Habits(List<Habit> habits) {
        this.habits = habits;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        showHabits();
    }

    // MODIFIES: this
    // EFFECTS: For each Habit in habits, !!!
    private void showHabits() {
        for (Habit habit : habits) {
            showHabitBar(habit, this);
        }
    }
    
    // !!! display habit
    public void showHabitBar(Habit habit, Habits habits) {
        // !!!
    }

    // !!! display habit
    public void showHabitHeatmap(Habit habit, Habits habits) {
        // !!!
    }
}
