package gui.mainframe.display.habit.heatmap;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import gui.mainframe.display.habit.HabitGUI;
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
        makePreviousTiles(calendarPanel);
        makeTodaysTile(calendarPanel);
        this.add(calendarPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: Creates a dayTile, sets the layout to BoxLayout, vertically aligned,
    // adds the labels to it, sets its colour, and adds it to calendarPanel
    private void makeTodaysTile(JPanel calendarPanel) {
        JPanel dayTile = new DayTile();
        dayTile.setLayout(new BoxLayout(dayTile, BoxLayout.Y_AXIS));
        dayTile.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        superMakeTodaysTile(dayTile);
        calendarPanel.add(dayTile);
    }

    // MODIFIES: this
    // EFFECTS: Adds labels for data for dayTile
    private void superMakeTodaysTile(JPanel dayTile) {
        addDayToTodaysTile(dayTile);
        addToTodaysTile(Integer.toString(habit.getGoal()), dayTile);
        addToTodaysTile(Integer.toString(habit.getCurrentAmount()), dayTile);
        addToTodaysTile(Integer.toString(habit.getProgressPercentage()), dayTile);
        addToTodaysTile(Integer.toString(habit.getOverloadAmount()), dayTile);
        addToTodaysTile(habit.getProgressType().toString(), dayTile);
        setColourForTodaysTile(dayTile);
    }

    // MODIFIES: this
    // EFFECTS: Adds today's date to dayTile in the form of <MONTH> <day>, where
    // <MONTH> is in full caps
    private void addDayToTodaysTile(JPanel dayTile) {
        LocalDate now = LocalDate.now();

        String month = now.getMonth().toString();
        String day = Integer.toString(now.getDayOfMonth());

        addToTodaysTile(month + " " + day, dayTile);
    }

    // MODIFIES: this
    // EFFECTS: Adds JLabel with label text in it to dayTile
    private void addToTodaysTile(String label, JPanel dayTile) {
        JLabel jlabel = new JLabel(label);
        dayTile.add(jlabel);
    }

    // MODIFIES: this
    // EFFECTS: Paints the tile up to habit.getProgresPercentage() %, from the bottom
    private void setColourForTodaysTile(JPanel dayTile) {
        double fillHeightDouble = dayTile.getHeight() * ((double) habit.getProgressPercentage() / 100);
        int fillHeight = (int) fillHeightDouble;
        ((DayTile) dayTile).setCustomHeight(fillHeight);
    }

    // MODIFIES: this
    // EFFECTS: For 34 days before today, creates a dayTile, sets the layout to
    // BoxLayout, vertically aligned, adds content to dayTile, and adds it to
    // calendarPanel
    private void makePreviousTiles(JPanel calendarPanel) {
        for (int i = 34; i > 0; i--) {
            JPanel dayTile = new DayTile();
            dayTile.setLayout(new BoxLayout(dayTile, BoxLayout.Y_AXIS));
            dayTile.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
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

    // MODIFIES: this
    // EFFECTS: makes a blank day with just the date
    private void makeBlankDayTile(LocalDate date, JPanel dayTile) {
        String month = date.getMonth().toString();
        String day = Integer.toString(date.getDayOfMonth());

        JLabel jlabel = new JLabel(month + " " + day);
        dayTile.add(jlabel);
    }

    // MODIFIES: this
    // EFFECTS: Adds fields from habitSnapshot to dayTile
    private void makeFullDayTile(JPanel dayTile, HabitSnapshot habitSnapshot) {
        addDayToDayTile(dayTile, habitSnapshot);
        addToDayTile(Integer.toString(habitSnapshot.getGoal()), dayTile);
        addToDayTile(Integer.toString(habitSnapshot.getCurrentAmount()), dayTile);
        addToDayTile(Integer.toString(habitSnapshot.getProgressPercentage()), dayTile);
        addToDayTile(Integer.toString(habitSnapshot.getOverloadAmount()), dayTile);
        addToDayTile(habitSnapshot.getProgressType().toString(), dayTile);
        setColourDayTile(dayTile, habitSnapshot);
    }

    // MODIFIES: this
    // EFFECTS: Adds date to dayTile in the form of <MONTH> <day>, where <MONTH> is
    // in full caps
    private void addDayToDayTile(JPanel dayTile, HabitSnapshot habitSnapshot) {
        LocalDate date = habitSnapshot.getDay();

        String month = date.getMonth().toString();
        String day = Integer.toString(date.getDayOfMonth());

        addToDayTile(month + " " + day, dayTile);
    }

    // MODIFIES: this
    // EFFECTS: Adds a JLabel constructed with label to dayTile
    private void addToDayTile(String label, JPanel dayTile) {
        JLabel jlabel = new JLabel(label);
        dayTile.add(jlabel);
    }

    // MODIFIES: this
    // EFFECTS: Paints the tile up to habitSnapshot.getProgresPercentage() %, from the bottom
    private void setColourDayTile(JPanel dayTile, HabitSnapshot habitSnapshot) {
        double fillHeightDouble = dayTile.getHeight() * ((double) habitSnapshot.getProgressPercentage() / 100);
        int fillHeight = (int) fillHeightDouble;
        ((DayTile) dayTile).setCustomHeight(fillHeight);
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
