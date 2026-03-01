package persistence;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.habit.Habit;
import model.habit.HabitCycleManager;
import model.habit.HabitIncrement;
import model.organization.centralization.AllGenericPagesPage;
import model.organization.centralization.AllHabitsPage;
import model.organization.tree.Page;
import model.organization.tree.Page.Order;

public class TestAllGenericPagesPageDataManager {
    private String destinationEmpty;
    private String destinationGeneralRead;
    private String destinationGeneralWrite;

    private LocalTime localTime;
    private LocalDate localDate;
    private LocalDateTime localDateTime;

    private AllHabitsPage allHabitsPage;

    private HabitCycleManager habitCycleManager;

    private Habit habitRockClimbing;
    private Habit habitCardio;
    private Habit habitWateringPlants;
    private Habit habitPullingWeeds;
    private Habit habitFlashcards;
    private Habit habitGrindingQuestions;

    private Page pageExercise;
    private Page pageGardening;
    private Page pageStudying;

    private AllGenericPagesPage allGenericPagesPageEmpty;
    private AllGenericPagesPage allGenericPagesPageGeneralWrite;

    private AllGenericPagesPageDataManager allGenericPagesPageDataManagerEmpty;
    private AllGenericPagesPageDataManager allGenericPagesPageDataManagerGeneralRead;
    private AllGenericPagesPageDataManager allGenericPagesPageDataManagerGeneralWrite;

    private AllGenericPagesPage whatShouldBeAllGenericPagesPage;

    @BeforeEach
    public void runBeforeEach() {
        destinationEmpty = "./data/testing/TestAllGenericPagesPageDataManagerEmpty.json";
        destinationGeneralRead = "./data/testing/TestAllGenericPagesPageDataManagerGeneralRead.json";
        destinationGeneralWrite = "./data/testing/TestAllGenericPagesPageDataManagerGeneralWrite.json";

        localTime = LocalTime.MIN;
        localDate = LocalDate.of(2026, 1, 1);
        localDateTime = LocalDateTime.of(localDate, localTime);

        allHabitsPage = new AllHabitsPage();

        habitCycleManager = new HabitCycleManager(allHabitsPage, localDateTime);

        habitRockClimbing = new HabitIncrement(1, 0, 1, "Rock Climbing", localTime, localDate, localDateTime, habitCycleManager);
        habitCardio = new HabitIncrement(1, 0, 1, "Cardio", localTime, localDate, localDateTime, habitCycleManager);
        habitWateringPlants = new HabitIncrement(1, 0, 1, "Watering Plants", localTime, localDate, localDateTime, habitCycleManager);
        habitPullingWeeds = new HabitIncrement(1, 0, 1, "Pulling Weeds", localTime, localDate, localDateTime, habitCycleManager);
        habitFlashcards = new HabitIncrement(1, 0, 1, "Flashcards", localTime, localDate, localDateTime, habitCycleManager);
        habitGrindingQuestions = new HabitIncrement(1, 0, 1, "Grinding Questions", localTime, localDate, localDateTime, habitCycleManager);

        pageExercise = new Page("Exercise");
        pageGardening = new Page("Gardening");
        pageStudying = new Page("Studying");

        allGenericPagesPageEmpty = new AllGenericPagesPage();
        allGenericPagesPageGeneralWrite = new AllGenericPagesPage();

        allGenericPagesPageDataManagerEmpty = new AllGenericPagesPageDataManager(destinationEmpty, allGenericPagesPageEmpty);
        allGenericPagesPageDataManagerGeneralRead = new AllGenericPagesPageDataManager(destinationGeneralRead, allGenericPagesPageEmpty);
        allGenericPagesPageDataManagerGeneralWrite = new AllGenericPagesPageDataManager(destinationGeneralWrite, allGenericPagesPageGeneralWrite);

        allHabitsPage.addToAllHabitsPage(habitRockClimbing);
        allHabitsPage.addToAllHabitsPage(habitCardio);
        allHabitsPage.addToAllHabitsPage(habitWateringPlants);
        allHabitsPage.addToAllHabitsPage(habitPullingWeeds);
        allHabitsPage.addToAllHabitsPage(habitFlashcards);
        allHabitsPage.addToAllHabitsPage(habitGrindingQuestions);

        pageExercise.addHabit(habitRockClimbing);
        pageExercise.addHabit(habitCardio);
        pageGardening.setOrder(Order.MANUAL);
        pageGardening.addHabit(habitWateringPlants);
        pageGardening.addHabit(habitPullingWeeds);
        pageStudying.addHabit(habitFlashcards);
        pageStudying.addHabit(habitGrindingQuestions);
    }

    @Test
    public void testConstructor() {
        assertTrue(allGenericPagesPageDataManagerEmpty.getDestination().equals(destinationEmpty));
        assertTrue(allGenericPagesPageDataManagerEmpty.getAllGenericPagesPage().equals(allGenericPagesPageEmpty));
    }

    @Test
    public void testBrokenDependencies() {
        AllGenericPagesPageDataManager allGenericPagesPageDataManagerBroken = new AllGenericPagesPageDataManager("./data/fake.json", allGenericPagesPageEmpty);

        try {
            allGenericPagesPageDataManagerBroken.readFromFile();
            allGenericPagesPageDataManagerBroken.writeToFile();
            fail("Expected IOException");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReadFromFileNothing() {
        try {
            allGenericPagesPageDataManagerEmpty.readFromFile();
        } catch (IOException e) {
            fail("Unexpected IOException");
        }

        assertTrue(allGenericPagesPageEmpty.getPages().isEmpty());
    }

    @Test
    public void testReadFromFileGeneral() {
        whatShouldBeAllGenericPagesPage = new AllGenericPagesPage();
        whatShouldBeAllGenericPagesPage.addToPages(pageExercise);
        whatShouldBeAllGenericPagesPage.addToPages(pageGardening);

        try {
            allGenericPagesPageDataManagerGeneralRead.readFromFile();
        } catch (IOException e) {
            fail("Unexpected IOException");
        }

        assertTrue(allGenericPagesPageEmpty.equals(whatShouldBeAllGenericPagesPage));
    }

    @Test
    public void testWriteToFileNothing() {
        try {
            allGenericPagesPageDataManagerEmpty.writeToFile();
            allGenericPagesPageDataManagerEmpty.readFromFile();
        } catch (IOException e) {
            fail("Unexpected IOException");
        }

        assertTrue(allGenericPagesPageEmpty.getPages().isEmpty());
    }

    @Test
    public void testWriteToFileGeneral() {
        allGenericPagesPageGeneralWrite.addToPages(pageGardening);
        allGenericPagesPageGeneralWrite.addToPages(pageStudying);

        whatShouldBeAllGenericPagesPage = new AllGenericPagesPage();
        whatShouldBeAllGenericPagesPage.addToPages(pageExercise);
        whatShouldBeAllGenericPagesPage.addToPages(pageStudying);

        try {
            allGenericPagesPageDataManagerGeneralWrite.writeToFile();
            allGenericPagesPageDataManagerGeneralWrite.readFromFile();
        } catch (IOException e) {
            fail("Unexpected IOException");
        }

        assertTrue(allGenericPagesPageGeneralWrite.equals(whatShouldBeAllGenericPagesPage));
    }
}
