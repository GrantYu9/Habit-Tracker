package gui.mainframe.display.habit;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.habit.Habit;

// Heatmaps of the habits, showcasing 1 month of history
@ExcludeFromJacocoGeneratedReport
public class Heatmap extends JPanel {
    private Habit habit; // Habit this represents

    private HabitGUI habitGUI; // Access to swapToBar() method

    /*
     * EFFECTS:
     * Instantiates heatmap such that
     * this.habit = habit;
     * this.habitGUI = habitGUI;
     * 
     * The layout is GridLayout
     * 
     * Calendar is formed
     * Swap button is made
     */
    public Heatmap(Habit habit, HabitGUI habitGUI) {
        this.habit = habit;
        this.habitGUI = habitGUI;

        this.setLayout(new GridLayout(5, 7));

        makeCalendar();
        makeSwapButton();
    }

    /*
     * MODIFIES:
     * this
     * EFFECTS:
     * !!!
     */
    private void makeCalendar() {
        // !!!
    }

    // MODIFIES: this
    // EFFECTS: Swaps to bar view
    private void makeSwapButton() {
        JButton button = new JButton("Swap");
        button.addActionListener(actionEvent -> {
            habitGUI.swapToBar();
        });
        this.add(button);
    }
}
