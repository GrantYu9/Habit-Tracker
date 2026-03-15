package gui.window;

import java.awt.BorderLayout;
import javax.swing.JFrame;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

// The overarching window of which all content will be displayed. Also where all the GUI action will be hosted
@ExcludeFromJacocoGeneratedReport
public class MainFrame extends JFrame {
    // EFFECTS: Instantiates the JFrame by configuring the default settings,
    // applying the visual components, and making it visible
    public MainFrame() {
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
        this.setTitle("Habit Tracker");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1_000, 1_000); // !!! play with size
        this.setLocationRelativeTo(null); // !!! test this works, centering
        this.setLayout(new BorderLayout());
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
        this.add(new Taskbar(), BorderLayout.WEST);
        this.add(new Display(), BorderLayout.CENTER);
        this.add(new Titlebar(), BorderLayout.NORTH);
    }
}
