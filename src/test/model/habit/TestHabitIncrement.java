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

    private LocalDate localDate; // Today
    private LocalTime localTime; // Now
    private LocalDateTime localDateTime; // Right now

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

        localDate = LocalDate.now();
        localTime = LocalTime.now();
        localDateTime = LocalDateTime.of(localDate, localTime);

        testHabitIncrementA = new HabitIncrement(1, 0, 1, "Workout", localTime, localDate, 
            testHabitCycleManager);
        testHabitIncrementB = new HabitIncrement(5, 0, 1, "Study", localTime, localDate, 
            testHabitCycleManager);
        testHabitIncrementC = new HabitIncrement(10, 0, 2, "Hydration", localTime, localDate, 
            testHabitCycleManager);
        testHabitIncrementD = new HabitIncrement(5, 1, 1, "Back stength", localTime, localDate, 
            testHabitCycleManager);
        testHabitIncrementE = new HabitIncrement(3, -3, 1, "Bulking", localTime, localDate, 
            testHabitCycleManager);
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
        expected.setCurrentAmountLogic(1);
        expected.setProgressPercentage(20);
        assertTrue(testHabitIncrementB.equals(expected));

        testHabitIncrementB.progressByStepAmount();
        expected.setCurrentAmountLogic(2);
        expected.setProgressPercentage(40);
        assertTrue(testHabitIncrementB.equals(expected));
    }

    @Test
    void testProgressByStepAmountUnderC() {
        expected = copyHabitIncrement(testHabitIncrementC);

        assertEquals(0, testHabitIncrementC.getCurrentAmount());

        testHabitIncrementC.progressByStepAmount();
        expected.setCurrentAmountLogic(2);
        expected.setProgressPercentage(20);
        assertTrue(testHabitIncrementC.equals(expected));

        testHabitIncrementC.progressByStepAmount();
        expected.setCurrentAmountLogic(4);
        expected.setProgressPercentage(40);
        assertTrue(testHabitIncrementC.equals(expected));
    }

    @Test
    void testProgressByStepAmountUnderD() {
        expected = copyHabitIncrement(testHabitIncrementD);

        assertEquals(1, testHabitIncrementD.getCurrentAmount());

        testHabitIncrementD.progressByStepAmount();
        expected.setCurrentAmountLogic(2);
        expected.setProgressPercentage(25);
        assertTrue(testHabitIncrementD.equals(expected));

        testHabitIncrementD.progressByStepAmount();
        expected.setCurrentAmountLogic(3);
        expected.setProgressPercentage(50);
        assertTrue(testHabitIncrementD.equals(expected));
    }

    @Test
    void testProgressByStepAmountAtA() {
        expected = copyHabitIncrement(testHabitIncrementA);

        assertEquals(0, testHabitIncrementA.getCurrentAmount());

        testHabitIncrementA.progressByStepAmount();
        expected.setCurrentAmountLogic(1);
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

        expected.setCurrentAmountLogic(4);
        expected.setProgressPercentage(80);
        assertTrue(testHabitIncrementB.equals(expected));

        testHabitIncrementB.progressByStepAmount();
        expected.setCurrentAmountLogic(5);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.DONE);
        assertTrue(testHabitIncrementB.equals(expected));
    }

    @Test
    void testProgressByStepAmountOverA() {
        expected = copyHabitIncrement(testHabitIncrementA);

        testHabitIncrementA.progressByStepAmount();
        testHabitIncrementA.progressByStepAmount();

        expected.setCurrentAmountLogic(2);
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

        expected.setCurrentAmountLogic(6);
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

        expected.setCurrentAmountLogic(6);
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
        expected.setCurrentAmountLogic(-2);
        expected.setProgressPercentage(16);
        assertTrue(testHabitIncrementE.equals(expected));

        testHabitIncrementE.progressByStepAmount();
        expected.setCurrentAmountLogic(-1);
        expected.setProgressPercentage(33);
        assertTrue(testHabitIncrementE.equals(expected));

        testHabitIncrementE.progressByStepAmount();
        expected.setCurrentAmountLogic(0);
        expected.setProgressPercentage(50);
        assertTrue(testHabitIncrementE.equals(expected));

        testHabitIncrementE.progressByStepAmount();
        expected.setCurrentAmountLogic(1);
        expected.setProgressPercentage(66);
        assertTrue(testHabitIncrementE.equals(expected));

        testHabitIncrementE.progressByStepAmount();
        expected.setCurrentAmountLogic(2);
        expected.setProgressPercentage(83);
        assertTrue(testHabitIncrementE.equals(expected));

        testHabitIncrementE.progressByStepAmount();
        expected.setCurrentAmountLogic(3);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.DONE);
        assertTrue(testHabitIncrementE.equals(expected));

        testHabitIncrementE.progressByStepAmount();
        expected.setCurrentAmountLogic(4);
        expected.setOverloadAmount(1);
        expected.setProgressType(ProgressType.OVERLOADED);
        assertTrue(testHabitIncrementE.equals(expected));

        testHabitIncrementE.progressByStepAmount();
        expected.setCurrentAmountLogic(5);
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
        assertEquals(25, testHabitIncrementD.calculateProgressPercentage(1, 2, 5));
    }

    @Test
    void testCalculateProgressPercentageE() {
        assertEquals(0, testHabitIncrementE.calculateProgressPercentage(-3, -3, 3));
        assertEquals(16, testHabitIncrementE.calculateProgressPercentage(-3, -2, 3));
        assertEquals(33, testHabitIncrementE.calculateProgressPercentage(-3, -1, 3));
        assertEquals(50, testHabitIncrementE.calculateProgressPercentage(-3, 0, 3));
        assertEquals(66, testHabitIncrementE.calculateProgressPercentage(-3, 1, 3));
        assertEquals(100, testHabitIncrementE.calculateProgressPercentage(-3, 3, 3));
    }

    @Test
    void testSetCurrentAmountIncreasingB() {
        expected = copyHabitIncrement(testHabitIncrementB);
        
        testHabitIncrementB.setCurrentAmountLogic(4);
        expected.setCurrentAmountLogic(4);
        expected.setProgressPercentage(80);
        assertTrue(testHabitIncrementB.equals(expected));

        testHabitIncrementB.setCurrentAmountLogic(5);
        expected.setCurrentAmountLogic(5);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.DONE);
        assertTrue(testHabitIncrementB.equals(expected));

        testHabitIncrementB.setCurrentAmountLogic(6);
        expected.setCurrentAmountLogic(6);
        expected.setOverloadAmount(1);
        expected.setProgressType(ProgressType.OVERLOADED);
        assertTrue(testHabitIncrementB.equals(expected));
    }

    @Test
    void testSetCurrentAmountIncreasingD() {
        expected = copyHabitIncrement(testHabitIncrementD);
        
        testHabitIncrementD.setCurrentAmountLogic(4);
        expected.setCurrentAmountLogic(4);
        expected.setProgressPercentage(75);
        assertTrue(testHabitIncrementD.equals(expected));
        
        testHabitIncrementD.setCurrentAmountLogic(5);
        expected.setCurrentAmountLogic(5);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.DONE);
        assertTrue(testHabitIncrementD.equals(expected));

        testHabitIncrementD.setCurrentAmountLogic(6);
        expected.setCurrentAmountLogic(6);
        expected.setOverloadAmount(1);
        expected.setProgressType(ProgressType.OVERLOADED);
        assertTrue(testHabitIncrementD.equals(expected));
    }

    @Test
    void testSetCurrentAmountDecreasingB() {
        expected = copyHabitIncrement(testHabitIncrementB);

        testHabitIncrementB.setCurrentAmountLogic(6);
        expected.setCurrentAmountLogic(6);
        expected.setOverloadAmount(1);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.OVERLOADED);
        assertTrue(testHabitIncrementB.equals(expected));

        testHabitIncrementB.setCurrentAmountLogic(5);
        expected.setCurrentAmountLogic(5);
        expected.setOverloadAmount(0);
        expected.setProgressType(ProgressType.DONE);
        assertTrue(testHabitIncrementB.equals(expected));

        testHabitIncrementB.setCurrentAmountLogic(4);
        expected.setCurrentAmountLogic(4);
        expected.setProgressPercentage(80);
        expected.setProgressType(ProgressType.UNDERDONE);
        assertTrue(testHabitIncrementB.equals(expected));
    }

    @Test
    void testSetCurrentAmountDecreasingD() {
        expected = copyHabitIncrement(testHabitIncrementD);

        testHabitIncrementD.setCurrentAmountLogic(6);
        expected.setCurrentAmountLogic(6);
        expected.setOverloadAmount(1);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.OVERLOADED);
        assertTrue(testHabitIncrementD.equals(expected));

        testHabitIncrementD.setCurrentAmountLogic(5);
        expected.setCurrentAmountLogic(5);
        expected.setOverloadAmount(0);
        expected.setProgressType(ProgressType.DONE);
        assertTrue(testHabitIncrementD.equals(expected));

        testHabitIncrementD.setCurrentAmountLogic(4);
        expected.setCurrentAmountLogic(4);
        expected.setProgressPercentage(75);
        expected.setProgressType(ProgressType.UNDERDONE);
        assertTrue(testHabitIncrementD.equals(expected));
    }

    @Test
    void testSetCurrentAmountIncreasingE() {
        expected = copyHabitIncrement(testHabitIncrementE);

        assertEquals(-3, testHabitIncrementE.getCurrentAmount());

        testHabitIncrementE.setCurrentAmountLogic(-2);
        expected.setCurrentAmountLogic(-2);
        expected.setProgressPercentage(16);
        assertTrue(testHabitIncrementE.equals(expected));

        testHabitIncrementE.setCurrentAmountLogic(0);
        expected.setCurrentAmountLogic(0);
        expected.setProgressPercentage(50);
        assertTrue(testHabitIncrementE.equals(expected));

        testHabitIncrementE.setCurrentAmountLogic(2);
        expected.setCurrentAmountLogic(2);
        expected.setProgressPercentage(83);
        assertTrue(testHabitIncrementE.equals(expected));

        testHabitIncrementE.setCurrentAmountLogic(3);
        expected.setCurrentAmountLogic(3);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.DONE);
        assertTrue(testHabitIncrementE.equals(expected));

        testHabitIncrementE.setCurrentAmountLogic(4);
        expected.setCurrentAmountLogic(4);
        expected.setOverloadAmount(1);
        expected.setProgressType(ProgressType.OVERLOADED);
        assertTrue(testHabitIncrementE.equals(expected));

        testHabitIncrementE.setCurrentAmountLogic(5);
        expected.setCurrentAmountLogic(5);
        expected.setOverloadAmount(2);
        assertTrue(testHabitIncrementE.equals(expected));
    }

    @Test
    void testSetCurrentAmountDecreasingE() {
        expected = copyHabitIncrement(testHabitIncrementE);

        testHabitIncrementE.setCurrentAmountLogic(4);
        expected.setCurrentAmountLogic(4);
        expected.setOverloadAmount(1);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.OVERLOADED);
        assertTrue(testHabitIncrementE.equals(expected));

        testHabitIncrementE.setCurrentAmountLogic(3);
        expected.setCurrentAmountLogic(3);
        expected.setOverloadAmount(0);
        expected.setProgressType(ProgressType.DONE);
        assertTrue(testHabitIncrementE.equals(expected));

        testHabitIncrementE.setCurrentAmountLogic(2);
        expected.setCurrentAmountLogic(2);
        expected.setProgressPercentage(83);
        expected.setProgressType(ProgressType.UNDERDONE);
        assertTrue(testHabitIncrementE.equals(expected));

        testHabitIncrementE.setCurrentAmountLogic(0);
        expected.setCurrentAmountLogic(0);
        expected.setProgressPercentage(50);
        assertTrue(testHabitIncrementE.equals(expected));

        testHabitIncrementE.setCurrentAmountLogic(-2);
        expected.setCurrentAmountLogic(-2);
        expected.setProgressPercentage(16);
        assertTrue(testHabitIncrementE.equals(expected));

        testHabitIncrementE.setCurrentAmountLogic(-3);
        expected.setCurrentAmountLogic(-3);
        expected.setProgressPercentage(0);
        assertTrue(testHabitIncrementE.equals(expected));
    }

    // EFFECTS: Creates a copy of habitIncrement
    public Habit copyHabitIncrement (Habit habitIncrement) {
        Habit h = new HabitIncrement(habitIncrement.getGoal(), habitIncrement.getStartingAmount(), 
            habitIncrement.getStepAmount(), habitIncrement.getTitle(), 
            habitIncrement.getCycleTime(), habitIncrement.getCurrentDay(), testHabitCycleManager);
        
        h.setNextCycleTime(habitIncrement.getNextCycleTime());

        return h;
    }
}
