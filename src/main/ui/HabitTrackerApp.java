package ui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import model.exceptions.EmptyLastTimeFileException;
import model.exceptions.HabitNotFoundException;
import model.habit.Habit;
import model.habit.HabitCycleManager;
import model.habit.HabitDecrement;
import model.habit.HabitIncrement;
import model.organization.Tag;
import model.organization.centralization.AllGenericPagesPage;
import model.organization.centralization.AllHabitsPage;
import model.organization.centralization.AllTagPagesPage;
import model.organization.specialpages.FavouritesPage;
import model.organization.specialpages.HomePage;
import model.organization.specialpages.TagPage;
import model.organization.tree.Page;
import model.organization.tree.Page.Order;
import persistence.AllGenericPagesPageDataManager;
import persistence.AllHabitsPageDataManager;
import persistence.LastTimeManager;

/*
Habit tracker
 */
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

    private boolean keepGoing; // To handle the program uptime
    private Scanner scanner; // To read input

    // EFFECTS: Sets up the program
    public HabitTrackerApp() {
        initHabitTrackerApp();

        refreshFields();
    }

    // EFFECTS: Initializes dependencies
    private void initHabitTrackerApp() {
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

    // EFFECTS: Resets fields as needed
    private void refreshFields() {
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
                System.out.println("");
                handleMenuInput(input);
            }
        }

        cleanUp();

        valediction();
    }

    // EFFECTS: Prints out a menu with inputs and descriptions
    private void displayInputMenu() {
        StringBuilder inputMenu = new StringBuilder();

        inputMenu.append('\n');
        inputMenu.append("To quit, type 'q'.\n");
        inputMenu.append("To save to file, type 's'.\n");
        inputMenu.append("To load from file, type 'l'.\n");
        inputMenu.append("To initialize a habit, type 'h'.\n");
        inputMenu.append("To view or modify a habit, type 'habits'.\n");
        inputMenu.append("To initialize a page, type 'p'.\n");
        inputMenu.append("To view or modify a page, type 'pages'.\n");

        System.out.println(inputMenu);
    }

    // MODIFIES: this
    // EFFECTS: Handles the input
    private void handleMenuInput(String input) {
        if (input.equals("s")) {
            saveToFile();
        } else if (input.equals("l")) {
            loadFromFile();
        } else if (input.equals("h")) {
            makeHabit();
        } else if (input.equals("habits")) {
            printHabits();
        } else if (input.equals("p")) {
            makePage();
        } else if (input.equals("pages")) {
            pageTypeSelection();
        } else {
            System.out.println("Invalid input.\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: Saves state to file
    private void saveToFile() {
        System.out.println("Saving to file ...");

        try {
            allHabitsPageDataManager.writeToFile(allHabitsPage);
            allGenericPagesPageDataManager.writeToFile();
        } catch (IOException e) {
            System.out.println("Failed to save!");
            e.printStackTrace();
        }

        System.out.println("Save successful!");
    }

    // MODIFIES: this
    // EFFECTS: Reconstructs state by loading from file
    private void loadFromFile() {
        System.out.println("Loading from file ...");

        try {
            allHabitsPageDataManager.readFromFile(homePage, favouritesPage, allTagPagesPage);
            allGenericPagesPageDataManager.readFromFile(allHabitsPage);
        } catch (IOException e) {
            System.out.println("Failed to save!");
            e.printStackTrace();
        } catch (HabitNotFoundException e) {
            System.out.println("Programmer error: incorrect dependencies");
            e.printStackTrace();
        }

        System.out.println("Successfully loaded from file!");
    }

    /*
     * REQUIRES:
     * 0 <= hour <= 23
     * 0 <= minute <= 59
     * MODIFIES:
     * this
     * EFFECTS:
     * Creates a habit and adds it to allHabitsPage
     * May also add it to the following
     * AllTagPagesPage
     * FavouritesPage
     * HomePage
     * May also initialize and add to the following
     * TagPage
     */
    private void makeHabit() {
        Habit habit;

        System.out.print("Title: ");
        String title = scanner.nextLine().strip();

        System.out.print("Goal: ");
        int goal = scanner.nextInt();

        System.out.print("Starting amount: ");
        int startingAmount = scanner.nextInt();

        System.out.print("Step amount: ");
        int stepAmount = scanner.nextInt();

        System.out.print("We will now determine the cycle time. ");
        System.out.println("We will ask for the hour, followed by the minute, in 24-hour time.");
        System.out.print("Hour: ");
        int hour = scanner.nextInt();
        System.out.print("Minute: ");
        int minute = scanner.nextInt();
        LocalTime cycleTime = LocalTime.of(hour, minute);
        scanner.nextLine();

        habit = instantiateHabit(goal, startingAmount, stepAmount, title, cycleTime, habitCycleManager);

        setUpUnit(habit);

        setUpTag(habit);

        allHabitsPage.addToAllHabitsPage(habit);
    }

    // MODIFIES: this
    // EFFECTS: Instantiates habit based on if user wants to increment or decrement
    private Habit instantiateHabit(int goal, int startingAmount, int stepAmount, String title, LocalTime cycleTime,
            HabitCycleManager habitCycleManager) {
        String input;
        System.out.print("Do you want the habit to increment or decrement? ");
        System.out.print("'i' for increment, 'd' for decrement: ");
        input = scanner.nextLine().strip().toLowerCase();

        if (input.equals("d")) {
            return new HabitDecrement(goal, startingAmount, stepAmount, title, cycleTime, LocalDate.now(),
                    LocalDateTime.now(), habitCycleManager);
        } else {
            System.out.println("Defaulting to increment.");
            return new HabitIncrement(goal, startingAmount, stepAmount, title, cycleTime, LocalDate.now(),
                    LocalDateTime.now(), habitCycleManager);
        }
    }

    // REQUIRES: unit has at least one character
    // MODIFIES: habit
    // EFFECTS: Sets up a unit, if user wants
    private void setUpUnit(Habit habit) {
        String input;
        System.out.print("Do you want to set a unit (y/n):");
        input = scanner.nextLine().strip().toLowerCase();

        if (input.equals("y")) {
            System.out.print("Unit: ");
            habit.setUnit(scanner.nextLine().strip());
        } else if (input.equals("n")) {
            return;
        } else {
            System.out.println("I guess not ...");
        }
    }

    // REQUIRES: tag title has at least one character
    // MODIFIES: habit
    // EFFECTS: sets up tags, if user wants
    private void setUpTag(Habit habit) {
        String input;

        System.out.println("Would you like to make a tag (y/n): ");
        input = scanner.nextLine().strip().toLowerCase();

        if (input.equals("y")) {
            System.out.println("How many: ");
            int count = scanner.nextInt();

            for (int i = 0; i < count; i++) {
                habit.addTagAndSortTags(makeTag(), homePage, favouritesPage, allTagPagesPage);
            }

            scanner.nextLine();

        } else if (input.equals("n")) {
            return;
        } else {
            System.out.println("I guess not ...");
        }
    }

    // EFFECTS: Prints all habits and asks if you want to select a habit
    private void printHabits() {
        List<Habit> habits = allHabitsPage.getHabits();

        if (habits.isEmpty()) {
            System.out.println("No habits yet.");
            return;
        }

        for (Habit habit : habits) {
            System.out.println("-------------------------------------------");
            printHabit(habit);
            System.out.println("-------------------------------------------");
        }

        System.out.print("Do you want to select a habit (y/n): ");
        String input = scanner.nextLine().strip().toLowerCase();

        if (input.equals("y")) {
            selectHabit();
        } else if (input.equals("n")) {
            return;
        } else {
            System.out.println("I guess not ...");
        }
    }

    // MODIFIES: habit
    // EFFECTS: Selects a habit and asks if you want to modify it
    private void selectHabit() {
        System.out.print("Title of habit you want to select (watch cases and spacing): ");
        String input = scanner.nextLine().strip();

        for (Habit habit : allHabitsPage.getHabits()) {
            if (input.equals(habit.getTitle())) {
                printHabit(habit);
                System.out.print("Do you want to modify this habit (y/n): ");
                input = scanner.nextLine().strip().toLowerCase();

                if (input.equals("y")) {
                    modifyHabit(habit);
                } else if (input.equals("n")) {
                    return;
                } else {
                    System.out.println("I guess not ...");
                }

                return;
            }
        }

        System.out.println("Match not found.");
    }

    // MODIFIES: habit
    // EFFECTS: Habit modification menu
    private void modifyHabit(Habit habit) {
        modifyHabitPrintMenu();
        modifyHabitTakeInput(habit);
    }

    // EFFECTS: Prints modifyHabit menu
    private void modifyHabitPrintMenu() {
        System.out.println("To step forward, type '+'.");
        System.out.println("To modify title, type 'title'.");
        System.out.println("To modify current amount, type 'current amount'.");
        System.out.println("To modify goal, type 'g'.");
        System.out.println("To modify starting amount, type 'starting amount'.");
        System.out.println("To modify step amount, type 'step amount'.");
        System.out.println("To modify cycle time, type 'time'.");
        System.out.println("To modify tags, type 'tag'.");
    }

    // MODIFIES: habit
    // EFFECTS: Takes input and allows user to change something based on the input
    private void modifyHabitTakeInput(Habit habit) {
        System.out.print("What do you want to do: ");
        String input = scanner.nextLine().strip().toLowerCase();

        if (input.equals("+")) {
            habit.progressByStepAmount();
        } else if (input.equals("title")) {
            modifyHabitModifyTitle(habit);
        } else if (input.equals("current amount")) {
            modifyHabitModifyCurrentAmount(habit);
        } else if (input.equals("g")) {
            modifyHabitModifyGoal(habit);
        } else if (input.equals("starting amount")) {
            modifyHabitModifyStartingAmount(habit);
        } else if (input.equals("step amount")) {
            modifyHabitModifyStepAmount(habit);
        } else if (input.equals("time")) {
            modifyHabitModifyTime(habit);
        } else if (input.equals("tag")) {
            modifyHabitModifyTag(habit);
        } else {
            System.out.println("Invalid input.");
        }
    }

    // MODIFIES: habit
    // EFFECTS: Changes title to input
    private void modifyHabitModifyTitle(Habit habit) {
        System.out.print("Title: ");
        String input = scanner.nextLine().strip();
        habit.setTitle(input);
    }

    // MODIFIES: habit
    // EFFECTS: Changes currentAmount to input
    private void modifyHabitModifyCurrentAmount(Habit habit) {
        System.out.print("Current amount: ");
        int incoming = scanner.nextInt();
        habit.setCurrentAmountLogic(incoming);
        scanner.nextLine();
    }

    // MODIFIES: habit
    // EFFECTS: Changes goal to input
    private void modifyHabitModifyGoal(Habit habit) {
        System.out.print("Goal: ");
        int incoming = scanner.nextInt();
        habit.setGoal(incoming);
        scanner.nextLine();
    }

    // MODIFIES: habit
    // EFFECTS: Changes startingAmount to input
    private void modifyHabitModifyStartingAmount(Habit habit) {
        System.out.print("Starting amount: ");
        int incoming = scanner.nextInt();
        habit.setStartingAmount(incoming);
        scanner.nextLine();
    }

    // MODIFIES: habit
    // EFFECTS: Changes stepAmount to input
    private void modifyHabitModifyStepAmount(Habit habit) {
        System.out.print("Step amount: ");
        int incoming = scanner.nextInt();
        habit.setStepAmount(incoming);
        scanner.nextLine();
    }

    // MODIFIES: habit
    // EFFECTS: Changes cycleTime to input
    private void modifyHabitModifyTime(Habit habit) {
        System.out.print("Hours: ");
        int hours = scanner.nextInt();
        System.out.print("Minutes: ");
        int minutes = scanner.nextInt();
        habit.setCycleTime(LocalTime.of(hours, minutes));
        scanner.nextLine();
    }

    // MODIFIES: habit
    // EFFECTS: Changes tag to input
    private void modifyHabitModifyTag(Habit habit) {
        System.out.print("Tag title: ");
        String input = scanner.nextLine();
        selectTag(habit, input);
    }

    // EFFECTS: Prints stats of a habit
    private void printHabit(Habit habit) {
        printHabitPrintData(habit);

        if (habit.getTags().isEmpty()) {
            System.out.println("No tags yet.");
        } else {
            System.out.println("TAGS:");
            printTags(habit);
        }
    }

    // EFFECTS: Prints out most of the data for habit
    private void printHabitPrintData(Habit habit) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("H:mm");

        System.out.print("Title: ");
        System.out.println(habit.getTitle());
        System.out.print("Progress percentage: ");
        System.out.print(habit.getProgressPercentage());
        System.out.println("%");
        System.out.print("Progress status: ");
        System.out.println(habit.getProgressType());
        System.out.print("Current amount: ");
        System.out.println(habit.getCurrentAmount());
        System.out.print("Goal: ");
        System.out.println(habit.getGoal());
        System.out.print("Overload amount: ");
        System.out.println(habit.getOverloadAmount());
        System.out.print("Starting amount: ");
        System.out.println(habit.getStepAmount());
        System.out.print("Cycle time: ");
        System.out.println(habit.getCycleTime().format(format));
    }

    // MODIFIES: this
    // EFFECTS: Make a tag
    private Tag makeTag() {
        System.out.println("Title: ");
        String input = scanner.nextLine().strip();
        return new Tag(input);
    }

    // EFFECTS: Prints tags
    private void printTags(Habit habit) {
        for (Tag tag : habit.getTags()) {
            System.out.println(tag.getTitle());
        }
    }

    // EFFECTS: Select tag
    private void selectTag(Habit habit, String title) {
        for (Tag tag : habit.getTags()) {
            if (tag.getTitle().equals(title)) {
                modifyTag(habit, tag);
                return;
            }
        }

        System.out.println("Match not found.");
    }

    // EFFECTS: Modify tag
    private void modifyTag(Habit habit, Tag tag) {
        System.out.println("To modify title, type 't'.");
        String input = scanner.nextLine();

        if (input.equals("t")) {
            System.out.print("Title: ");
            input = scanner.nextLine();
            tag.setTitle(input);
        } else {
            System.out.println("Invalid input.");
        }
    }

    // EFFECTS: Make page
    private void makePage() {
        System.out.print("Title: ");
        String input = scanner.nextLine();

        allGenericPagesPage.addToPages(new Page(input));
    }

    // EFFECTS: Selects a type of page, prints it and asks if you want to select a
    // page or a habit
    private void pageTypeSelection() {
        pageTypeSelectionPrintMenu();
        
        System.out.print("What do you want to do: ");
        String input = scanner.nextLine().strip().toLowerCase();

        if (input.equals("f")) {
            favouritePageMenu();
        } else if (input.equals("h")) {
            homePageMenu();
        } else if (input.equals("g")) {
            allGenericPagesPageMenu();
        } else if (input.equals("t")) {
            allTagPagesPageMenu();
        } else {
            System.out.println("Invalid input.");
        }
    }

    // EFFECTS: Prints menu of types of pages
    private void pageTypeSelectionPrintMenu() {
        System.out.println("For favourite page, type 'f'.");
        System.out.println("For home page, type 'h'.");
        System.out.println("For all generic pages, type 'g'.");
        System.out.println("For all tag pages, type 't'");
    }

    // MODIFIES: this
    // EFFECTS: User may change favouritesPage
    private void favouritePageMenu() {
        printFavouritesPage();
        System.out.print("Do you want to select a habit (y/n): ");
        String input = scanner.nextLine().strip().toLowerCase();

        if (input.equals("y")) {
            selectHabit();
        } else if (input.equals("n")) {
            return;
        } else {
            System.out.println("I guess not ...");
        }
    }

    // MODIFIES: this
    // EFFECTS: User may change homePage
    private void homePageMenu() {
        printHomePage();
        System.out.print("Do you want to select a habit (y/n): ");
        String input = scanner.nextLine().strip().toLowerCase();

        if (input.equals("y")) {
            selectHabit();
        } else if (input.equals("n")) {
            return;
        } else {
            System.out.println("I guess not ...");
        }
    }

    // MODIFIES: this
    // EFFECTS: User may change allGenericPagesPage
    private void allGenericPagesPageMenu() {
        printAllGenericPages();
        System.out.print("Do you want to select a page (y/n): ");
        String input = scanner.nextLine().strip().toLowerCase();

        if (input.equals("y")) {
            selectPage();
        } else if (input.equals("n")) {
            return;
        } else {
            System.out.println("I guess not ...");
        }
    }

    // MODIFIES: this
    // EFFECTS: User may change allTagPages
    private void allTagPagesPageMenu() {
        printAllTagPages();
        System.out.print("Do you want to select a page (y/n): ");
        String input = scanner.nextLine().strip().toLowerCase();

        if (input.equals("y")) {
            selectPage();
        } else if (input.equals("n")) {
            return;
        } else {
            System.out.println("I guess not ...");
        }
    }

    // EFFECTS: Print favourites page and asks if you want to select a habit
    private void printFavouritesPage() {
        for (Habit habit : favouritesPage.getHabits()) {
            System.out.println("-------------------------------------------");
            printHabit(habit);
            System.out.println("-------------------------------------------");
        }

        System.out.print("Do you want to select a habit (y/n): ");
        String input = scanner.nextLine().strip().toLowerCase();

        if (input.equals("y")) {
            selectHabit();
        } else if (input.equals("n")) {
            return;
        } else {
            System.out.println("I guess not ...");
        }
    }

    // EFFECTS: Print home page and asks if you want to select a habit
    private void printHomePage() {
        for (Habit habit : homePage.getHabits()) {
            System.out.println("-------------------------------------------");
            printHabit(habit);
            System.out.println("-------------------------------------------");
        }

        System.out.print("Do you want to select a habit (y/n): ");
        String input = scanner.nextLine().strip().toLowerCase();

        if (input.equals("y")) {
            selectHabit();
        } else if (input.equals("n")) {
            return;
        } else {
            System.out.println("I guess not ...");
        }
    }

    // EFFECTS: Prints allGenericPagesPage and asks if you want to select a page
    private void printAllGenericPages() {
        for (Page page : allGenericPagesPage.getPages()) {
            System.out.println("-------------------------------");
            System.out.print("Title: ");
            System.out.println(page.getTitle());
            if (page.getHabits().isEmpty()) {
                System.out.println("No habits yet.");
            } else {
                System.out.println("HABITS:");
                for (Habit habit : page.getHabits()) {
                    System.out.println(habit.getTitle());
                }
            }
            System.out.println("-------------------------------");
        }

        System.out.print("Do you want to select a page (y/n): ");
        String input = scanner.nextLine().strip().toLowerCase();

        if (input.equals("y")) {
            selectPage();
        } else if (input.equals("n")) {
            return;
        } else {
            System.out.println("I guess not ...");
        }
    }

    // EFFECTS: Prints allTagPages
    private void printAllTagPages() {
        for (TagPage page : allTagPagesPage.getTagPages()) {
            System.out.println("-------------------------------");
            System.out.print("Title: ");
            System.out.println(page.getTitle());
            if (page.getHabits().isEmpty()) {
                System.out.println("No habits yet.");
            } else {
                System.out.println("HABITS:");
                for (Habit habit : page.getHabits()) {
                    System.out.println(habit.getTitle());
                }
            }
            System.out.println("-------------------------------");
        }
    }

    // EFFECTS: Selects a page and asks if you want to modify it or print its
    // contents
    private void selectPage() {
        System.out.print("Title (watch case and spacing): ");
        String input = scanner.nextLine();

        for (Page page : allGenericPagesPage.getPages()) {
            if (page.getTitle().equals(input)) {
                System.out.print("Modify, print, or neiter (m/p/n): ");
                input = scanner.nextLine().strip().toLowerCase();

                if (input.equals("m")) {
                    modifyPage(page);
                } else if (input.equals("p")) {
                    printPage(page);
                } else if (input.equals("n")) {
                    return;
                } else {
                    System.out.println("Defaulting to neither.");
                }
            }
        }

        System.out.println("Match not found.");
    }

    // EFFECTS: Modify page
    private void modifyPage(Page page) {
        modifyPagePrintMenu();

        System.out.print("What do you want to do: ");
        String input = scanner.nextLine().strip().toLowerCase();

        if (input.equals("t")) {
            modifyPageModifyTitle(page);
        } else if (input.equals("o")) {
            modifyPageModifyOrder(page);
        } else if (input.equals("h")) {
            modifyPageModifyHabits(page);
        } else {
            System.out.println("Invalid input.");
        }
    }

    // EFFECTS: Prints menu of options for modifying the page
    private void modifyPagePrintMenu() {
        System.out.println("To change the title, type 't'.");
        System.out.println("To change the order, type 'o'.");
        System.out.println("To modify habits that appear, type 'h'.");
    }

    // MODIFIES: this
    // EFFECTS: User may change title
    private void modifyPageModifyTitle(Page page) {
        System.out.print("New title: ");
        String input = scanner.nextLine().strip().toLowerCase();
        page.setTitle(input);
    }

    // MODIFIES: this
    // EFFECTS: User may change order
    private void modifyPageModifyOrder(Page page) {
        System.out.print("Alphabetical or manual (a/m): ");
        String input = scanner.nextLine().strip().toLowerCase();
        if (input.equals("a")) {
            page.setOrder(Order.ALPHABETICAL);
        } else if (input.equals("m")) {
            page.setOrder(Order.MANUAL);
        } else {
            System.out.println("Invalid input.");
        }
    }

    // MODIFIES: this
    // EFFECTS: User may change what habits are in the page
    private void modifyPageModifyHabits(Page page) {
        System.out.print("How many habits do you want to add: ");
        int count = scanner.nextInt();

        for (int i = 0; i < count; i++) {
            System.out.print("Title: ");
            String input = scanner.nextLine().strip();
            addHabit(page, input);
        }

        scanner.nextLine();
    }

    // EFFECTS: Can add habits to a page
    private void addHabit(Page page, String title) {
        for (Habit habit : allHabitsPage.getHabits()) {
            if (habit.getTitle().equals(title)) {
                page.addHabit(habit);
            } else {
                System.out.println("Match not found.");
            }
        }
    }

    // EFFECTS: Prints page and asks if you want to modify a ahbit
    private void printPage(Page page) {
        for (Habit habit : page.getHabits()) {
            System.out.println("-------------------------------------------");
            printHabit(habit);
            System.out.println("-------------------------------------------");
        }

        System.out.print("Do you want to select a habit (y/n): ");
        String input = scanner.nextLine().strip().toLowerCase();

        if (input.equals("y")) {
            selectHabit();
        } else if (input.equals("n")) {
            return;
        } else {
            System.out.println("I guess not ...");
        }
    }

    // MODIFIES: this
    // EFFECTS: Sets up fields for runHabitTrackerApp
    private void setUp() {
        keepGoing = true;
        scanner = new Scanner(System.in);
    }

    // EFFECTS: Prints a greeting, introduction, and home page
    private void greeting() {
        System.out.println("Hello!");

        StringBuilder intro = new StringBuilder();

        intro.append("Welcome to your habit tracker!");

        System.out.println(intro);
    }

    // MODIFIES:
    // EFFECTS: Cleans up anything needed and prints a valediction
    private void cleanUp() {
        scanner.close();

        try {
            lastTimeManager.writeToFile(LocalDateTime.now());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // EFFECTS: Prints a farewell statement
    private void valediction() {
        System.out.println("Thank you, farewell!");
    }
}
