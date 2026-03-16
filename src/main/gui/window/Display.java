package gui.window;

import java.awt.Color;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import gui.window.display.Habits;
import gui.window.display.Pages;
import gui.window.display.instantiation.HabitInstantiation;
import model.habit.Habit;
import model.organization.tree.Page;

// The space that will be everything but the TaskBar, where habits and pages will be displayed
@ExcludeFromJacocoGeneratedReport
public class Display extends JPanel {
    /*
    EFFECTS: 
    Instantiates Display such that
    The layout is BoxLayout, vertically aligned
    The background is dark grey
     */
    public Display() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.DARK_GRAY); // !!! default for now
    }

    // wipe state !!!
    private void wipeDisplay() {
        // !!!
    }

    // !!!
    public void makeHabit() {
        // !!!
    }

    // !!!
    public void makePage() {
        // !!!
    }

    // show habits !!!
    public void showHabits(List<Habit> habits) {
        wipeDisplay();
        new Habits(habits);
    }

    // show pages !!!
    public void showPages(List<Page> pages) {
        wipeDisplay();
        new Pages(pages);
    }
}
