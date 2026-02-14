package model.habit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.habit.Habit.ProgressType;
import model.organization.centralization.AllHabitsPage;

public class TestHabitIncrement {
    private AllHabitsPage testAllHabitsPage;
    private HabitCycleManager testHabitCycleManager;

    private LocalDate localDate; // Fr Feb 13, 2026
    private LocalTime localTime; // 23:30
    private LocalDateTime localDateTime; // localDate + localTime

    private Habit testHabitIncrementA; // Boolean
    private Habit testHabitIncrementB; // Step 1
    private Habit testHabitIncrementC; // Step 2
    private Habit testHabitIncrementD; // Head start
    private Habit testHabitIncrementE; // Negatives to positives

    Habit expected;
    
    @BeforeEach
    void runBefore() {
        testAllHabitsPage = new AllHabitsPage();
        testHabitCycleManager = new HabitCycleManager(testAllHabitsPage, localDateTime);

        localDate = LocalDate.of(2026, 2, 13);
        localTime = LocalTime.of(23, 30);
        localDateTime = LocalDateTime.of(localDate, localTime);

        testHabitIncrementA = new HabitIncrement(1, 0, 1, "Workout", null, localTime, localDate, testAllHabitsPage, 
            testHabitCycleManager);
        testHabitIncrementB = new HabitIncrement(5, 0, 1, "Study", "study sessions", localTime, localDate, 
            testAllHabitsPage, testHabitCycleManager);
        testHabitIncrementC = new HabitIncrement(10, 0, 2, "Hydration", "sips", localTime, localDate, 
            testAllHabitsPage, testHabitCycleManager);
        testHabitIncrementD = new HabitIncrement(5, 1, 1, "Back stength", "pullups", localTime, localDate, 
            testAllHabitsPage, testHabitCycleManager);
        testHabitIncrementE = new HabitIncrement(3, -3, 1, "Bulking", "Net 100s of calories", localTime, localDate, 
            testAllHabitsPage, testHabitCycleManager);
    }

    @Test
    void testConstructorA() {
        assertEquals(0, testHabitIncrementA.getProgressPercentage());
    }

    @Test
    void testConstructorD() {
        assertEquals(0, testHabitIncrementD.getProgressPercentage());
    }

    @Test
    void testConstructorE() {
        assertEquals(0, testHabitIncrementE.getProgressPercentage());
    }

    @Test
    void testProgressByStepAmountUnderB() {
        expected = copyHabitIncrement(testHabitIncrementB);

        assertEquals(0, testHabitIncrementB.getCurrentAmount());

        testHabitIncrementB.progressByStepAmount();
        expected.setCurrentAmount(1);
        expected.setProgressPercentage(20);
        assertTrue(testHabitIncrementB.equals(expected));

        testHabitIncrementB.progressByStepAmount();
        expected.setCurrentAmount(2);
        expected.setProgressPercentage(40);
        assertTrue(testHabitIncrementB.equals(expected));
    }

    @Test
    void testProgressByStepAmountUnderC() {
        expected = copyHabitIncrement(testHabitIncrementC);

        assertEquals(0, testHabitIncrementC.getCurrentAmount());

        testHabitIncrementC.progressByStepAmount();
        expected.setCurrentAmount(2);
        expected.setProgressPercentage(20);
        assertTrue(testHabitIncrementC.equals(expected));

        testHabitIncrementC.progressByStepAmount();
        expected.setCurrentAmount(4);
        expected.setProgressPercentage(40);
        assertTrue(testHabitIncrementC.equals(expected));
    }

    @Test
    void testProgressByStepAmountUnderD() {
        expected = copyHabitIncrement(testHabitIncrementD);

        assertEquals(1, testHabitIncrementD.getCurrentAmount());

        testHabitIncrementD.progressByStepAmount();
        expected.setCurrentAmount(2);
        expected.setProgressPercentage(25);
        assertTrue(testHabitIncrementD.equals(expected));

        testHabitIncrementD.progressByStepAmount();
        expected.setCurrentAmount(3);
        expected.setProgressPercentage(50);
        assertTrue(testHabitIncrementD.equals(expected));
    }

