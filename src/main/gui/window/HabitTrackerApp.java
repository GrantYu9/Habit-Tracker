package gui.window;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;

import javax.swing.JFrame;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import gui.HabitTrackerController;

// The overarching window of which all content will be displayed. Also where all the GUI action will be hosted
@ExcludeFromJacocoGeneratedReport
public class HabitTrackerApp extends JFrame {
    private HabitTrackerController habitTrackerController;

    // EFFECTS: Instantiates the JFrame by configuring the default settings,
    // applying the visual components, and making it visible
    public HabitTrackerApp() {
        setUpConfigurations();
        setUpComponents();
        this.setVisible(true);
    }

    /*
     * EFFECTS:
     * Configures the JFrame such that:
     * The title is "Habit Tracker"
     * The application terminates upon closing the GUI window
     * Fixes size to 1,000 by 1,000 pixels
     * Centers the window
     * Uses BorderLayout
     */
    private void setUpConfigurations() {
        habitTrackerController = new HabitTrackerController();

        this.setTitle("Habit Tracker");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1_250, 1_000);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                habitTrackerController.saveTime();
                System.exit(0);
            }
        });
    }

    /*
     * MODIFIES:
     * this
     * EFFECTS:
     * Instantiates and adds the following to BorderLayout:
     * Taskbar
     * Display
     * Titlebar
     */
    private void setUpComponents() {
        Display display = new Display(habitTrackerController);
        Titlebar titlebar = new Titlebar();

        this.add(display, BorderLayout.CENTER);
        this.add(titlebar, BorderLayout.NORTH);
        this.add(new Taskbar(habitTrackerController, display, titlebar), BorderLayout.WEST);
    }
}
