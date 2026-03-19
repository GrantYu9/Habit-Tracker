package gui.mainframe.display.habit;

import javax.swing.JButton;
import javax.swing.JPanel;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.habit.Habit;

// Heatmaps of the habits, showcasing 1 month of history
@ExcludeFromJacocoGeneratedReport
public class Heatmap extends JPanel {
    private Habit habit; // Habit this represents

    private HabitGUI habitGUI; // Access to swapToBar() method

    /*
    EFFECTS:
    Instantiates heatmap such that
    this.habit = habit;
    this.habitGUI = habitGUI;
    The layout is !!!
    !!!
     */
    public Heatmap(Habit habit, HabitGUI habitGUI) {
        this.habit = habit;
        this.habitGUI = habitGUI;

        // !!! layout gridlayout
        
        makeCalendar();
        makeSwapButton();
    }

    // !!!
    private void makeCalendar() {
        // !!!
    }
    
    // !!!
    private void makeSwapButton() {
        JButton button = new JButton("Swap");
        button.addActionListener(actionEvent -> {
            habitGUI.swapToHeatmap();
        });
        this.add(button);
    }
}
