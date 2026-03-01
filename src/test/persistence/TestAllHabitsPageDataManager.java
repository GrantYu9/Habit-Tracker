package persistence;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.habit.Habit;
import model.habit.HabitCycleManager;
import model.habit.HabitIncrement;
import model.habit.HabitSnapshot;
import model.habit.Habit.ProgressType;
import model.organization.Tag;
import model.organization.centralization.AllHabitsPage;
import model.organization.centralization.AllTagPagesPage;
import model.organization.specialpages.FavouritesPage;
import model.organization.specialpages.HomePage;

public class TestAllHabitsPageDataManager {
    private String destinationEmpty;
    private String destinationGeneralRead;
    private String destinationGeneralWrite;

    private LocalDate localDate;
    private LocalTime localTime;
    private LocalDateTime localDateTime;
    
    private Tag tagA;
    private Tag tagHome;
    private Tag tagFavourite;

    private HabitSnapshot habitSnapshotA;
    private HabitSnapshot habitSnapshotB;
    
    private Habit habitA;
    private Habit habitB;
    private Habit habitC;

    private HomePage homePage;
    private FavouritesPage favouritesPage;

    private AllTagPagesPage allTagPagesPage;

    private AllHabitsPage allHabitsPageEmpty;
    private AllHabitsPage allHabitsPageGeneralWrite;
    
    private HabitCycleManager habitCycleManager;

    private AllHabitsPageDataManager allHabitsPageDataManagerEmpty;
    private AllHabitsPageDataManager allHabitsPageDataManagerGeneralRead;
    private AllHabitsPageDataManager allHabitsPageDataManagerGeneralWrite;

    private AllHabitsPage whatShouldBeAllHabitsPage;

    @BeforeEach
    public void runBeforeEach() {
        destinationEmpty = "./data/testing/TestAllHabitsPageDataManagerEmpty.json";
        destinationGeneralRead = "./data/testing/TestAllHabitsPageDataManagerGeneralRead.json";
        destinationGeneralWrite = "./data/testing/TestAllHabitsPageDataManagerGeneralWrite.json";

        localDate = LocalDate.of(2026, 2, 27);
        localTime = LocalTime.MIN;
        localDateTime = LocalDateTime.of(localDate.plusDays(1), localTime);

        tagA = new Tag("Exercise");
        tagHome = new Tag("Home");
        tagFavourite = new Tag("Favourite");

        habitSnapshotA = new HabitSnapshot(3, 2, 1, 100, 1, 1, ProgressType.OVERLOADED, localDate.minusDays(1), "runs");
        habitSnapshotB = new HabitSnapshot(3, 2, 1, 100, 1, 1, ProgressType.OVERLOADED, localDate.minusDays(2), "runs");

        homePage = new HomePage();
        favouritesPage = new FavouritesPage();

        allTagPagesPage = new AllTagPagesPage();

        allHabitsPageEmpty = new AllHabitsPage();
        allHabitsPageGeneralWrite = new AllHabitsPage();

        habitCycleManager = new HabitCycleManager(allHabitsPageEmpty, localDateTime);

        habitA = new HabitIncrement(1, 0, 1, "Rock Climbing", localTime, localDate, localDateTime, habitCycleManager);
        habitB = new HabitIncrement(2, 1, 1, "Cardio", localTime, localDate, localDateTime, habitCycleManager);
        habitB.setUnit("runs");
        habitB.addTagAndSortTags(tagA, homePage, favouritesPage, allTagPagesPage);
        habitB.addTagAndSortTags(tagHome, homePage, favouritesPage, allTagPagesPage);
        habitB.addTagAndSortTags(tagFavourite, homePage, favouritesPage, allTagPagesPage);
        habitB.addToHistory(habitSnapshotA);
        habitB.addToHistory(habitSnapshotB);
        habitB.progressByStepAmount();
        habitC = new HabitIncrement(1, 0, 1, "Leetcode", localTime, localDate, localDateTime, habitCycleManager);

        allHabitsPageGeneralWrite.addToAllHabitsPage(habitB);
        allHabitsPageGeneralWrite.addToAllHabitsPage(habitC);

        allHabitsPageDataManagerEmpty = new AllHabitsPageDataManager(allHabitsPageEmpty, destinationEmpty);
        allHabitsPageDataManagerGeneralRead = new AllHabitsPageDataManager(allHabitsPageEmpty, destinationGeneralRead);
        allHabitsPageDataManagerGeneralWrite = new AllHabitsPageDataManager(allHabitsPageGeneralWrite, destinationGeneralWrite);

        // We clear TestAllHabitsPageDataManagerGeneralWrite.json every time we run this test suite
        try {
            Files.writeString(Path.of(destinationGeneralWrite), "");
        } catch (IOException e) {
            fail("Should not have thrown exception");
        }

        whatShouldBeAllHabitsPage = new AllHabitsPage();
    }

    @Test
    public void testConstructor() {
        assertTrue(allHabitsPageDataManagerEmpty.getDestination().equals(destinationEmpty));
        assertTrue(allHabitsPageDataManagerEmpty.getAllHabitsPage().equals(allHabitsPageEmpty));
    }

    @Test
    public void testBrokenDependencies() {
        String fakeDestination = "./data/fake.json";

        AllHabitsPageDataManager fakeAllHabitsPageDataManager = new AllHabitsPageDataManager(allHabitsPageEmpty, fakeDestination);

        try {
            fakeAllHabitsPageDataManager.readFromFile(homePage, favouritesPage, allTagPagesPage);

            fakeAllHabitsPageDataManager.writeToFile(allHabitsPageEmpty);

            fail("Did not throw IOException");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReadFromFileNothing() {
        try {
            allHabitsPageDataManagerEmpty.readFromFile(homePage, favouritesPage, allTagPagesPage);
        } catch (IOException e) {
            fail("Should not have thrown exception");
        }

        assertTrue(allHabitsPageEmpty.getHabits().isEmpty());
    }

    @Test
    public void testReadFromFileGeneral() {
        whatShouldBeAllHabitsPage.addToAllHabitsPage(habitA);
        whatShouldBeAllHabitsPage.addToAllHabitsPage(habitB);

        try {
            allHabitsPageDataManagerGeneralRead.readFromFile(homePage, favouritesPage, allTagPagesPage);
        } catch (IOException e) {
            fail("Should not have thrown exception");
        }

        assertTrue(Objects.equals(whatShouldBeAllHabitsPage, allHabitsPageEmpty));
    }

    @Test
    public void testWriteToFileNothing() {
        try {
            allHabitsPageDataManagerEmpty.writeToFile(allHabitsPageEmpty);
            allHabitsPageDataManagerEmpty.readFromFile(homePage, favouritesPage, allTagPagesPage);
        } catch (IOException e) {
            fail("Should not have thrown exception");
        }

        assertTrue(allHabitsPageEmpty.getHabits().isEmpty());
    }

    @Test
    public void testWriteToFileGeneral() {
        whatShouldBeAllHabitsPage.addToAllHabitsPage(habitB);
        whatShouldBeAllHabitsPage.addToAllHabitsPage(habitC);

        try {
            allHabitsPageDataManagerGeneralWrite.writeToFile(allHabitsPageGeneralWrite);
            allHabitsPageDataManagerGeneralWrite.readFromFile(homePage, favouritesPage, allTagPagesPage);
        } catch (IOException e) {
            fail("Should not have thrown exception");
        }

        assertTrue(Objects.equals(whatShouldBeAllHabitsPage, allHabitsPageGeneralWrite));
    }
}
