package persistence;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import exceptions.HabitNotFoundException;
import logging.EventLog;
import model.habit.Habit;
import model.habit.HabitCycleManager;
import model.habit.HabitIncrement;
import model.organization.centralization.AllGenericPagesPage;
import model.organization.centralization.AllHabitsPage;
import model.organization.tree.Page;
import model.organization.tree.Page.Order;

@ExcludeFromJacocoGeneratedReport
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

        allHabitsPage.addToAllHabitsPage(habitRockClimbing, EventLog.getInstance());
        allHabitsPage.addToAllHabitsPage(habitCardio, EventLog.getInstance());
        allHabitsPage.addToAllHabitsPage(habitWateringPlants, EventLog.getInstance());
        allHabitsPage.addToAllHabitsPage(habitPullingWeeds, EventLog.getInstance());
        allHabitsPage.addToAllHabitsPage(habitFlashcards, EventLog.getInstance());
        allHabitsPage.addToAllHabitsPage(habitGrindingQuestions, EventLog.getInstance());

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
            allGenericPagesPageDataManagerBroken.readFromFile(allHabitsPage);
            allGenericPagesPageDataManagerBroken.writeToFile();
            fail("Expected IOException");
        } catch (IOException e) {
            // pass
        } catch (HabitNotFoundException e) {
            fail("Unexpected HabitNotFoundException");
        }
    }

    @Test
    public void testReadFromFileNothing() {
        try {
            allGenericPagesPageDataManagerEmpty.readFromFile(allHabitsPage);
        } catch (IOException e) {
            fail("Unexpected IOException");
        } catch (HabitNotFoundException e) {
            fail("Unexpected HabitNotFoundException");
        }

        assertTrue(allGenericPagesPageEmpty.getPages().isEmpty());
    }

    @Test
    public void testReadFromFileHabitNotFound() {
        allHabitsPage.getHabits().clear();

        try {
            allGenericPagesPageDataManagerGeneralRead.readFromFile(allHabitsPage);
            fail("Expected HabitNotFoundException");
        } catch (IOException e) {
            fail("Unexpected IOException");
        } catch (HabitNotFoundException e) {
            // pass
        }
    }

    @Test
    public void testReadFromFileGeneral() {
        whatShouldBeAllGenericPagesPage = new AllGenericPagesPage();
        whatShouldBeAllGenericPagesPage.addToPages(pageExercise, EventLog.getInstance());
        whatShouldBeAllGenericPagesPage.addToPages(pageGardening, EventLog.getInstance());

        try {
            allGenericPagesPageDataManagerGeneralRead.readFromFile(allHabitsPage);
        } catch (IOException e) {
            fail("Unexpected IOException");
        } catch (HabitNotFoundException e) {
            fail("Unexpected HabitNotFoundException");
        }

        assertTrue(allGenericPagesPageEmpty.equals(whatShouldBeAllGenericPagesPage));
    }

    @Test
    public void testWriteToFileNothing() {
        try {
            allGenericPagesPageDataManagerEmpty.writeToFile();
            allGenericPagesPageDataManagerEmpty.readFromFile(allHabitsPage);
        } catch (IOException e) {
            fail("Unexpected IOException");
        } catch (HabitNotFoundException e) {
            fail("Unexpected HabitNotFoundException");
        }

        assertTrue(allGenericPagesPageEmpty.getPages().isEmpty());
    }

    @Test
    public void testWriteToFileGeneral() {
        allGenericPagesPageGeneralWrite.addToPages(pageGardening, EventLog.getInstance());
        allGenericPagesPageGeneralWrite.addToPages(pageStudying, EventLog.getInstance());

        whatShouldBeAllGenericPagesPage = new AllGenericPagesPage();
        whatShouldBeAllGenericPagesPage.addToPages(pageGardening, EventLog.getInstance());
        whatShouldBeAllGenericPagesPage.addToPages(pageStudying, EventLog.getInstance());

        try {
            allGenericPagesPageDataManagerGeneralWrite.writeToFile();
            allGenericPagesPageDataManagerGeneralWrite.readFromFile(allHabitsPage);
        } catch (IOException e) {
            fail("Unexpected IOException");
        } catch (HabitNotFoundException e) {
            fail("Unexpected HabitNotFoundException");
        }

        assertTrue(allGenericPagesPageGeneralWrite.equals(whatShouldBeAllGenericPagesPage));
    }
}
