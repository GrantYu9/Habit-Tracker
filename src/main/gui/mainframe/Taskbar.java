package gui.mainframe;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import gui.HabitTrackerController;
import gui.mainframe.display.Display;

// A sidebar on the left that will be quite slim but span the full vertical space, housing our controls will reside
@ExcludeFromJacocoGeneratedReport
public class Taskbar extends JPanel {
    private HabitTrackerController habitTrackerController; // Access to the backend logic of the app
    private Display display; // Access to the main GUI display of the app
    private Titlebar titlebar; // The titlebar

    /*
     * EFFECTS:
     * Instantiates Taskbar such that
     * this.habitTrackerController = habitTrackerController;
     * The layout is GridLayout
     * The background is grey
     * Buttons are created
     */
    public Taskbar(HabitTrackerController habitTrackerController, Display display, Titlebar titlebar) {
        this.habitTrackerController = habitTrackerController;
        this.display = display;
        this.titlebar = titlebar;

        this.setLayout(new GridLayout(6, 1));
        this.setBackground(Color.GRAY);
        makeButtons();
        
    }

    // MODIFES: this
    // EFFECTS: Instantiates all required buttons
    private void makeButtons() {
        saveToFileButton();
        loadFromFileButton();
        makeHabitButton();
        makePageButton();
        showAllHabitsButton();
        showAllPagesButton();
    }

    /*
    MODIFIES:
    this
    EFFECTS:
    Instantiates a button named "Save"
    On click, saves state to file
     */
    private void saveToFileButton() {
        JButton button = new JButton("Save");
        button.addActionListener(actionEvent -> {
            habitTrackerController.saveToFile();
        });
        this.add(button);
    }

    /*
    MODIFIES:
    this
    EFFECTS:
    Instantiates a button named "Load"
    On click, loads state from file
     */
    private void loadFromFileButton() {
        JButton button = new JButton("Load");
        button.addActionListener(actionEvent -> {
            habitTrackerController.loadFromFile();
        });
        this.add(button);
    }

    /*
    MODIFIES:
    this
    EFFECTS:
    Instantiates a button named "Make habit"
    On click, shows habit instantiation page
     */
    private void makeHabitButton() {
        JButton button = new JButton("Make habit");
        button.addActionListener(actionEvent -> {
            display.makeHabit();
            titlebar.displayTitle("Habit instantiation");
        });
        this.add(button);
    }

    /*
    MODIFIES:
    this
    EFFECTS:
    Instantiates a button named "Make page"
    On click, shows page instantiation page
     */
    private void makePageButton() {
        JButton button = new JButton("Make page");
        button.addActionListener(actionEvent -> {
            display.makePage();
            titlebar.displayTitle("Page instantiation");
        });
        this.add(button);
    }

    /*
    MODIFIES:
    this
    EFFECTS:
    Instantiates a button named "Show all habits"
    On click, shows all habits
     */
    private void showAllHabitsButton() {
        JButton button = new JButton("Show all habits");
        button.addActionListener(actionEvent -> {
            display.showHabits(habitTrackerController.getAllHabitsPage().getHabits());
            titlebar.displayTitle("All habits");
        });
        this.add(button);
    }

    /*
    MODIFIES:
    this
    EFFECTS:
    Instantiates a button named "Show all pages"
    On click, shows all pages
     */
    private void showAllPagesButton() {
        JButton button = new JButton("Show all pages");
        button.addActionListener(actionEvent -> {
            display.showPages(habitTrackerController.getAllGenericPagesPage().getPages(), display);
            titlebar.displayTitle("All pages");
        });
        this.add(button);
    }
}
