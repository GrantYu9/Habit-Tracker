package gui.mainframe.display.habit;

import javax.swing.JButton;
import javax.swing.JPanel;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.habit.Habit;

// Habits as an interactable bar widget
@ExcludeFromJacocoGeneratedReport
public class Bar extends JPanel {
    private Habit habit; // The habit this represents

    private HabitGUI habitGUI; // Access to swapToHeatMap() method

    // Constructor !!!
    public Bar(Habit habit, HabitGUI habitGUI) {
        this.habit = habit;
        this.habitGUI = habitGUI;
        // !!!
    }

    // 4 circles
        // currentAmount !!!
        // goal !!!
        // percentage !!!
        // overload amount !!!
    
    // step corner
        // button to increment !!!
        // unit !!!
}
