package model.habit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.habit.Habit.ProgressType;
import model.habit.Habit.ViewMode;
import model.organization.Tag;
import model.organization.specialpages.AllHabitsPage;

// We use HabitIncrement as a way to test Habit
public class TestHabit {
    private AllHabitsPage testAllHabitsPage;

    private LocalTime timeOne;
    
    private Habit testHabitA; // Boolean
    private Habit testHabitB; // Step
    private Habit testHabitC; // Bigger steps
    private Habit testHabitD; // Headstart
    private Habit testHabitE; // Funny title and unit

    private HabitSnapshot testHabitSnapshotA; // For testHabitA

    private Tag tagA;
    private Tag tagB;
    private Tag tagC; // Actually mimics tagA

    private List<Habit> whatShouldBeHabit;
    private List<HabitSnapshot> whatShouldBeHabitSnapshot;
    private List<Tag> whatShouldBeTag;
    
    @BeforeEach
    void runBefore() {
        testAllHabitsPage = new AllHabitsPage();

        timeOne = uhhhhh;

        testHabitA = new HabitIncrement(1, 0, 1, "testHabitA", null, testAllHabitsPage);
        testHabitB = new HabitIncrement(5, 0, 1, "testHabitB", "sips", testAllHabitsPage);
        testHabitC = new HabitIncrement(10, 0, 2, "testHabitC", "jumping jacks", testAllHabitsPage);
        testHabitD = new HabitIncrement(5, 1, 1, "testHabitD", "km", testAllHabitsPage);
        testHabitE = new HabitIncrement(5, 0, 1, " super silly Title ", " super silly Unit", testAllHabitsPage);

        testHabitSnapshotA = new HabitSnapshot(0, 1, 0, 0, 0, 1, ProgressType.UNDERDONE, null);

        tagA = new Tag("A");
        tagB = new Tag("B");
        tagC = new Tag("a");

        whatShouldBeHabit = new ArrayList<>();
    }

    @Test
    void testConstructorA() {
        whatShouldBeHabit.add(testHabitA);
        
        assertEquals(1, testHabitA.getGoal());
        assertEquals(0, testHabitA.getStartingAmount());
        assertEquals(1, testHabitA.getStepAmount());
        assertEquals("testHabitA", testHabitA.getTitle());
        assertNull(testHabitA.getUnit());

        assertTrue(testHabitA.getCurrentAmount() == testHabitA.getStartingAmount());
        assertEquals(0, testHabitA.getOverloadAmount());
        assertEquals(0, testHabitA.getProgressPercentage());
        assertEquals(ViewMode.BAR, testHabitA.getViewMode());
        assertEquals(ProgressType.UNDERDONE, testHabitA.getProgressType());
        assertTrue(testHabitA.getHistory().isEmpty());
        assertTrue(testHabitA.getTags().isEmpty());

        assertEquals(whatShouldBeHabit, testAllHabitsPage.getHabits());
    }

    @Test
    void testConstructorD() {
        whatShouldBeHabit.add(testHabitD);
        
        assertEquals(5, testHabitA.getGoal());
        assertEquals(1, testHabitA.getStartingAmount());
        assertEquals(1, testHabitA.getStepAmount());
        assertEquals("testHabitD", testHabitA.getTitle());
        assertTrue(testHabitA.getUnit().equals("km"));
        assertEquals(whatShouldBeHabit, testAllHabitsPage.getHabits());

        assertTrue(testHabitD.getCurrentAmount() == testHabitD.getStartingAmount());
        assertEquals(0, testHabitA.getOverloadAmount());
        assertEquals(0, testHabitA.getProgressPercentage());
        assertEquals(ViewMode.BAR, testHabitA.getViewMode());
        assertEquals(ProgressType.UNDERDONE, testHabitA.getProgressType());
        assertTrue(testHabitA.getHistory().isEmpty());
        assertTrue(testHabitA.getTags().isEmpty());

        assertEquals(whatShouldBeHabit, testAllHabitsPage.getHabits());
    }

    // Just to check title and unit trimming behaviour
    @Test
    void testConstructorE() {
        assertTrue(testHabitE.getTitle().equals("super silly Title"));
        assertTrue(testHabitE.getTitle().equals("super silly Unit"));
    }

    @Test
    void testCycleHabit() {
        // needs some extra stuff with time

        whatShouldBeHabitSnapshot.add(testHabitSnapshotA);

        assertEquals(whatShouldBeHabitSnapshot, testHabitA.getHistory());
        assertTrue(testHabitA.getStartingAmount() == testHabitA.getCurrentAmount());
        assertEquals(0, testHabitA.getOverloadAmount());
        assertEquals(0, testHabitA.getProgressPercentage());
        assertEquals(ProgressType.UNDERDONE, testHabitA.getProgressType());
    }

    @Test
    void testAddTagAndSortTags() {
        testHabitA.addTagAndSortTags(tagB);
        whatShouldBeTag.add(tagB);
        assertEquals(whatShouldBeTag, testHabitA.getTags());

        testHabitA.addTagAndSortTags(tagA);
        whatShouldBeTag.add(0, tagA);
        assertEquals(whatShouldBeTag, testHabitA.getTags());

        testHabitA.addTagAndSortTags(tagC);
        whatShouldBeTag.add(1, tagC);
        assertEquals(whatShouldBeTag, testHabitA.getTags());
    }

    @Test
    void testSetTitle() {
        testHabitA.setTitle(" nuh uh Uh ");
        assertTrue(testHabitA.getTitle().equals("nuh uh uh"));
    }

    @Test
    void testSetUnit() {
        testHabitA.setUnit(" sips, sips, and more sips ");
        assertTrue(testHabitA.getUnit().equals(" sips, sips, and more sips "));
    }
}
