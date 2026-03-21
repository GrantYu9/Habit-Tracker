package gui.mainframe.display.habit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.habit.Habit;
import model.habit.HabitSnapshot;

// Heatmap of a habit, showcasing 35 days of history, including today
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
     * The layout is BorderLayout
     * A thin, black border is created
     * 
     * Calendar is formed
     * Swap button is made
     */
    public Heatmap(Habit habit, HabitGUI habitGUI) {
        this.habit = habit;
        this.habitGUI = habitGUI;

        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        makeCalendar();
        makeSwapButton();
    }

    /*
     * MODIFIES:
     * this
     * EFFECTS:
     * Instantiates a calendarPanel and sets the layout to a 5 block wide, 7 block
     * tall GridLayout
     * Instantiates today's tile and the previous tiles, adding them to
     * calendarPanel
     * Adds calendarPanel to this, in the centre
     */
    private void makeCalendar() {
        JPanel calendarPanel = new JPanel();
        calendarPanel.setLayout(new GridLayout(5, 7));
        makeTodaysTile(calendarPanel);
        makePreviousTiles(calendarPanel);
        this.add(calendarPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: Creates a dayTile, sets the layout to BoxLayout, vertically aligned,
    // adds the labels to it, sets its colour, and adds it to calendarPanel
    private void makeTodaysTile(JPanel calendarPanel) {
        JPanel dayTile = new JPanel();
        dayTile.setLayout(new BoxLayout(dayTile, BoxLayout.Y_AXIS));
        addLabelsToTodaysTile(dayTile);
        setColourForTodaysTile(dayTile);
        calendarPanel.add(dayTile);
    }

    // MODIFIES: this
    // EFFECTS: Adds labels for data for dayTile
    private void addLabelsToTodaysTile(JPanel dayTile) {
        addDayToTodaysTile(dayTile);
        addGoalToTodaysTile(dayTile);
        addCurrentAmountToTodaysTile(dayTile);
        addPercentageToTodaysTile(dayTile);
        addOverloadAmountToTodaysTile(dayTile);
        addProgressTypeToTodaysTile(dayTile);
    }

    // !!!
    private void addDayToTodaysTile(JPanel dayTile) {
        // !!!
    }

    // !!!
    private void addGoalToTodaysTile(JPanel dayTile) {
        // !!!
    }

    // !!!
    private void addCurrentAmountToTodaysTile(JPanel dayTile) {
        // !!!
    }

    // !!!
    private void addPercentageToTodaysTile(JPanel dayTile) {
        // !!!
    }

    // !!!
    private void addOverloadAmountToTodaysTile(JPanel dayTile) {
        // !!!
    }

    // !!!
    private void addProgressTypeToTodaysTile(JPanel dayTile) {
        // !!!
    }

    // !!!
    private void setColourForTodaysTile(JPanel dayTile) {
        // !!!
    }

    // MODIFIES: this
    // EFFECTS: For 34 days before today, creates a dayTile, sets the layout to
    // BoxLayout, vertically aligned, adds content to dayTile, and adds it to
    // calendarPanel
    private void makePreviousTiles(JPanel calendarPanel) {
        for (int i = 1; i < 35; i++) {
            JPanel dayTile = new JPanel();
            dayTile.setLayout(new BoxLayout(dayTile, BoxLayout.Y_AXIS));
            makeDayTile(LocalDate.now().minusDays(i), dayTile);
            calendarPanel.add(dayTile);
        }
    }

    // MODIFIES: this
    // EFFECTS: If there is a habitSnapshot in habit.getHistory(), a full dayTile
    // will be created. Else, a blank dayTile will be made
    private void makeDayTile(LocalDate date, JPanel dayTile) {
        for (HabitSnapshot habitSnapshot : habit.getHistory()) {
            if (habitSnapshot.getDay().equals(date)) {
                makeFullDayTile(dayTile, habitSnapshot);
            }
        }

        makeBlankDayTile(date, dayTile);
    }

    // !!!
    private void makeBlankDayTile(LocalDate date, JPanel dayTile) {
        // !!!
    }

    // MODIFIES: this
    // EFFECTS: Adds fields from habitSnapshot to dayTile
    private void makeFullDayTile(JPanel dayTile, HabitSnapshot habitSnapshot) {
        addDayToDayTile(dayTile, habitSnapshot);
        addGoalToDayTile(dayTile, habitSnapshot);
        addCurrentAmountToDayTile(dayTile, habitSnapshot);
        addPercentageToDayTile(dayTile, habitSnapshot);
        addOverloadAmountToDayTile(dayTile, habitSnapshot);
        addProgressTypeToToDayTile(dayTile, habitSnapshot);
    }

    // !!!
    private void addDayToDayTile(JPanel dayTile, HabitSnapshot habitSnapshot) {
        // !!!
    }

    // !!!
    private void addGoalToDayTile(JPanel dayTile, HabitSnapshot habitSnapshot) {
        // !!!
    }

    // !!!
    private void addCurrentAmountToDayTile(JPanel dayTile, HabitSnapshot habitSnapshot) {
        // !!!
    }

    // !!!
    private void addPercentageToDayTile(JPanel dayTile, HabitSnapshot habitSnapshot) {
        // !!!
    }

    // !!!
    private void addOverloadAmountToDayTile(JPanel dayTile, HabitSnapshot habitSnapshot) {
        // !!!
    }

    // !!!
    private void addProgressTypeToToDayTile(JPanel dayTile, HabitSnapshot habitSnapshot) {
        // !!!
    }

    // MODIFIES: this
    // EFFECTS: Swaps to bar view
    private void makeSwapButton() {
        JButton button = new JButton("Swap");
        button.addActionListener(actionEvent -> {
            habitGUI.swapToBar();
        });
        this.add(button, BorderLayout.SOUTH);
    }
}
