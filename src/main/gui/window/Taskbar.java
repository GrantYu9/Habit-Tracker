package gui.window;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import gui.HabitTrackerController;

// A sidebar on the left that will be quite slim but span the full vertical space
// Where our controls will reside
@ExcludeFromJacocoGeneratedReport
public class Taskbar extends JPanel {
    private HabitTrackerController habitTrackerController;
    private Display display;
    private Titlebar titlebar;
    // !!! a whole bunch of fields, with display and titlebar
    /*
    EFFECTS:
     */
    public Taskbar(HabitTrackerController habitTrackerController, Display display, Titlebar titlebar) {
        this.habitTrackerController = habitTrackerController;

        this.setLayout(new GridLayout());
        this.setBackground(Color.GRAY);
    }

    // grid layout

    // section 1
        // save to file !!!
        // load from file !!!
        // make habit !!! -> HabitInstantiation popup
        // make page !!! -> PageInstnatiation page
    
    // section 2
        // all pages -> display.showpages
        // all habits -> display.showhabits
}
