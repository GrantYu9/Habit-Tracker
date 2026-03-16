package gui.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.JPanel;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import gui.window.display.Habits;
import gui.window.display.Pages;
import gui.window.display.instantiation.HabitInstantiation;
import gui.window.display.instantiation.PageInstantiation;
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
        this.setLayout(new BorderLayout());
        this.setBackground(Color.DARK_GRAY);
    }

    // MODIFIES: this
    // EFFECTS: Wipes display so only the default colour shows
    private void wipeDisplay() {
        this.removeAll();
        this.revalidate();
        this.repaint();
    }

    // MODIFIES: this
    // EFFECTS: Shows habit instantiation page
    public void makeHabit() {
        wipeDisplay();
        this.add(new HabitInstantiation(this), BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: Shows page instantiation page
    public void makePage() {
        wipeDisplay();
        this.add(new PageInstantiation(this), BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: Shows habits
    public void showHabits(List<Habit> habits) {
        wipeDisplay();
        this.add(new Habits(habits), BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: Shows pages
    public void showPages(List<Page> pages, Display display) {
        wipeDisplay();
        this.add(new Pages(pages, this), BorderLayout.CENTER);
    }
}
