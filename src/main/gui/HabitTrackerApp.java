package gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.time.LocalDateTime;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import gui.window.MainFrame;
import model.exceptions.EmptyLastTimeFileException;
import model.habit.HabitCycleManager;
import model.organization.centralization.AllGenericPagesPage;
import model.organization.centralization.AllHabitsPage;
import model.organization.centralization.AllTagPagesPage;
import model.organization.specialpages.FavouritesPage;
import model.organization.specialpages.HomePage;
import persistence.AllGenericPagesPageDataManager;
import persistence.AllHabitsPageDataManager;
import persistence.LastTimeManager;

// The full habit tracker, with GUI from MainFrame and backend logic from Logic
@ExcludeFromJacocoGeneratedReport
public class HabitTrackerApp {
    private LocalDateTime lastTime;

    private String destinationAllGenericPagesPage;
    private String destinationAllHabitsPage;
    private String destinationLastTime;

    private AllGenericPagesPage allGenericPagesPage;
    private AllHabitsPage allHabitsPage;
    private AllTagPagesPage allTagPagesPage;
    private FavouritesPage favouritesPage;
    private HomePage homePage;
    private HabitCycleManager habitCycleManager;

    private AllGenericPagesPageDataManager allGenericPagesPageDataManager;
    private AllHabitsPageDataManager allHabitsPageDataManager;
    private LastTimeManager lastTimeManager;

    // EFFECTS: Sets up the program
    public HabitTrackerApp() {
        initHabitTrackerAppBackend();
        refreshFields();
        initHabitTrackerAppGUI();
    }

    // EFFECTS: Initializes dependencies
    private void initHabitTrackerAppBackend() {
        destinationAllGenericPagesPage = "./data/AllGenericPagesPage.json";
        destinationAllHabitsPage = "./data/AllHabitsPage.json";
        destinationLastTime = "./data/LastTime.json";

        allGenericPagesPage = new AllGenericPagesPage();
        allHabitsPage = new AllHabitsPage();
        allTagPagesPage = new AllTagPagesPage();

        favouritesPage = new FavouritesPage();
        homePage = new HomePage();

        allGenericPagesPageDataManager = new AllGenericPagesPageDataManager(destinationAllGenericPagesPage,
                allGenericPagesPage);
        allHabitsPageDataManager = new AllHabitsPageDataManager(allHabitsPage, destinationAllHabitsPage);
        lastTimeManager = new LastTimeManager(destinationLastTime);

        try {
            lastTime = lastTimeManager.readFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EmptyLastTimeFileException e) {
            lastTime = LocalDateTime.now();
        } // !!! is this the same with gui?

        habitCycleManager = new HabitCycleManager(allHabitsPage, lastTime);
    }

    // MODIFIES: this
    // EFFECTS: Resets fields as needed
    private void refreshFields() {
        habitCycleManager.cycleAllHabitsAtStartup(LocalDateTime.now());
        habitCycleManager.scheduleAllHabits(LocalDateTime.now());
    }

    // MODIFIES: this
    // EFFECTS: Instantiates MainFrame and implements a feature where upon detecting
    // the window closing, it will write the time to file and exit
    private void initHabitTrackerAppGUI() {
        MainFrame mainFrame = new MainFrame();

        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                try {
                    lastTimeManager.writeToFile(LocalDateTime.now());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.exit(0);
            }
        });
    }
}
