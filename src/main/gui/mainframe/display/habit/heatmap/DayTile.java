package gui.mainframe.display.habit.heatmap;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

// Custom version of JPanel with custom paintComponent behaviour
public class DayTile extends JPanel {
    private int customHeight;

    // EFFECTS: Calls super to instantiate DayTile
    public DayTile() {
        super();
    }

    // MODIFIES: this
    // EFFECTS: Has same properties as super.paintComponent from
    // javax.swing.JComponent but paints to certain height based on height
    @Override
    protected void paintComponent(Graphics graphic) {
        super.paintComponent(graphic);
        graphic.setColor(Color.GREEN);
        graphic.fillRect(0, getHeight() - customHeight, getWidth(), customHeight);
    }

    // SETTERS

    public void setCustomHeight(int customHeight) {
        this.customHeight = customHeight;
    }

    // GETTERS

    public int getCustomHeight() {
        return customHeight;
    }
}
