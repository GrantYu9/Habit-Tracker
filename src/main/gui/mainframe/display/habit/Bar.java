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

    /*
     * EFFECTS:
     * Instantiates Bar such that
     * this.habit = habit;
     * this.habitGUI = habitGUI;
     * 
     * The layout is !!!
     * 
     * Stats are shown
     * Buttons are made
     */
    public Bar(Habit habit, HabitGUI habitGUI) {
        this.habit = habit;
        this.habitGUI = habitGUI;

        // !!! layout

        makeDisplays();
        makeButtons();
    }

    // EFFECTS: Makes displays for stats
    private void makeDisplays() {
        showGoal();
        showCurrentAmount();
        showOverloadAmount();
        showPercentage();
        showUnit();
    }

    // !!!
    private void showGoal() {
        // !!!
    }

    // !!!
    private void showCurrentAmount() {
        // !!!
    }

    // !!!
    private void showOverloadAmount() {
        // !!!
    }

    // !!!
    private void showPercentage() {
        // !!!
    }

    // !!!
    private void showUnit() {
        // !!!
    }

    // MODIFIES: this
    // EFFECTS: Makes buttons
    private void makeButtons() {
        makeProgressButton();
        makeSwapButton();
    }

    // MODIFIES: this
    // EFFECTS: Progresses the habit by a step amount
    private void makeProgressButton() {
        JButton button = new JButton("+");
        button.addActionListener(actionEvent -> {
            habit.progressByStepAmount();
        });
        this.add(button);
    }

    // MODIFIES: this
    // EFFECTS: Swaps to heatmap view
    private void makeSwapButton() {
        JButton button = new JButton("Swap");
        button.addActionListener(actionEvent -> {
            habitGUI.swapToHeatmap();
        });
        this.add(button);
    }
}
