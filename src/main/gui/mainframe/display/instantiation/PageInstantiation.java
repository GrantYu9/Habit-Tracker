package gui.mainframe.display.instantiation;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import exceptions.HabitNotFoundException;
import gui.HabitTrackerController;
import gui.mainframe.display.Display;
import model.habit.Habit;

// The page instantiation page
@ExcludeFromJacocoGeneratedReport
public class PageInstantiation extends JPanel {
    private JTextField titleField; // Field to set the title
    private JTextField habitsField; // Field to set which habits the user wants in the page

    private Display display; // Access to the main GUI display of the app
    private HabitTrackerController habitTrackerController; // Access to the backend logic of the app

    /*
     * EFFECTS:
     * Instantiates pageInstantiation such that
     * this.display = display;
     * this.habitTrackerController = habitTrackerController;
     * The layout is BoxLayout
     * Fields are made
     * The done button is made
     */
    public PageInstantiation(Display display, HabitTrackerController habitTrackerController) {
        this.display = display;
        this.habitTrackerController = habitTrackerController;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        makeFields();
        makeDoneButton();
    }

    // MODIFIES: this
    // EFFECTS: Creates all the fields
    private void makeFields() {
        titleField = makeField("Title: ");
        habitsField = makeField("Habits: ");
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

    /*
     * REQUIRES:
     * Each field can not be empty and must have a value valid for its corresponding
     * MODIFIES:
     * this
     * EFFECTS:
     * Instantiates button named "Done" and on click, creates the page from
     * values from the fields, and wipes and refreshes the display
     */
    private void makeDoneButton() {
        JButton button = new JButton("Done");
        button.addActionListener(actionEvent -> {
            String title = titleField.getText();
            List<Habit> habits = parseHabitsField(habitsField.getText());

            habitTrackerController.makePage(title, habits);

            display.wipeDisplay();
            display.refresh();
        });
        this.add(button);
    }

    // EFFECTS: Parses input and for each title, tries to find a habit and adds it
    // to the list to be returned
    private List<Habit> parseHabitsField(String input) { // !!! pleaseee test this thoroughly
        List<String> habitTitles = new ArrayList<>(Arrays.asList(input.split(",\\s")));

        List<Habit> result = new ArrayList<>();

        for (String title : habitTitles) {
            Habit habit = null;

            try {
                habit = habitTrackerController.findHabit(title);
            } catch (HabitNotFoundException exception) {
                exception.printStackTrace();
            }

            result.add(habit);
        }

        return result;
    }
}
