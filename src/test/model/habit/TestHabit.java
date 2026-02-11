package model.habit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.organization.Tag;
import model.organization.specialpages.AllHabitsPage;

// We use HabitIncrement as a way to test Habit
public class TestHabit {
    private enum ProgressType {
        UNDERDONE,
        DONE,
        OVERLOADED
    }

    private enum ViewMode {
        BAR,
        HEATMAP
    }

    private AllHabitsPage testAllHabitsPage;
    
    private Habit testHabitA; // Boolean
    private Habit testHabitB; // Step
    private Habit testHabitC; // Bigger steps
    private Habit testHabitD; // Headstart
    private Habit testHabitE; // Funny title and unit

    private Tag tagA;
    private Tag tagB;
    private Tag tagC; // Actually mimics tagA

    private List<Habit> whatShouldBe;
    
    @BeforeEach
    void runBefore() {
        testAllHabitsPage = new AllHabitsPage();

        testHabitA = new HabitIncrement(1, 0, 1, "testHabitA", null, testAllHabitsPage);
        testHabitB = new HabitIncrement(5, 0, 1, "testHabitB", "sips", testAllHabitsPage);
        testHabitC = new HabitIncrement(10, 0, 2, "testHabitC", "jumping jacks", testAllHabitsPage);
        testHabitD = new HabitIncrement(5, 1, 1, "testHabitD", "km", testAllHabitsPage);
        testHabitE = new HabitIncrement(5, 0, 1, " super silly Title ", " super silly Unit", testAllHabitsPage);

        tagA = new Tag("A");
        tagB = new Tag("B");
        tagC = new Tag("a");

        whatShouldBe = new ArrayList<>();
    }

    @Test
    void testConstructorA() {
        whatShouldBe.add(testHabitA);
        
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

        assertEquals(whatShouldBe, testAllHabitsPage.getHabits());
    }

    @Test
    void testConstructorD() {
        whatShouldBe.add(testHabitD);
        
        assertEquals(5, testHabitA.getGoal());
        assertEquals(1, testHabitA.getStartingAmount());
        assertEquals(1, testHabitA.getStepAmount());
        assertEquals("testHabitD", testHabitA.getTitle());
        assertTrue(testHabitA.getUnit().equals("km"));
        assertEquals(whatShouldBe, testAllHabitsPage.getHabits());

        assertTrue(testHabitD.getCurrentAmount() == testHabitD.getStartingAmount());
        assertEquals(0, testHabitA.getOverloadAmount());
        assertEquals(0, testHabitA.getProgressPercentage());
        assertEquals(ViewMode.BAR, testHabitA.getViewMode());
        assertEquals(ProgressType.UNDERDONE, testHabitA.getProgressType());
        assertTrue(testHabitA.getHistory().isEmpty());
        assertTrue(testHabitA.getTags().isEmpty());

        assertEquals(whatShouldBe, testAllHabitsPage.getHabits());
    }

    // Just to check title and unit trimming behaviour
    @Test
    void testConstructorE() {
        assertTrue(testHabitE.getTitle().equals("super silly Title"));
        assertTrue(testHabitE.getTitle().equals("super silly Unit"));
    }

    @Test
    void testCycleHabit() {
        // !!!
    }

    @Test
    void testAddTagAndSortTags() {
        // !!!
    }

    @Test
    void testSetTitle() {
        // !!!
    }

    @Test
    void testSetUnit() {
        // !!!
    }
}
