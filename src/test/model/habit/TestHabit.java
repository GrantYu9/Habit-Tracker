package model.habit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.habit.Habit.ProgressType;
import model.organization.Tag;
import model.organization.centralization.AllHabitsPage;
import model.organization.centralization.AllTagPagesPage;
import model.organization.specialpages.FavouritesPage;
import model.organization.specialpages.HomePage;

@ExcludeFromJacocoGeneratedReport
// We use HabitIncrement as a way to test Habit
public class TestHabit {
    // Fix naming consistency later !!!
    private AllHabitsPage testAllHabitsPage;
    private SpyHabitCycleManager spyHabitCycleManagerA;
    private SpyHabitCycleManager spyHabitCycleManagerB;
    private SpyHabitCycleManager spyHabitCycleManagerD;
    private SpyHabitCycleManager spyHabitCycleManagerE;
    private HomePage testHomePage;
    private FavouritesPage testFavouritesPage;
    private AllTagPagesPage testAllTagPages;

    private LocalTime cycleTimeA; // Midnight
    private LocalTime cycleTimeD; // 23:30

    private LocalDate dayA; // Today

    private LocalDateTime time; // Right now
    private LocalDateTime justNow; // Right now - 1 hr
    
    private Habit testHabitA; // Boolean
    private Habit testHabitB; // testHabitA copy
    private Habit testHabitD; // Headstart
    private Habit testHabitE; // Funny title and unit

    private HabitSnapshot testHabitSnapshotA; // For testHabitA

    private Tag tagA;
    private Tag tagB;
    private Tag tagC; // Actually mimics tagA
    private Tag tagHome; // Home tag
    private Tag tagFavourite; // Favourite tag

    private List<Habit> whatShouldBeHabit;
    private List<HabitSnapshot> whatShouldBeHabitSnapshot;
    private List<Tag> whatShouldBeTag;
    
    @BeforeEach
    void runBefore() {
        cycleTimeA = LocalTime.of(0, 0);
        cycleTimeD = LocalTime.of(23, 30);

        dayA = LocalDate.now();

        time = LocalDateTime.now();
        justNow = time.minusHours(1);

        testHabitSnapshotA = new HabitSnapshot(0, 1, 0, 0, 0, 1, ProgressType.UNDERDONE, dayA, null);

        tagA = new Tag("A");
        tagB = new Tag("B");
        tagC = new Tag("a");
        tagHome = new Tag("Home");
        tagFavourite = new Tag("Favourite");

        testAllHabitsPage = new AllHabitsPage();
        testHomePage = new HomePage();
        testFavouritesPage = new FavouritesPage();
        testAllTagPages = new AllTagPagesPage();
        spyHabitCycleManagerA = new SpyHabitCycleManager(testAllHabitsPage, justNow);
        spyHabitCycleManagerB = new SpyHabitCycleManager(testAllHabitsPage, justNow);
        spyHabitCycleManagerD = new SpyHabitCycleManager(testAllHabitsPage, justNow);
        spyHabitCycleManagerE = new SpyHabitCycleManager(testAllHabitsPage, justNow);

        testHabitA = new HabitIncrement(1, 0, 1, "testHabitA", cycleTimeA, dayA, 
            time, spyHabitCycleManagerA);
        testHabitB = new HabitIncrement(1, 0, 1, "testHabitA", cycleTimeA, dayA, 
            time, spyHabitCycleManagerB);
        testHabitD = new HabitIncrement(5, 1, 1, "testHabitD", cycleTimeD, dayA, 
            time, spyHabitCycleManagerD);
        testHabitE = new HabitIncrement(5, 0, 1, " silly Title ", cycleTimeA, dayA, 
            time, spyHabitCycleManagerE);

        whatShouldBeHabit = new ArrayList<>();
        whatShouldBeHabitSnapshot = new ArrayList<>();
        whatShouldBeTag = new ArrayList<>();
    }

    @Test
    void testConstructorA() {
        assertEquals(1, testHabitA.getGoal());
        assertEquals(0, testHabitA.getStartingAmount());
        assertEquals(1, testHabitA.getStepAmount());
        assertEquals("testHabitA", testHabitA.getTitle());
        assertEquals(cycleTimeA, testHabitA.getCycleTime());
        assertTrue(testHabitA.getCurrentDay().isEqual(dayA));

        assertTrue(testHabitA.getCurrentAmount() == testHabitA.getStartingAmount());
        assertEquals(0, testHabitA.getOverloadAmount());
        assertEquals(ProgressType.UNDERDONE, testHabitA.getProgressType());
        assertEquals("", testHabitA.getUnit());
        assertTrue(testHabitA.getHistory().isEmpty());
        assertTrue(testHabitA.getTags().isEmpty());

        assertTrue(testHabitA.getNextCycleTime().isEqual(LocalDateTime.of(dayA, cycleTimeA).plusDays(1)));

        assertEquals(1, spyHabitCycleManagerA.scheduleHabitCallCount);
    }

