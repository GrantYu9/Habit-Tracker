package gui.mainframe;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

// Shows where the user is in the app
public class Titlebar extends JPanel {
    /*
    EFFECTS:
    Instantiates titlebar such that
    The layout is FlowLayout
    The background is light grey
     */
    public Titlebar() {
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.setBackground(Color.LIGHT_GRAY);
    }

    // EFFECTS: Displays title
    public void displayTitle(String title) {
        this.removeAll();
        JLabel label = new JLabel(title);
        this.add(label);
        this.revalidate();
        this.repaint();
    }
}
