package model.habit;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.habit.Habit.ProgressType;
import model.organization.centralization.AllHabitsPage;

public class TestHabitDecrement {
    private AllHabitsPage testAllHabitsPage;
    private HabitCycleManager testHabitCycleManager;

    private LocalDate localDate; // Fr Feb 13, 2026
    private LocalTime localTime; // 23:30
    private LocalDateTime localDateTime; // localDate + localTime
    private LocalDateTime rightNow; // Right now

    private Habit testHabitDecrementA; // Boolean
    private Habit testHabitDecrementB; // Step 1
    private Habit testHabitDecrementC; // Step 2
    private Habit testHabitDecrementD; // Lowered the bar
    private Habit testHabitDecrementE; // Positives to negatives

    private Habit expected;
    
    @BeforeEach
    void runBefore() {
        testAllHabitsPage = new AllHabitsPage();
        testHabitCycleManager = new HabitCycleManager(testAllHabitsPage, localDateTime);

        localDate = LocalDate.of(2026, 2, 13);
        localTime = LocalTime.of(23, 30);
        localDateTime = LocalDateTime.of(localDate, localTime);
        rightNow = LocalDateTime.now();

        testHabitDecrementA = new HabitDecrement(0, 1, 1, "Quitting smoking", localTime, localDate, 
            rightNow, testHabitCycleManager);
        testHabitDecrementB = new HabitDecrement(0, 5, 1, "Doomscrolling", localTime, localDate, 
            rightNow, testHabitCycleManager);
        testHabitDecrementC = new HabitDecrement(0, 10, 2, "Snack abstination", localTime, localDate, 
            rightNow, testHabitCycleManager);
        testHabitDecrementD = new HabitDecrement(5, 10, 1, "Locking In", localTime, localDate, 
            rightNow, testHabitCycleManager);
        testHabitDecrementE = new HabitDecrement(-3, 3, 1, "Cutting", localTime, localDate, 
            rightNow, testHabitCycleManager);
    }

    @Test
    void testConstructorA() {
        assertEquals(0, testHabitDecrementA.getProgressPercentage());
    }

    @Test
    void testConstructorD() {
        assertEquals(0, testHabitDecrementD.getProgressPercentage());
    }

    @Test
    void testConstructorE() {
        assertEquals(0, testHabitDecrementE.getProgressPercentage());
    }

