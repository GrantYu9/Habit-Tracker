package gui.window.display.instantiation;

import java.awt.FlowLayout;
import java.time.LocalTime;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import gui.HabitTrackerController;
import gui.window.Display;

// The habit instantiation page
public class HabitInstantiation extends JPanel {
    private JToggleButton isIncrementToggle; // !!!
    private JTextField goalField; // !!!
    private JTextField startingAmountField; // !!!
    private JTextField stepAmountField; // !!!
    private JTextField titleField; // !!!
    private JTextField unitField; // !!!
    private JTextField hourField; // !!!
    private JTextField minuteField; // !!!

    private Display display; // !!!
    private HabitTrackerController habitTrackerController; // !!!

    /*
     * EFFECTS:
     * Instantiates HabitInstantiation such that
     * this.display = display
     * this.habitTrackerController = habitTrackerController;
     * The layout is BoxLayout
     * Makes the fields to create the habit
     * Makes the done button
     */
    public HabitInstantiation(Display display, HabitTrackerController habitTrackerController) {
        this.display = display;
        this.habitTrackerController = habitTrackerController;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        makeFields();
        makeIncrementToggle();
        makeDoneButton();
    }

    // MODIFIES: this
    // EFFECTS: Creates all the fields
    private void makeFields() {
        titleField = makeField("Title: ");
        unitField = makeField("Unit: ");
        goalField = makeField("Goal: ");
        startingAmountField = makeField("Starting amount: ");
        stepAmountField = makeField("Step amount: ");
        hourField = makeField("Cycle time hour: ");
        minuteField = makeField("Cycle time minute: ");
    }

    // MODIFIES: this
    // EFFECTS: For a fieldLabel, makes a horizontally aligned JPanel with
    // FlowLayout with a label and a textfield inside of it
    private JTextField makeField(String fieldLabel) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel label = new JLabel(fieldLabel);
        JTextField textField = new JTextField(20);

        row.add(label);
        row.add(textField);

        this.add(row);

        return textField;
    }

    // MODIFIES: this
    // EFFECTS: Makes a toggle button that lets the user decide if they want an
    // incrementing or decrementing habit
    private void makeIncrementToggle() {
        isIncrementToggle = new JToggleButton("Mode: Increment");
        isIncrementToggle.addActionListener(actionEvent -> {
            if (isIncrementToggle.isSelected()) {
                isIncrementToggle.setText("Mode: Decrement");
            } else {
                isIncrementToggle.setText("Mode: Increment");
            }
        });
        this.add(isIncrementToggle);
    }

    /*
     * REQUIRES:
     * Each field can not be empty and must have a value valid for its corresponding
     * data type
     * hourField must have a nonnegative integer less than 24
     * minuteField must have a nonnegative integer less than 60
     * MODIFIES:
     * this
     * EFFECTS:
     * Instantiates button named "Done" and on click, creates the habit from values
     * from the fields, and wipes and refreshes the display
     */
    private void makeDoneButton() {
        JButton button = new JButton("Done");
        button.addActionListener(actionEvent -> {
            int goal = Integer.parseInt(goalField.getText());
            int startingAmount = Integer.parseInt(startingAmountField.getText());
            int stepAmount = Integer.parseInt(stepAmountField.getText());
            String title = titleField.getText();
            String unit = unitField.getText();
            int hour = Integer.parseInt(hourField.getText());
            int minute = Integer.parseInt(minuteField.getText());
            LocalTime cycleTime = LocalTime.of(hour, minute);
            boolean isIncrement = !isIncrementToggle.isSelected();

            habitTrackerController.makeHabit(goal, startingAmount, stepAmount, title, unit, cycleTime, isIncrement);

            display.wipeDisplay();
            display.refresh();
        });
        this.add(button);
    }
}
