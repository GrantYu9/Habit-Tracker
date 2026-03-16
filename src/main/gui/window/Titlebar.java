package gui.window;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

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
        JLabel label = new JLabel(title);
        this.add(label);
    }
}
