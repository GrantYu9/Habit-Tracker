package gui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import model.exceptions.EmptyLastTimeFileException;
import model.exceptions.HabitNotFoundException;
import model.habit.Habit;
import model.habit.HabitCycleManager;
import model.habit.HabitDecrement;
import model.habit.HabitIncrement;
import model.organization.centralization.AllGenericPagesPage;
import model.organization.centralization.AllHabitsPage;
import model.organization.centralization.AllTagPagesPage;
import model.organization.specialpages.FavouritesPage;
import model.organization.specialpages.HomePage;
import model.organization.tree.Page;
import persistence.AllGenericPagesPageDataManager;
import persistence.AllHabitsPageDataManager;
import persistence.LastTimeManager;

// Provides core functionality of the app for GUI aspects
public class HabitTrackerController {
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

    // EFFECTS: Initializes HabitTrackerController such that required classes are
    // initialized and habits are refreshed as needed
    public HabitTrackerController() {
        initBackend();
        refreshSystem();
    }

    // MODIFIES: this
    // EFFECTS: Initializes dependencies
    private void initBackend() {
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
        }

        habitCycleManager = new HabitCycleManager(allHabitsPage, lastTime);
    }

    // MODIFIES: this
    // EFFECTS: Refresh values
    private void refreshSystem() {
        habitCycleManager.cycleAllHabitsAtStartup(LocalDateTime.now());
        habitCycleManager.scheduleAllHabits(LocalDateTime.now());

    }

    // MODIFIES: this
    // EFFECTS: Writes the time to file
    public void saveTime() {
        try {
            lastTimeManager.writeToFile(LocalDateTime.now());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: Saves state to file
    public void saveToFile() {
        try {
            allHabitsPageDataManager.writeToFile(allHabitsPage);
            allGenericPagesPageDataManager.writeToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: Loads state from file
    public void loadFromFile() {
        try {
            allHabitsPageDataManager.readFromFile(homePage, favouritesPage, allTagPagesPage);
            allGenericPagesPageDataManager.readFromFile(allHabitsPage);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (HabitNotFoundException e) { // This would be a programmer error; incorrect dependencies
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: Creates a habit
    public void makeHabit(int goal, int startingAmount, int stepAmount, String title, String unit, LocalTime cycleTime, boolean isIncrement) {
        Habit habit = null;

        if (isIncrement) {
            habit = new HabitIncrement(goal, startingAmount, stepAmount, title, cycleTime, LocalDate.now(), LocalDateTime.now(), habitCycleManager);
        } else {
            habit = new HabitDecrement(goal, startingAmount, stepAmount, title, cycleTime, LocalDate.now(), LocalDateTime.now(), habitCycleManager);
        }

        allHabitsPage.addToAllHabitsPage(habit);
        habit.setUnit(unit);
    }

    // MODIFIES: this
    // EFFECTS: Creates a page
    public void makePage(String title, List<Habit> habits) {
        Page page = new Page(title);
        allGenericPagesPage.addToPages(page);
        for (Habit habit : habits) {
            page.addHabit(habit);
        }
    }

    // GETTERS

    public AllHabitsPage getAllHabitsPage() {
        return allHabitsPage;
    }

    public AllGenericPagesPage getAllGenericPagesPage() {
        return allGenericPagesPage;
    }
}
