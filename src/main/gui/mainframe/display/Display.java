package gui.mainframe.display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.JPanel;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import gui.HabitTrackerController;
import gui.mainframe.display.instantiation.HabitInstantiation;
import gui.mainframe.display.instantiation.PageInstantiation;
import gui.mainframe.display.lists.Habits;
import gui.mainframe.display.lists.Pages;
import model.habit.Habit;
import model.organization.tree.Page;

// The space that will be everything but the TaskBar, where habits and pages will be displayed
@ExcludeFromJacocoGeneratedReport
public class Display extends JPanel {
    private HabitTrackerController habitTrackerController; // Access to the backend logic of the app
    
    /*
    EFFECTS: 
    Instantiates Display such that
    this.habitTrackerController = habitTrackerController;

    The layout is BoxLayout, vertically aligned
    The background is dark grey
     */
    public Display(HabitTrackerController habitTrackerController) {
        this.habitTrackerController = habitTrackerController;

        this.setLayout(new BorderLayout());
        this.setBackground(Color.DARK_GRAY);
    }

    // MODIFIES: this
    // EFFECTS: Wipes display so only the default colour shows
    public void wipeDisplay() {
        this.removeAll();
    }

    // MODIFIES: this
    // EFFECTS: Recalculates hierarchies
    public void refresh() {
        this.revalidate();
        this.repaint();
    }

    // MODIFIES: this
    // EFFECTS: Shows habit instantiation page
    public void makeHabit() {
        wipeDisplay();
        this.add(new HabitInstantiation(this, habitTrackerController), BorderLayout.CENTER);
        refresh();
    }

    // MODIFIES: this
    // EFFECTS: Shows page instantiation page
    public void makePage() {
        wipeDisplay();
        this.add(new PageInstantiation(this, habitTrackerController), BorderLayout.CENTER);
        refresh();
    }

    // MODIFIES: this
    // EFFECTS: Shows habits
    public void showHabits(List<Habit> habits) {
        wipeDisplay();
        this.add(new Habits(habits), BorderLayout.CENTER);
        refresh();
    }

    // MODIFIES: this
    // EFFECTS: Shows pages
    public void showPages(List<Page> pages, Display display) {
        wipeDisplay();
        this.add(new Pages(pages, this), BorderLayout.CENTER);
        refresh();
    }
}