    @Test
    void testConstructorD() {
        assertEquals(5, testHabitD.getGoal());
        assertEquals(1, testHabitD.getStartingAmount());
        assertEquals(1, testHabitD.getStepAmount());
        assertEquals("testHabitD", testHabitD.getTitle());
        assertEquals(cycleTimeD, testHabitD.getCycleTime());
        assertTrue(testHabitD.getCurrentDay().isEqual(dayA));

        assertTrue(testHabitD.getCurrentAmount() == testHabitD.getStartingAmount());
        assertEquals(0, testHabitD.getOverloadAmount());
        assertEquals(ProgressType.UNDERDONE, testHabitD.getProgressType());
        assertEquals("", testHabitA.getUnit());
        assertTrue(testHabitD.getHistory().isEmpty());
        assertTrue(testHabitD.getTags().isEmpty());

        assertTrue(testHabitD.getNextCycleTime().isEqual(LocalDateTime.of(dayA, cycleTimeD)));

        testAllHabitsPage.addToAllHabitsPage(testHabitD);
        
        assertEquals(1, spyHabitCycleManagerD.scheduleHabitCallCount);
    }

    // Just to check title trimming behaviour
    @Test
    void testConstructorE() {
        assertTrue(testHabitE.getTitle().equals("silly Title"));
    }

    @Test
    void testAddTagAndSortTags() {
        whatShouldBeHabit.add(testHabitA);

        testHabitA.addTagAndSortTags(tagB, testHomePage, testFavouritesPage, testAllTagPages);
        whatShouldBeTag.add(tagB);
        assertEquals(whatShouldBeTag, testHabitA.getTags());

        testHabitA.addTagAndSortTags(tagA, testHomePage, testFavouritesPage, testAllTagPages);
        whatShouldBeTag.add(0, tagA);
        assertEquals(whatShouldBeTag, testHabitA.getTags());

        testHabitA.addTagAndSortTags(tagHome, testHomePage, testFavouritesPage, testAllTagPages);
        whatShouldBeTag.add(0, tagHome);
        assertEquals(whatShouldBeHabit, testHomePage.getHabits());

        testHabitA.addTagAndSortTags(tagFavourite, testHomePage, testFavouritesPage, testAllTagPages);
        whatShouldBeTag.add(0, tagFavourite);
        assertEquals(whatShouldBeHabit, testFavouritesPage.getHabits());

        testHabitA.addTagAndSortTags(tagC, testHomePage, testFavouritesPage, testAllTagPages);
        whatShouldBeTag.add(2, tagC);
        assertEquals(whatShouldBeTag, testHabitA.getTags());
    }

    @Test
    void testContainsTagType() {
        // !!!
    }

    @Test
    void testAddToHistory() {
        whatShouldBeHabitSnapshot.add(testHabitSnapshotA);
        testHabitA.addToHistory(testHabitSnapshotA);
        assertEquals(whatShouldBeHabitSnapshot, testHabitA.getHistory());
    }

    @Test
    void testSetTitle() {
        testHabitA.setTitle(" nuh uh Uh ");
        assertTrue(testHabitA.getTitle().equals("nuh uh Uh"));
    }

    @Test
    void testSetUnit() {
        testHabitA.setUnit(" sips, sips, and more sips ");
        assertTrue(testHabitA.getUnit().equals("sips, sips, and more sips"));
    }

    @Test
    void testEquals() {
        assertTrue(testHabitA.equals(testHabitB));
    }

    // Spy class to check if scheduleHabit is called
    public class SpyHabitCycleManager extends HabitCycleManager {
        public int scheduleHabitCallCount = 0;

        public SpyHabitCycleManager(AllHabitsPage allHabitsPage, LocalDateTime lastTime) {
            super(allHabitsPage, lastTime);
        }

        @Override
        public void scheduleHabit(Habit habit, Duration duration, LocalDateTime marker) {
            scheduleHabitCallCount++;
        }
    };
}
