package gui.mainframe.display.habit;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import gui.mainframe.display.Display;
import gui.mainframe.display.habit.heatmap.Heatmap;
import model.habit.Habit;

// Higher level form of each habit that allows switching between Bar and Heatmap form
@ExcludeFromJacocoGeneratedReport
public class HabitGUI extends JPanel {
    private Habit habit; // The habit this represents

    private Display display; // Bar needs this

    /*
     * Instantiates HabitGUI such that
     * this.habit = habit;
     * The layout is BorderLayout
     * Shows habit as bar by default
     */
    public HabitGUI(Habit habit, Display display) {
        this.habit = habit;
        this.display = display;
        this.setLayout(new BorderLayout());
        showBar(this);
    }

    // MODIFIES: this
    // EFFECTS: Wipes display
    private void wipe() {
        this.removeAll();
    }

    // MODIFIES: this
    // EFFECTS: Recalculates hierarchies
    private void refresh() {
        this.revalidate();
        this.repaint();
    }

    // MODIFIES: this
    // EFFECTS: Shows habit as a bar widget and user can interact with it
    private void showBar(HabitGUI habitGUI) {
        wipe();
        this.add(new Bar(habit, habitGUI, display));
        refresh();
    }

    // MODIFIES: this
    // EFFECTS: Shows heatmap of habit
    private void showHeatmap(HabitGUI habitGUI) {
        wipe();
        this.add(new Heatmap(habit, habitGUI));
        refresh();
    }

    // MODIFIES: this
    // EFFECTS: Shows habit as a bar
    public void swapToBar() {
        showBar(this);
    }

    // MODIFIES: this
    // EFFETS: Shows habit as heatmap
    public void swapToHeatmap() {
        showHeatmap(this);
    }
}
