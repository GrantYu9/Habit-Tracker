package ui;

import java.time.LocalDateTime;
import java.util.Scanner;

import model.habit.Habit;
import model.habit.HabitCycleManager;
import model.organization.centralization.AllGenericPagesPage;
import model.organization.centralization.AllHabitsPage;
import model.organization.centralization.AllTagPagesPage;
import model.organization.specialpages.FavouritesPage;
import model.organization.specialpages.HomePage;
import model.organization.tree.Page;

/*
Habit tracker
 */
public class HabitTrackerApp {
    /*
    This is meant to simulate "last time", which is supposed to be the timestamp of the last moment the program was up
    This will be properly implemented in Phase 2, as data persistence is outside the scope of Phase 1
     */
    private LocalDateTime psuedoLastTime;

    private AllGenericPagesPage allGenericPagesPage;
    private AllHabitsPage allHabitsPage;
    private AllTagPagesPage allTagPagesPage;
    private FavouritesPage favouritesPage;
    private HomePage homePage;
    private HabitCycleManager habitCycleManager;

    private boolean keepGoing; // To handle the program uptime
    private Scanner scanner; // To read input

    // EFFECTS: Sets up the program
    public HabitTrackerApp() {
        initHabitTrackerApp();

        refreshFields();
    }

    // EFFECTS: Initializes dependencies
    public void initHabitTrackerApp() {
        psuedoLastTime = LocalDateTime.now();

        allGenericPagesPage = new AllGenericPagesPage();
        allHabitsPage = new AllHabitsPage();
        allTagPagesPage = new AllTagPagesPage();

        favouritesPage = new FavouritesPage();
        homePage = new HomePage();

        // !!! Change this in Phase 2
        habitCycleManager = new HabitCycleManager(allHabitsPage, psuedoLastTime);
    }

    // EFFECTS: Resets fields as needed
    public void refreshFields() {
        habitCycleManager.cycleAllHabitsAtStartup(LocalDateTime.now());
        habitCycleManager.scheduleAllHabits(LocalDateTime.now());
    }

    // MODIFIES: this
    // EFFECTS: Runs the application and handles inputs
    public void runHabitTrackerApp() {
        setUp();

        greeting();

        while (keepGoing) {
            displayInputMenu();

            System.out.print("What would you like to do: ");
            String input = scanner.nextLine().strip().toLowerCase();
            
            if (input.equals("q")) {
                keepGoing = false;
            } else {
                handleMenuInput(input);
            }
        }

        cleanUp();

        valediction();
    }

    // EFFECTS: Prints out a menu with inputs and descriptions
    public void displayInputMenu() {
        StringBuilder inputMenu = new StringBuilder();

        inputMenu.append('\n');
        inputMenu.append("To quit, type 'q'.\n");
        inputMenu.append("To initialize a habit, type 'h'.\n");
        inputMenu.append("To view or modify a habit, type 'habits'.\n");
        inputMenu.append("To initialize a page, type 'p'.\n");
        inputMenu.append("To view or modify a page, type 'pages'.\n");
        
        System.out.println(inputMenu);
    }

    // MODIFIES: this
    // EFFECTS: Handles the input
    public void handleMenuInput(String input) {
        if (input.equals("h")) {
            makeHabit();
        } else if (input.equals("habits")) {
            printHabits();
        } else if (input.equals("p")) {
            // !!!
        } else if (input.equals("pages")) {
            // !!!
        } else {
            System.out.println("Invalid input.\n");
        }
    }

    /*
    MODIFIES:
    this
    EFFECTS:
    Creates a habit and adds it to allHabitsPage
    May also add it to the following
        AllTagPagesPage
        FavouritesPage
        HomePage
    May also initialize and add to the following
        TagPage
     */
    public void makeHabit() {
        // !!!
    }

    // EFFECTS: Prints all habits and asks if you want to select a habit
    public void printHabits() {
        // !!!
    }

    // EFFECTS: Selects a habit and asks if you want to modify it
    public Habit selectHabit() {
        return null; // stub
    }

    // EFFECTS: Habit modification menu
    public void modifyHabit(Habit habit) {
        // !!!
    }

    // EFFECTS: Selects a type of page, prints it and asks if you want to select a page or a habit
    public void pageTypeSelection() {
        // !!!
    }

    // EFFECTS: Print favourites page and asks if you want to select a habit
    public void printFavouritesPage() {
        // !!!
    }

    // EFFECTS: Print home page and asks if you want to select a habit
    public void printHomePage() {
        // !!!
    }

    // EFFECTS: Prints allGenericPagesPage and asks if you want to select a page
    public void printAllGenericPages() {
        // !!!
    }

    // EFFECTS: Prints allTagPages and asks if you want to select a page
    public void printAllTagPages() {
        // !!!
    }

    // EFFECTS: Selects a page and asks if you want to modify it or print its contents
    public Page selectPage() {
        /// !!!
        return null; // stub
    }

    // EFFECTS: Modify page
    public void modifyPage(Page page) {
        // !!!
    }

    // EFFECTS: Prints page and asks if you want to modify a ahbit
    public Habit printPage() {
        return null;
    }

    // MODIFIES: this
    // EFFECTS: Sets up fields for runHabitTrackerApp
    public void setUp() {
        keepGoing = true;
        scanner = new Scanner(System.in);
    }

    // EFFECTS: Prints a greeting, introduction, and home page
    public void greeting() {
        System.out.println("Hello!");

        StringBuilder intro = new StringBuilder();

        intro.append("Welcome to your habit tracker!"); // !!!

        printHomePage();
    }

    // MODIFIES:
    // EFFECTS: Cleans up anything needed and prints a valediction
    public void cleanUp() {
        scanner.close();
    }

    // EFFECTS: Prints a farewell statement
    public void valediction() {
        System.out.println("Thank you, farewell!");
    }
}
