package gui.mainframe.display.lists;

import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import gui.mainframe.display.Display;
import model.organization.tree.Page;

// Where a list of pages will be displayed
@ExcludeFromJacocoGeneratedReport
public class Pages extends JPanel {
    private List<Page> pages; // !!!

    private Display display; // !!!

    // !!! scrollpane

    /*
    Instantiates Pages such that
    this.pages = pages
    this.display = display

    The Layout is BoxLayout, vertically aligned

    Displays all the pages
     */
    public Pages(List<Page> pages, Display display) {
        this.pages = pages;
        this.display = display;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        displayPages();
    }

    // MODIFIES: this
    // EFFECTS: For each page in pages, makes a pageButton
    private void displayPages() {
        for (Page page : pages) {
            makePageButton(page);
        }
    }

    /*
    MODIFIES:
    this
    EFFECTS:
    Creates a button with title page.getTitle()
    On click, shows the habits in that page
     */
    private void makePageButton(Page page) {
        JButton button = new JButton(page.getTitle());
        button.addActionListener(actionEvent -> {
            display.showHabits(page.getHabits());
        });
    }
}