    @Test
    void testProgressByStepAmountAtA() {
        expected = copyHabitIncrement(testHabitIncrementA);

        assertEquals(0, testHabitIncrementA.getCurrentAmount());

        testHabitIncrementA.progressByStepAmount();
        expected.setCurrentAmount(1);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.DONE);
        assertTrue(testHabitIncrementA.equals(expected));
    }

    @Test
    void testProgressByStepAmountAtB() {
        expected = copyHabitIncrement(testHabitIncrementB);

        testHabitIncrementB.progressByStepAmount(); // 1
        testHabitIncrementB.progressByStepAmount(); // 2
        testHabitIncrementB.progressByStepAmount(); // 3
        testHabitIncrementB.progressByStepAmount(); // 4

        expected.setCurrentAmount(4);
        expected.setProgressPercentage(80);
        assertTrue(testHabitIncrementB.equals(expected));

        testHabitIncrementB.progressByStepAmount();
        expected.setCurrentAmount(5);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.DONE);
        assertTrue(testHabitIncrementB.equals(expected));
    }

    @Test
    void testProgressByStepAmountOverA() {
        expected = copyHabitIncrement(testHabitIncrementA);

        testHabitIncrementA.progressByStepAmount();
        testHabitIncrementA.progressByStepAmount();

        expected.setCurrentAmount(2);
        expected.setOverloadAmount(1);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.OVERLOADED);
        assertTrue(testHabitIncrementA.equals(expected));
    }

    @Test
    void testProgressByStepAmountOverB() {
        Habit expected = copyHabitIncrement(testHabitIncrementB);

        testHabitIncrementB.progressByStepAmount(); // 1
        testHabitIncrementB.progressByStepAmount(); // 2
        testHabitIncrementB.progressByStepAmount(); // 3
        testHabitIncrementB.progressByStepAmount(); // 4
        testHabitIncrementB.progressByStepAmount(); // 5
        testHabitIncrementB.progressByStepAmount(); // 5 + 1

        expected.setCurrentAmount(6);
        expected.setOverloadAmount(1);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.OVERLOADED);
        assertTrue(testHabitIncrementB.equals(expected));
    }
    
    @Test
    void testProgressByStepAmountOverD() {
        expected = copyHabitIncrement(testHabitIncrementD);

        testHabitIncrementD.progressByStepAmount(); // 2
        testHabitIncrementD.progressByStepAmount(); // 3
        testHabitIncrementD.progressByStepAmount(); // 4
        testHabitIncrementD.progressByStepAmount(); // 5
        testHabitIncrementD.progressByStepAmount(); // 5 + 1

        expected.setCurrentAmount(6);
        expected.setOverloadAmount(1);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.OVERLOADED);
        assertTrue(testHabitIncrementD.equals(expected));
    }

    @Test
    void testProgressByStepAmountE() {
        expected = copyHabitIncrement(testHabitIncrementE);

        assertEquals(-3, testHabitIncrementE.getCurrentAmount());

        testHabitIncrementE.progressByStepAmount();
        expected.setCurrentAmount(-2);
        expected.setProgressPercentage(17);
        assertTrue(testHabitIncrementE.equals(expected));

        testHabitIncrementE.progressByStepAmount();
        expected.setCurrentAmount(-1);
        expected.setProgressPercentage(34);
        assertTrue(testHabitIncrementE.equals(expected));

        testHabitIncrementE.progressByStepAmount();
        expected.setCurrentAmount(0);
        expected.setProgressPercentage(50);
        assertTrue(testHabitIncrementE.equals(expected));

        testHabitIncrementE.progressByStepAmount();
        expected.setCurrentAmount(1);
        expected.setProgressPercentage(67);
        assertTrue(testHabitIncrementE.equals(expected));

        testHabitIncrementE.progressByStepAmount();
        expected.setCurrentAmount(2);
        expected.setProgressPercentage(83);
        assertTrue(testHabitIncrementE.equals(expected));

        testHabitIncrementE.progressByStepAmount();
        expected.setCurrentAmount(3);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.DONE);
        assertTrue(testHabitIncrementE.equals(expected));

        testHabitIncrementE.progressByStepAmount();
        expected.setCurrentAmount(4);
        expected.setOverloadAmount(1);
        expected.setProgressType(ProgressType.OVERLOADED);
        assertTrue(testHabitIncrementE.equals(expected));

        testHabitIncrementE.progressByStepAmount();
        expected.setCurrentAmount(5);
        expected.setOverloadAmount(2);
        assertTrue(testHabitIncrementE.equals(expected));
    }

    @Test
    void testCalculateOverloadAmountA() {
        assertEquals(1, testHabitIncrementA.calculateOverloadAmount(2, 1));
        assertEquals(2, testHabitIncrementA.calculateOverloadAmount(3, 1));
    }

    @Test
    void testCalculateProgressPercentageNoneA() {
        assertEquals(0, testHabitIncrementA.calculateProgressPercentage(0, 0, 1));
    }

    @Test
    void testCalculateProgressPercentageUnderB() {
        assertEquals(20, testHabitIncrementB.calculateProgressPercentage(0, 1, 5));
        assertEquals(40, testHabitIncrementB.calculateProgressPercentage(0, 2, 5));
    }

    @Test
    void testCalculateProgressPercentageUnderD() {
        assertEquals(25, testHabitIncrementD.calculateProgressPercentage(1, 2, 5));
        assertEquals(50, testHabitIncrementD.calculateProgressPercentage(1, 3, 5));
    }

    @Test
    void testCalculateProgressPercentageAtA() {
        assertEquals(100, testHabitIncrementA.calculateProgressPercentage(0, 1, 1));
    }

    @Test
    void testCalculateProgressPercentageAtD() {
        assertEquals(100, testHabitIncrementD.calculateProgressPercentage(1, 2, 5));
    }

    @Test
    void testCalculateProgressPercentageE() {
        assertEquals(0, testHabitIncrementE.calculateProgressPercentage(-3, -3, 3));
        assertEquals(17, testHabitIncrementE.calculateProgressPercentage(-3, -2, 3));
        assertEquals(34, testHabitIncrementE.calculateProgressPercentage(-3, -1, 3));
        assertEquals(50, testHabitIncrementE.calculateProgressPercentage(-3, 0, 3));
        assertEquals(67, testHabitIncrementE.calculateProgressPercentage(-3, 1, 3));
        assertEquals(100, testHabitIncrementE.calculateProgressPercentage(-3, 3, 3));
    }

    @Test
    void testSetCurrentAmountIncreasingB() {
        expected = copyHabitIncrement(testHabitIncrementB);
        
        testHabitIncrementB.setCurrentAmount(4);
        expected.setCurrentAmount(4);
        expected.setProgressPercentage(80);
        assertTrue(testHabitIncrementB.equals(expected));

        testHabitIncrementB.setCurrentAmount(5);
        expected.setCurrentAmount(5);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.DONE);
        assertTrue(testHabitIncrementB.equals(expected));

        testHabitIncrementB.setCurrentAmount(6);
        expected.setCurrentAmount(6);
        expected.setOverloadAmount(1);
        expected.setProgressType(ProgressType.OVERLOADED);
        assertTrue(testHabitIncrementB.equals(expected));
    }

    @Test
    void testSetCurrentAmountIncreasingD() {
        expected = copyHabitIncrement(testHabitIncrementD);
        
        testHabitIncrementD.setCurrentAmount(4);
        expected.setCurrentAmount(4);
        expected.setProgressPercentage(75);
        assertTrue(testHabitIncrementD.equals(expected));
        
        testHabitIncrementD.setCurrentAmount(5);
        expected.setCurrentAmount(5);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.DONE);
        assertTrue(testHabitIncrementD.equals(expected));

        testHabitIncrementD.setCurrentAmount(6);
        expected.setCurrentAmount(6);
        expected.setOverloadAmount(1);
        expected.setProgressType(ProgressType.OVERLOADED);
        assertTrue(testHabitIncrementD.equals(expected));
    }

    @Test
    void testSetCurrentAmountDecreasingB() {
        expected = copyHabitIncrement(testHabitIncrementB);

        testHabitIncrementB.setCurrentAmount(6);
        expected.setCurrentAmount(6);
        expected.setOverloadAmount(1);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.OVERLOADED);
        assertTrue(testHabitIncrementB.equals(expected));

        testHabitIncrementB.setCurrentAmount(5);
        expected.setCurrentAmount(5);
        expected.setOverloadAmount(0);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.DONE);
        assertTrue(testHabitIncrementB.equals(expected));

        testHabitIncrementB.setCurrentAmount(4);
        expected.setCurrentAmount(4);
        expected.setProgressPercentage(80);
        expected.setProgressType(ProgressType.UNDERDONE);
        assertTrue(testHabitIncrementB.equals(expected));
    }

    @Test
    void testSetCurrentAmountDecreasingD() {
        expected = copyHabitIncrement(testHabitIncrementD);

        testHabitIncrementD.setCurrentAmount(6);
        expected.setCurrentAmount(6);
        expected.setOverloadAmount(1);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.OVERLOADED);
        assertTrue(testHabitIncrementD.equals(expected));

        testHabitIncrementD.setCurrentAmount(5);
        expected.setCurrentAmount(5);
        expected.setOverloadAmount(0);
        expected.setProgressType(ProgressType.DONE);
        assertTrue(testHabitIncrementD.equals(expected));

        testHabitIncrementD.setCurrentAmount(4);
        testHabitIncrementD.setCurrentAmount(4);
        expected.setCurrentAmount(4);
        expected.setProgressPercentage(80);
        expected.setProgressType(ProgressType.UNDERDONE);
        assertTrue(testHabitIncrementD.equals(expected));
    }

    @Test
    void testSetCurrentAmountIncreasingE() {
        expected = copyHabitIncrement(testHabitIncrementE);

        assertEquals(-3, testHabitIncrementE.getCurrentAmount());

        testHabitIncrementE.setCurrentAmount(-2);
        expected.setCurrentAmount(-2);
        expected.setProgressPercentage(17);
        assertTrue(testHabitIncrementE.equals(expected));

        testHabitIncrementE.setCurrentAmount(0);
        expected.setCurrentAmount(0);
        expected.setProgressPercentage(50);
        assertTrue(testHabitIncrementE.equals(expected));

        testHabitIncrementE.setCurrentAmount(2);
        expected.setCurrentAmount(2);
        expected.setProgressPercentage(83);
        assertTrue(testHabitIncrementE.equals(expected));

        testHabitIncrementE.setCurrentAmount(3);
        expected.setCurrentAmount(3);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.DONE);
        assertTrue(testHabitIncrementE.equals(expected));

        testHabitIncrementE.setCurrentAmount(4);
        expected.setCurrentAmount(4);
        expected.setOverloadAmount(1);
        expected.setProgressType(ProgressType.OVERLOADED);
        assertTrue(testHabitIncrementE.equals(expected));

        testHabitIncrementE.setCurrentAmount(5);
        expected.setCurrentAmount(5);
        expected.setOverloadAmount(2);
        assertTrue(testHabitIncrementE.equals(expected));
    }

    @Test
    void testSetCurrentAmountDecreasingE() {
        expected = copyHabitIncrement(testHabitIncrementE);

        testHabitIncrementE.setCurrentAmount(4);
        expected.setCurrentAmount(4);
        expected.setOverloadAmount(1);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.OVERLOADED);
        assertTrue(testHabitIncrementE.equals(expected));

        testHabitIncrementE.setCurrentAmount(3);
        expected.setCurrentAmount(3);
        expected.setOverloadAmount(0);
        expected.setProgressType(ProgressType.DONE);
        assertTrue(testHabitIncrementE.equals(expected));

        testHabitIncrementE.setCurrentAmount(2);
        expected.setCurrentAmount(2);
        expected.setProgressPercentage(83);
        expected.setProgressType(ProgressType.UNDERDONE);
        assertTrue(testHabitIncrementE.equals(expected));

        testHabitIncrementE.setCurrentAmount(0);
        expected.setCurrentAmount(0);
        expected.setProgressPercentage(50);
        assertTrue(testHabitIncrementE.equals(expected));

        testHabitIncrementE.setCurrentAmount(-2);
        expected.setCurrentAmount(-2);
        expected.setProgressPercentage(17);
        assertTrue(testHabitIncrementE.equals(expected));

        testHabitIncrementE.setCurrentAmount(-3);
        expected.setCurrentAmount(-3);
        expected.setProgressPercentage(0);
        assertTrue(testHabitIncrementE.equals(expected));
    }

    // EFFECTS: Creates a copy of habitIncrement
    public Habit copyHabitIncrement (Habit habitIncrement) {
        return new HabitIncrement(habitIncrement.getGoal(), habitIncrement.getStartingAmount(), 
            habitIncrement.getStepAmount(), habitIncrement.getTitle(), habitIncrement.getUnit(), 
            habitIncrement.getCycleTime(), habitIncrement.getCurrentDay(), testAllHabitsPage, testHabitCycleManager);
    }
}
