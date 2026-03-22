package gui.mainframe.display.habit.heatmap;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

// Custom version of JPanel with custom paintComponent behaviour
@ExcludeFromJacocoGeneratedReport
public class DayTile extends JPanel {
    private int percentage;

    // EFFECTS: Calls super to instantiate DayTile and sets percentage to 0
    public DayTile() {
        super();

        percentage = 0;
    }

    // MODIFIES: this
    // EFFECTS: Has same properties as super.paintComponent from
    // javax.swing.JComponent but paints to certain height based on height
    @Override
    protected void paintComponent(Graphics graphic) {
        super.paintComponent(graphic);
        graphic.setColor(Color.GREEN);
        int fillHeight = (int) (getHeight() * ((double) percentage / 100));
        graphic.fillRect(0, getHeight() - fillHeight, getWidth(), fillHeight);
    }

    // SETTERS

    public void setPercentage(int percentage) {
        this.percentage = percentage;
        this.revalidate();
        this.repaint();
    }

    // GETTERS

    public int getPercentage() {
        return percentage;
    }
}