    @Test
    void testProgressByStepAmountA() {
        expected = copyHabitDecrement(testHabitDecrementA);

        assertEquals(1, testHabitDecrementA.getCurrentAmount());

        testHabitDecrementA.progressByStepAmount();
        expected.setCurrentAmountLogic(0);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.DONE);
        assertTrue(testHabitDecrementA.equals(expected));
    }

    @Test
    void testProgressByStepAmountB() {
        expected = copyHabitDecrement(testHabitDecrementB);

        assertEquals(5, testHabitDecrementB.getCurrentAmount());

        testHabitDecrementB.progressByStepAmount();
        expected.setCurrentAmountLogic(4);
        expected.setProgressPercentage(20);
        assertTrue(testHabitDecrementB.equals(expected));

        testHabitDecrementB.progressByStepAmount();
        expected.setCurrentAmountLogic(3);
        expected.setProgressPercentage(40);

        testHabitDecrementB.progressByStepAmount();

        testHabitDecrementB.progressByStepAmount();
        expected.setCurrentAmountLogic(1);
        expected.setProgressPercentage(80);
        assertTrue(testHabitDecrementB.equals(expected));

        testHabitDecrementB.progressByStepAmount();
        expected.setCurrentAmountLogic(0);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.DONE);
        assertTrue(testHabitDecrementB.equals(expected));

    }

    @Test
    void testProgressByStepAmountC() {
        expected = copyHabitDecrement(testHabitDecrementC);

        assertEquals(10, testHabitDecrementC.getCurrentAmount());

        testHabitDecrementC.progressByStepAmount();
        expected.setCurrentAmountLogic(8);
        expected.setProgressPercentage(20);
        assertTrue(testHabitDecrementC.equals(expected));

        testHabitDecrementC.progressByStepAmount();
        expected.setCurrentAmountLogic(6);
        expected.setProgressPercentage(40);
        assertTrue(testHabitDecrementC.equals(expected));

        testHabitDecrementC.progressByStepAmount();

        testHabitDecrementC.progressByStepAmount();
        expected.setCurrentAmountLogic(2);
        expected.setProgressPercentage(80);
        assertTrue(testHabitDecrementC.equals(expected));

        testHabitDecrementC.progressByStepAmount();
        expected.setCurrentAmountLogic(0);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.DONE);
        assertTrue(testHabitDecrementC.equals(expected));
    }

    @Test
    void testProgressByStepAmountD() {
        expected = copyHabitDecrement(testHabitDecrementD);

        assertEquals(10, testHabitDecrementD.getCurrentAmount());

        testHabitDecrementD.progressByStepAmount();
        expected.setCurrentAmountLogic(9);
        expected.setProgressPercentage(20);
        assertTrue(testHabitDecrementD.equals(expected));

        testHabitDecrementD.progressByStepAmount();
        expected.setCurrentAmountLogic(8);
        expected.setProgressPercentage(40);
        assertTrue(testHabitDecrementD.equals(expected));

        testHabitDecrementD.progressByStepAmount();

        testHabitDecrementD.progressByStepAmount();
        expected.setCurrentAmountLogic(6);
        expected.setProgressPercentage(80);
        assertTrue(testHabitDecrementD.equals(expected));

        testHabitDecrementD.progressByStepAmount();
        expected.setCurrentAmountLogic(5);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.DONE);
        assertTrue(testHabitDecrementD.equals(expected));

        testHabitDecrementD.progressByStepAmount();
        expected.setCurrentAmountLogic(4);
        expected.setOverloadAmount(1);
        expected.setProgressType(ProgressType.OVERLOADED);
        assertTrue(testHabitDecrementD.equals(expected));

        testHabitDecrementD.progressByStepAmount();
        expected.setCurrentAmountLogic(3);
        expected.setOverloadAmount(2);
        assertTrue(testHabitDecrementD.equals(expected));
    }

    @Test
    void testProgressByStepAmountE() {
        expected = copyHabitDecrement(testHabitDecrementE);

        assertEquals(3, testHabitDecrementE.getCurrentAmount());

        testHabitDecrementE.progressByStepAmount();
        expected.setCurrentAmountLogic(2);
        expected.setProgressPercentage(16);
        assertTrue(testHabitDecrementE.equals(expected));

        testHabitDecrementE.progressByStepAmount();
        expected.setCurrentAmountLogic(1);
        expected.setProgressPercentage(33);
        assertTrue(testHabitDecrementE.equals(expected));

        testHabitDecrementE.progressByStepAmount();
        expected.setCurrentAmountLogic(0);
        expected.setProgressPercentage(50);
        assertTrue(testHabitDecrementE.equals(expected));

        testHabitDecrementE.progressByStepAmount();
        expected.setCurrentAmountLogic(-1);
        expected.setProgressPercentage(66);
        assertTrue(testHabitDecrementE.equals(expected));

        testHabitDecrementE.progressByStepAmount();
        expected.setCurrentAmountLogic(-2);
        expected.setProgressPercentage(83);
        assertTrue(testHabitDecrementE.equals(expected));

        testHabitDecrementE.progressByStepAmount();
        expected.setCurrentAmountLogic(-3);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.DONE);
        assertTrue(testHabitDecrementE.equals(expected));

        testHabitDecrementE.progressByStepAmount();
        expected.setCurrentAmountLogic(-4);
        expected.setOverloadAmount(1);
        expected.setProgressType(ProgressType.OVERLOADED);
        assertTrue(testHabitDecrementE.equals(expected));

        testHabitDecrementE.progressByStepAmount();
        expected.setCurrentAmountLogic(-5);
        expected.setOverloadAmount(2);
        assertTrue(testHabitDecrementE.equals(expected));
    }

    @Test
    void testCalculateOverloadAmountD() {
        assertEquals(1, testHabitDecrementD.calculateOverloadAmount(4, 5));
        assertEquals(2, testHabitDecrementD.calculateOverloadAmount(3, 5));
    }

    @Test
    void testCalculateOverloadAmountE() {
        assertEquals(1, testHabitDecrementE.calculateOverloadAmount(-4, -3));
        assertEquals(2, testHabitDecrementE.calculateOverloadAmount(-5, -3));
    }

    @Test
    void testCalculateProgressPercentageB() {
        assertEquals(0, testHabitDecrementB.calculateProgressPercentage(5, 5, 0));
        assertEquals(20, testHabitDecrementB.calculateProgressPercentage(5, 4, 0));
        assertEquals(40, testHabitDecrementB.calculateProgressPercentage(5, 3, 0));
        assertEquals(80, testHabitDecrementB.calculateProgressPercentage(5, 1, 0));
        assertEquals(100, testHabitDecrementB.calculateProgressPercentage(5, 0, 0));
    }

    @Test
    void testCalculateProgressPercentageD() {
        assertEquals(0, testHabitDecrementD.calculateProgressPercentage(10, 10, 5));
        assertEquals(20, testHabitDecrementD.calculateProgressPercentage(10, 9, 5));
        assertEquals(40, testHabitDecrementD.calculateProgressPercentage(10, 8, 5));
        assertEquals(80, testHabitDecrementD.calculateProgressPercentage(10, 6, 5));
        assertEquals(100, testHabitDecrementD.calculateProgressPercentage(10, 5, 5));
    }

    @Test
    void testCalculateProgressPercentageE() {
        assertEquals(0, testHabitDecrementE.calculateProgressPercentage(3, 3, -3));
        assertEquals(16, testHabitDecrementE.calculateProgressPercentage(3, 2, -3));
        assertEquals(50, testHabitDecrementE.calculateProgressPercentage(3, 0, -3));
        assertEquals(83, testHabitDecrementE.calculateProgressPercentage(3, -2, -3));
        assertEquals(100, testHabitDecrementE.calculateProgressPercentage(3, -3, -3));
    }

    @Test
    void testSetCurrentAmountDecreasingB() {
        expected = copyHabitDecrement(testHabitDecrementB);

        testHabitDecrementB.setCurrentAmountLogic(4);
        expected.setCurrentAmountLogic(4);
        expected.setProgressPercentage(20);
        assertTrue(testHabitDecrementB.equals(expected));


        testHabitDecrementB.setCurrentAmountLogic(1);
        expected.setCurrentAmountLogic(1);
        expected.setProgressPercentage(80);
        assertTrue(testHabitDecrementB.equals(expected));

        testHabitDecrementB.setCurrentAmountLogic(0);
        expected.setCurrentAmountLogic(0);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.DONE);
        assertTrue(testHabitDecrementB.equals(expected));

        testHabitDecrementB.setCurrentAmountLogic(-1);
        expected.setCurrentAmountLogic(-1);
        expected.setOverloadAmount(1);
        expected.setProgressType(ProgressType.OVERLOADED);
        assertTrue(testHabitDecrementB.equals(expected));

        testHabitDecrementB.setCurrentAmountLogic(-2);
        expected.setCurrentAmountLogic(-2);
        expected.setOverloadAmount(2);
        assertTrue(testHabitDecrementB.equals(expected));
    }

    @Test
    void testSetCurrentAmountDecreasingD() {
        expected = copyHabitDecrement(testHabitDecrementD);

        testHabitDecrementD.setCurrentAmountLogic(9);
        expected.setCurrentAmountLogic(9);
        expected.setProgressPercentage(20);
        assertTrue(testHabitDecrementD.equals(expected));

        testHabitDecrementD.setCurrentAmountLogic(6);
        expected.setCurrentAmountLogic(6);
        expected.setProgressPercentage(80);
        assertTrue(testHabitDecrementD.equals(expected));

        testHabitDecrementD.setCurrentAmountLogic(5);
        expected.setCurrentAmountLogic(5);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.DONE);
        assertTrue(testHabitDecrementD.equals(expected));

        testHabitDecrementD.setCurrentAmountLogic(4);
        expected.setCurrentAmountLogic(4);
        expected.setOverloadAmount(1);
        expected.setProgressType(ProgressType.OVERLOADED);
        assertTrue(testHabitDecrementD.equals(expected));

        testHabitDecrementD.setCurrentAmountLogic(3);
        expected.setCurrentAmountLogic(3);
        expected.setOverloadAmount(2);
        assertTrue(testHabitDecrementD.equals(expected));
    }

    @Test
    void testSetCurrentAmountDecreasingE() {
        expected = copyHabitDecrement(testHabitDecrementE);

        testHabitDecrementE.setCurrentAmountLogic(2);
        expected.setCurrentAmountLogic(2);
        expected.setProgressPercentage(16);
        assertTrue(testHabitDecrementE.equals(expected));

        testHabitDecrementE.setCurrentAmountLogic(-2);
        expected.setCurrentAmountLogic(-2);
        expected.setProgressPercentage(83);
        assertTrue(testHabitDecrementE.equals(expected));

        testHabitDecrementE.setCurrentAmountLogic(-3);
        expected.setCurrentAmountLogic(-3);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.DONE);
        assertTrue(testHabitDecrementE.equals(expected));

        testHabitDecrementE.setCurrentAmountLogic(-4);
        expected.setCurrentAmountLogic(-4);
        expected.setOverloadAmount(1);
        expected.setProgressType(ProgressType.OVERLOADED);
        assertTrue(testHabitDecrementE.equals(expected));

        testHabitDecrementE.setCurrentAmountLogic(-5);
        expected.setCurrentAmountLogic(-5);
        expected.setOverloadAmount(2);
        assertTrue(testHabitDecrementE.equals(expected));
    }

    @Test
    void testSetCurrentAmountIncreasingB() {
        expected = copyHabitDecrement(testHabitDecrementB);

        testHabitDecrementB.setCurrentAmountLogic(-2);
        expected.setCurrentAmountLogic(-2);
        expected.setOverloadAmount(2);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.OVERLOADED);
        assertTrue(testHabitDecrementB.equals(expected));

        testHabitDecrementB.setCurrentAmountLogic(-1);
        expected.setCurrentAmountLogic(-1);
        expected.setOverloadAmount(1);
        assertTrue(testHabitDecrementB.equals(expected));

        testHabitDecrementB.setCurrentAmountLogic(0);
        expected.setCurrentAmountLogic(0);
        expected.setOverloadAmount(0);
        expected.setProgressType(ProgressType.DONE);
        assertTrue(testHabitDecrementB.equals(expected));

        testHabitDecrementB.setCurrentAmountLogic(1);
        expected.setCurrentAmountLogic(1);
        expected.setProgressPercentage(80);
        expected.setProgressType(ProgressType.UNDERDONE);
        assertTrue(testHabitDecrementB.equals(expected));

        testHabitDecrementB.setCurrentAmountLogic(4);
        expected.setCurrentAmountLogic(4);
        expected.setProgressPercentage(20);
        assertTrue(testHabitDecrementB.equals(expected));
    }

    @Test
    void testSetCurrentAmountIncreasingD() {
        expected = copyHabitDecrement(testHabitDecrementD);

        testHabitDecrementD.setCurrentAmountLogic(3);
        expected.setCurrentAmountLogic(3);
        expected.setOverloadAmount(2);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.OVERLOADED);
        assertTrue(testHabitDecrementD.equals(expected));

        testHabitDecrementD.setCurrentAmountLogic(4);
        expected.setCurrentAmountLogic(4);
        expected.setOverloadAmount(1);
        assertTrue(testHabitDecrementD.equals(expected));

        testHabitDecrementD.setCurrentAmountLogic(5);
        expected.setCurrentAmountLogic(5);
        expected.setOverloadAmount(0);
        expected.setProgressType(ProgressType.DONE);
        assertTrue(testHabitDecrementD.equals(expected));

        testHabitDecrementD.setCurrentAmountLogic(6);
        expected.setCurrentAmountLogic(6);
        expected.setProgressPercentage(80);
        expected.setProgressType(ProgressType.UNDERDONE);
        assertTrue(testHabitDecrementD.equals(expected));

        testHabitDecrementD.setCurrentAmountLogic(9);
        expected.setCurrentAmountLogic(9);
        expected.setProgressPercentage(20);
        assertTrue(testHabitDecrementD.equals(expected));
    }

    @Test
    void testSetCurrentAmountIncreasingE() {
        expected = copyHabitDecrement(testHabitDecrementE);

        testHabitDecrementE.setCurrentAmountLogic(-5);
        expected.setCurrentAmountLogic(-5);
        expected.setOverloadAmount(2);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.OVERLOADED);
        assertTrue(testHabitDecrementE.equals(expected));
        
        testHabitDecrementE.setCurrentAmountLogic(-4);
        expected.setCurrentAmountLogic(-4);
        expected.setOverloadAmount(1);
        assertTrue(testHabitDecrementE.equals(expected));

        testHabitDecrementE.setCurrentAmountLogic(-3);
        expected.setCurrentAmountLogic(-3);
        expected.setOverloadAmount(0);
        expected.setProgressType(ProgressType.DONE);
        assertTrue(testHabitDecrementE.equals(expected));

        testHabitDecrementE.setCurrentAmountLogic(-2);
        expected.setCurrentAmountLogic(-2);
        expected.setProgressPercentage(83);
        expected.setProgressType(ProgressType.UNDERDONE);
        assertTrue(testHabitDecrementE.equals(expected));

        testHabitDecrementE.setCurrentAmountLogic(2);
        expected.setCurrentAmountLogic(2);
        expected.setProgressPercentage(16);
        assertTrue(testHabitDecrementE.equals(expected));
    }

    // EFFECTS: Creates a copy of habitDecrement
    public Habit copyHabitDecrement (Habit habitDecrement) {
        Habit h = new HabitDecrement(habitDecrement.getGoal(), habitDecrement.getStartingAmount(), 
            habitDecrement.getStepAmount(), habitDecrement.getTitle(), 
            habitDecrement.getCycleTime(), habitDecrement.getCurrentDay(), rightNow, testHabitCycleManager);

        h.setNextCycleTime(habitDecrement.getNextCycleTime());

        return h;
    }
}
