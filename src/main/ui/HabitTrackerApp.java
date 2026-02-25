package ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

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
                System.out.println("");
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
            makePage();
        } else if (input.equals("pages")) {
            selectPage();
        } else {
            System.out.println("Invalid input.\n");
        }
    }

    // !!! too long
    /*
    REQUIRES:
    0 <= hour <= 23
    0 <= minute <= 59
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
        int goal;
        int startingAmount;
        int stepAmount;
        String title;
        int hour;
        int minute;
        LocalTime cycleTime;

        Habit habit;

        String input;

        System.out.print("Title: ");
        title = scanner.nextLine().strip();

        System.out.print("Goal: ");
        goal = scanner.nextInt();

        System.out.print("Starting amount: ");
        startingAmount = scanner.nextInt();

        System.out.print("Step amount: ");
        stepAmount = scanner.nextInt();

        System.out.print("We will now determine the cycle time. ");
        System.out.println("We will ask for the hour, followed by the minute, in 24-hour time.");
        System.out.print("Hour: ");
        hour = scanner.nextInt();
        System.out.print("Minute: ");
        minute = scanner.nextInt();
        cycleTime = LocalTime.of(hour, minute);
        scanner.nextLine();

        System.out.print("Do you want the habit to increment or decrement? ");
        System.out.print("'i' for increment, 'd' for decrement: ");
        input = scanner.nextLine().strip().toLowerCase();

        if (input.equals("i")) {
            habit = new HabitIncrement(goal, startingAmount, stepAmount, title, cycleTime, LocalDate.now(), 
                LocalDateTime.now(), habitCycleManager);
            allHabitsPage.addToAllHabitsPage(habit);
        } else if (input.equals("d")) {
            habit = new HabitDecrement(goal, startingAmount, stepAmount, title, cycleTime, LocalDate.now(), 
                LocalDateTime.now(), habitCycleManager);
        } else {
            System.out.println("Defaulting to increment.");
            habit = new HabitIncrement(goal, startingAmount, stepAmount, title, cycleTime, LocalDate.now(), 
                LocalDateTime.now(), habitCycleManager);
        }

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

        System.out.println("Would you like to make a tag (y/n): ");

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
    public void printHabits() {
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
    public void selectHabit() {
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

    // !!! too long
    // MODIFIES: habit
    // EFFECTS: Habit modification menu
    public void modifyHabit(Habit habit) {
        System.out.println("To step forward, type '+'.");
        System.out.println("To modify title, type 'title'.");
        System.out.println("To modify current amount, type 'current amount'.");
        System.out.println("To modify goal, type 'g'.");
        System.out.println("To modify starting amount, type 'starting amount'.");
        System.out.println("To modify step amount, type 'step amount'.");
        System.out.println("To modify cycle time, type 'time'.");
        System.out.println("To modify tags, type 'tag'.");

        System.out.print("What do you want to do: ");
        String input = scanner.nextLine().strip().toLowerCase();

        if (input.equals("+")) {
            habit.progressByStepAmount();
        } else if (input.equals("title")) {
            System.out.print("Title: ");
            input = scanner.nextLine().strip();
            habit.setTitle(input);
        } else if (input.equals("current amount")) {
            System.out.print("Current amount: ");
            int incoming = scanner.nextInt();
            habit.setCurrentAmountLogic(incoming);
            scanner.nextLine();
        } else if (input.equals("g")) {
            System.out.print("Goal: ");
            int incoming = scanner.nextInt();
            habit.setGoal(incoming);
            scanner.nextLine();
        } else if (input.equals("starting amount")) {
            System.out.print("Starting amount: ");
            int incoming = scanner.nextInt();
            habit.setStartingAmount(incoming);
            scanner.nextLine();
        } else if (input.equals("step amount")) {
            System.out.print("Step amount: ");
            int incoming = scanner.nextInt();
            habit.setStepAmount(incoming);
            scanner.nextLine();
        } else if (input.equals("time")) {
            System.out.print("Hours: ");
            int hours = scanner.nextInt();
            System.out.print("Minutes: ");
            int minutes = scanner.nextInt();
            habit.setCycleTime(LocalTime.of(hours, minutes));
            scanner.nextLine();
        } else if (input.equals("tag")) {
            System.out.print("Tag title: ");
            input = scanner.nextLine();
            selectTag(habit, input);
        } else {
            System.out.println("Invalid input.");
        }
    }

    // !!! too long
    // EFFECTS: Prints stats of a habit
    public void printHabit(Habit habit) {
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

        if (habit.getTags().isEmpty()) {
            System.out.println("No tags yet.");
        } else {
            System.out.println("TAGS:");
            printTags(habit);
        }
    }

    // MODIFIES: this
    // EFFECTS: Make a tag
    public Tag makeTag() {
        System.out.println("Title: ");
        String input = scanner.nextLine().strip();
        return new Tag(input);
    }

    // EFFECTS: Prints tags
    public void printTags(Habit habit) {
        for (Tag tag : habit.getTags()) {
            System.out.println(tag.getTitle());
        }
    }

    // EFFECTS: Select tag
    public void selectTag(Habit habit, String title) {
        for (Tag tag : habit.getTags()) {
            if (tag.getTitle().equals(title)) {
                modifyTag(habit, tag);
                return;
            }
        }

        System.out.println("Match not found.");
    }

    // EFFECTS: Modify tag
    public void modifyTag(Habit habit, Tag tag) {
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
    public void makePage() {
        System.out.print("Title: ");
        String input = scanner.nextLine();

        allGenericPagesPage.addToPages(new Page(input));
    }

    // EFFECTS: Selects a type of page, prints it and asks if you want to select a page or a habit
    public void pageTypeSelection() {
        System.out.println("For favourite page, type 'f'.");
        System.out.println("For home page, type 'h'.");
        System.out.println("For all generic pages, type 'g'.");
        System.out.println("For all tag pages, type 't'");

        System.out.print("What do you want to do: ");
        String input = scanner.nextLine().strip().toLowerCase();

        if (input.equals("f")) {
            printFavouritesPage();
            System.out.print("Do you want to select a habit (y/n): ");
            input = scanner.nextLine().strip().toLowerCase();

            if (input.equals("y")) {
                selectHabit();
            } else if (input.equals("n")) {
                return;
            } else {
                System.out.println("I guess not ...");
            }
        } else if (input.equals("h")) {
            printHomePage();
            System.out.print("Do you want to select a habit (y/n): ");
            input = scanner.nextLine().strip().toLowerCase();

            if (input.equals("y")) {
                selectHabit();
            } else if (input.equals("n")) {
                return;
            } else {
                System.out.println("I guess not ...");
            }
        } else if (input.equals("g")) {
            printAllGenericPages();
            System.out.print("Do you want to select a page (y/n): ");
            input = scanner.nextLine().strip().toLowerCase();

            if (input.equals("y")) {
                selectPage();
            } else if (input.equals("n")) {
                return;
            } else {
                System.out.println("I guess not ...");
            }
        } else if (input.equals("t")) {
            printAllTagPages();
            System.out.print("Do you want to select a page (y/n): ");
            input = scanner.nextLine().strip().toLowerCase();

            if (input.equals("y")) {
                selectPage();
            } else if (input.equals("n")) {
                return;
            } else {
                System.out.println("I guess not ...");
            }
        } else {
            System.out.println("Invalid input.");
        }
    }

    // EFFECTS: Print favourites page and asks if you want to select a habit
    public void printFavouritesPage() {
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
    public void printHomePage() {
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
    public void printAllGenericPages() {
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
    public void printAllTagPages() {
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

    // EFFECTS: Selects a page and asks if you want to modify it or print its contents
    public void selectPage() {
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

    // !!! too many lines
    // EFFECTS: Modify page
    public void modifyPage(Page page) {
        System.out.println("To change the title, type 't'.");
        System.out.println("To change the order, type 'o'.");
        System.out.println("To modify habits that appear, type 'h'.");

        System.out.print("What do you want to do: ");
        String input = scanner.nextLine().strip().toLowerCase();

        if (input.equals("t")) {
            System.out.print("New title: ");
            input = scanner.nextLine().strip().toLowerCase();
            page.setTitle(input);
        } else if (input.equals("o")) {
            System.out.print("Alphabetical or manual (a/m): ");
            input = scanner.nextLine().strip().toLowerCase();
            if (input.equals("a")) {
                page.setOrder(Order.ALPHABETICAL);
            } else if (input.equals("m")) {
                page.setOrder(Order.MANUAL);
            } else {
                System.out.println("Invalid input.");
            }
        } else if (input.equals("h")) {
            System.out.print("How many habits do you want to add: ");
            int count = scanner.nextInt();

            for (int i = 0; i < count; i++) {
                System.out.print("Title: ");
                input = scanner.nextLine().strip();
                addHabit(page, input);
            }

            scanner.nextLine();
        } else {
            System.out.println("Invalid input.");
        }
    }

    // EFFECTS: Can add habits to a page
    public void addHabit(Page page, String title) {
        for (Habit habit : allHabitsPage.getHabits()) {
            if (habit.getTitle().equals(title)) {
                page.addHabit(habit);
            } else {
                System.out.println("Match not found.");
            }
        }
    }

    // EFFECTS: Prints page and asks if you want to modify a ahbit
    public void printPage(Page page) {
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
    public void setUp() {
        keepGoing = true;
        scanner = new Scanner(System.in);
    }

    // EFFECTS: Prints a greeting, introduction, and home page
    public void greeting() {
        System.out.println("Hello!");

        StringBuilder intro = new StringBuilder();

        intro.append("Welcome to your habit tracker!"); // !!!

        System.out.println(intro);
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
