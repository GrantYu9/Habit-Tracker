package gui.mainframe.display.habit;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import gui.mainframe.display.Display;
import model.habit.Habit;
import model.habit.Habit.ProgressType;

// Habits as an interactable bar widget
@ExcludeFromJacocoGeneratedReport
public class Bar extends JPanel {
    private GridBagConstraints gridBagConstraints; // Allows us to configure where on the GridBagLayout we want our
                                                   // components

    private Habit habit; // The habit this represents

    private HabitGUI habitGUI; // Access to swapToHeatMap() method
    private Display display; // Access to the flashImage() method

    /*
     * EFFECTS:
     * Instantiates Bar such that
     * this.habit = habit;
     * this.habitGUI = habitGUI;
     * 
     * Sets up the layout
     * 
     * Stats are shown
     * Buttons are made
     */
    public Bar(Habit habit, HabitGUI habitGUI, Display display) {
        this.habit = habit;
        this.habitGUI = habitGUI;
        this.display = display;

        setUpLayout();

        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        makeDisplays();
        makeButtons();
    }

    /*
     * EFFECTS:
     * Sets the layout to GridBagLayout
     * 
     * Instantiates gridBagConstraints such that
     * Cells fill in both dimensions
     * weightx is 1 to stretch the bar out horizontally
     * weighty is 0 to keep the bar as vertically thin as possible
     */
    private void setUpLayout() {
        this.setLayout(new GridBagLayout());

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 0;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
    }

    // MODIFIES: this
    // EFFECTS: Makes displays for stats
    private void makeDisplays() {
        showTitle();
        showCycleTime();
        showGoal();
        showCurrentAmount();
        showOverloadAmount();
        showPercentage();
        showProgressType();
        showUnit();
        showStepAmount();
    }

    // MODIFIES: this
    // EFFECTS: Displays the title
    private void showTitle() {
        JLabel label = new JLabel("Title: " + habit.getTitle());

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        this.add(label, gridBagConstraints);
    }

    // MODIFIES: this
    // EFFECTS: Displays the cycle time
    private void showCycleTime() {
        JLabel label = new JLabel("Cycle time: " + habit.getCycleTime());

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        this.add(label, gridBagConstraints);
    }

    // MODIFIES: this
    // EFFECTS: Displays the goal
    private void showGoal() {
        JLabel label = new JLabel("Goal: " + habit.getGoal());

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        this.add(label, gridBagConstraints);
    }

    // MODIFIES: this
    // EFFECTS: Displays the currentAmount
    private void showCurrentAmount() {
        JLabel label = new JLabel("Current amount: " + habit.getCurrentAmount());

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        this.add(label, gridBagConstraints);
    }

    // MODIFIES: this
    // EFFECTS: Displays the overload amount
    private void showOverloadAmount() {
        JLabel label = new JLabel("Overload amount: " + habit.getOverloadAmount());

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        this.add(label, gridBagConstraints);
    }

    // MODIFIES: this
    // EFFECTS: Displays the percentage
    private void showPercentage() {
        JLabel label = new JLabel("Percentage: " + habit.getProgressPercentage() + "%");

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        this.add(label, gridBagConstraints);
    }

    // MODIFIES: this
    // EFFECTS: Displays the progress type
    private void showProgressType() {
        ProgressType progressType = habit.getProgressType();
        String string;

        switch (progressType) {
            case UNDERDONE:
                string = "Underdone";
                break;
            case DONE:
                string = "Done";
                break;
            default:
                string = "OVERLOADED";
        }
        
        JLabel label = new JLabel("Progress status: " + string);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        this.add(label, gridBagConstraints);
    }

    // MODIFIES: this
    // EFFECTS: Displays the step amount
    private void showStepAmount() {
        JLabel label = new JLabel("Step amount: " + habit.getStepAmount());

        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        this.add(label, gridBagConstraints);
    }

    // REQUIRES: unit is not null
    // MODIFIES: this
    // EFFECTS: Displays the unit
    private void showUnit() {
        JLabel label = new JLabel("Unit: " + habit.getUnit());

        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        this.add(label, gridBagConstraints);
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
        JButton button = new JButton("Progress");

        button.addActionListener(actionEvent -> {
            habit.progressByStepAmount();
            habitGUI.swapToBar();
            if (habit.getProgressType().equals(ProgressType.DONE)) {
                display.flashImage();
            }
        });

        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        this.add(button, gridBagConstraints);
    }

    // MODIFIES: this
    // EFFECTS: Swaps to heatmap view
    private void makeSwapButton() {
        JButton button = new JButton("Swap");

        button.addActionListener(actionEvent -> {
            habitGUI.swapToHeatmap();
        });

        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        this.add(button, gridBagConstraints);
    }
}
