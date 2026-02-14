package model.habit;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.habit.Habit.ProgressType;
import model.organization.specialpages.AllHabitsPage;

public class TestHabitDecrement {
    private AllHabitsPage testAllHabitsPage;
    private HabitCycleManager testHabitCycleManager;

    private LocalDate localDate; // Fr Feb 13, 2026
    private LocalTime localTime; // 23:30
    private LocalDateTime localDateTime; // localDate + localTime

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

        testHabitDecrementA = new HabitDecrement(0, 1, 1, "Quitting smoking", null, localTime, localDate, 
            testAllHabitsPage, testHabitCycleManager);
        testHabitDecrementB = new HabitDecrement(0, 5, 1, "Doomscrolling", "sessions", localTime, localDate, 
            testAllHabitsPage, testHabitCycleManager);
        testHabitDecrementC = new HabitDecrement(0, 10, 2, "Snack abstination", "snacks avoided", localTime, localDate, 
            testAllHabitsPage, testHabitCycleManager);
        testHabitDecrementD = new HabitDecrement(5, 10, 1, "Locking In", "distractions avoided", localTime, localDate, 
            testAllHabitsPage, testHabitCycleManager);
        testHabitDecrementE = new HabitDecrement(-3, 3, 1, "Cutting", "Net 100s of calories", localTime, localDate, 
            testAllHabitsPage, testHabitCycleManager);
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
        expected.setCurrentAmount(0);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.DONE);
        assertTrue(testHabitDecrementA.equals(expected));
    }

    @Test
    void testProgressByStepAmountB() {
        expected = copyHabitDecrement(testHabitDecrementB);

        assertEquals(5, testHabitDecrementB.getCurrentAmount());

        testHabitDecrementB.progressByStepAmount();
        expected.setCurrentAmount(4);
        expected.setProgressPercentage(20);
        assertTrue(testHabitDecrementB.equals(expected));

        testHabitDecrementB.progressByStepAmount();
        expected.setCurrentAmount(3);
        expected.setProgressPercentage(40);

        testHabitDecrementB.progressByStepAmount();

        testHabitDecrementB.progressByStepAmount();
        expected.setCurrentAmount(1);
        expected.setProgressPercentage(80);
        assertTrue(testHabitDecrementB.equals(expected));

        testHabitDecrementB.progressByStepAmount();
        expected.setCurrentAmount(0);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.DONE);
        assertTrue(testHabitDecrementB.equals(expected));

    }

    @Test
    void testProgressByStepAmountC() {
        expected = copyHabitDecrement(testHabitDecrementC);

        assertEquals(10, testHabitDecrementC.getCurrentAmount());

        testHabitDecrementC.progressByStepAmount();
        expected.setCurrentAmount(8);
        expected.setProgressPercentage(20);
        assertTrue(testHabitDecrementC.equals(expected));

        testHabitDecrementC.progressByStepAmount();
        expected.setCurrentAmount(6);
        expected.setProgressPercentage(40);
        assertTrue(testHabitDecrementC.equals(expected));

        testHabitDecrementC.progressByStepAmount();

        testHabitDecrementC.progressByStepAmount();
        expected.setCurrentAmount(2);
        expected.setProgressPercentage(80);
        assertTrue(testHabitDecrementC.equals(expected));

        testHabitDecrementC.progressByStepAmount();
        expected.setCurrentAmount(0);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.DONE);
        assertTrue(testHabitDecrementC.equals(expected));
    }

    @Test
    void testProgressByStepAmountD() {
        expected = copyHabitDecrement(testHabitDecrementD);

        assertEquals(10, testHabitDecrementD.getCurrentAmount());

        testHabitDecrementD.progressByStepAmount();
        expected.setCurrentAmount(9);
        expected.setProgressPercentage(20);
        assertTrue(testHabitDecrementD.equals(expected));

        testHabitDecrementD.progressByStepAmount();
        expected.setCurrentAmount(8);
        expected.setProgressPercentage(40);
        assertTrue(testHabitDecrementD.equals(expected));

        testHabitDecrementD.progressByStepAmount();

        testHabitDecrementD.progressByStepAmount();
        expected.setCurrentAmount(6);
        expected.setProgressPercentage(80);
        assertTrue(testHabitDecrementD.equals(expected));

        testHabitDecrementD.progressByStepAmount();
        expected.setCurrentAmount(5);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.DONE);
        assertTrue(testHabitDecrementD.equals(expected));

        testHabitDecrementD.progressByStepAmount();
        expected.setCurrentAmount(4);
        expected.setOverloadAmount(1);
        expected.setProgressType(ProgressType.OVERLOADED);
        assertTrue(testHabitDecrementD.equals(expected));

        testHabitDecrementD.progressByStepAmount();
        expected.setCurrentAmount(3);
        expected.setOverloadAmount(2);
        assertTrue(testHabitDecrementD.equals(expected));
    }

    @Test
    void testProgressByStepAmountE() {
        expected = copyHabitDecrement(testHabitDecrementE);

        assertEquals(3, testHabitDecrementE.getCurrentAmount());

        testHabitDecrementE.progressByStepAmount();
        expected.setCurrentAmount(2);
        expected.setProgressPercentage(17);
        assertTrue(testHabitDecrementE.equals(expected));

        testHabitDecrementE.progressByStepAmount();
        expected.setCurrentAmount(1);
        expected.setProgressPercentage(34);
        assertTrue(testHabitDecrementE.equals(expected));

        testHabitDecrementE.progressByStepAmount();
        expected.setCurrentAmount(0);
        expected.setProgressPercentage(50);
        assertTrue(testHabitDecrementE.equals(expected));

        testHabitDecrementE.progressByStepAmount();
        expected.setCurrentAmount(-1);
        expected.setProgressPercentage(67);
        assertTrue(testHabitDecrementE.equals(expected));

        testHabitDecrementE.progressByStepAmount();
        expected.setCurrentAmount(-2);
        expected.setProgressPercentage(83);
        assertTrue(testHabitDecrementE.equals(expected));

        testHabitDecrementE.progressByStepAmount();
        expected.setCurrentAmount(-3);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.DONE);
        assertTrue(testHabitDecrementE.equals(expected));

        testHabitDecrementE.progressByStepAmount();
        expected.setCurrentAmount(-4);
        expected.setOverloadAmount(1);
        expected.setProgressType(ProgressType.OVERLOADED);
        assertTrue(testHabitDecrementE.equals(expected));

        testHabitDecrementE.progressByStepAmount();
        expected.setCurrentAmount(-5);
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
        assertEquals(17, testHabitDecrementE.calculateProgressPercentage(3, 2, -3));
        assertEquals(50, testHabitDecrementE.calculateProgressPercentage(3, 0, -3));
        assertEquals(83, testHabitDecrementE.calculateProgressPercentage(3, -2, -3));
        assertEquals(100, testHabitDecrementE.calculateProgressPercentage(3, -3, -3));
    }

    @Test
    void testSetCurrentAmountDecreasingB() {
        expected = copyHabitDecrement(testHabitDecrementB);

        testHabitDecrementB.setCurrentAmount(4);
        expected.setCurrentAmount(4);
        expected.setProgressPercentage(20);
        assertTrue(testHabitDecrementB.equals(expected));


        testHabitDecrementB.setCurrentAmount(1);
        expected.setCurrentAmount(1);
        expected.setProgressPercentage(80);
        assertTrue(testHabitDecrementB.equals(expected));

        testHabitDecrementB.setCurrentAmount(0);
        expected.setCurrentAmount(0);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.DONE);
        assertTrue(testHabitDecrementB.equals(expected));

        testHabitDecrementB.setCurrentAmount(-1);
        expected.setCurrentAmount(-1);
        expected.setOverloadAmount(1);
        expected.setProgressType(ProgressType.OVERLOADED);
        assertTrue(testHabitDecrementB.equals(expected));

        testHabitDecrementB.setCurrentAmount(-2);
        expected.setCurrentAmount(-2);
        expected.setOverloadAmount(2);
        assertTrue(testHabitDecrementB.equals(expected));
    }

    @Test
    void testSetCurrentAmountDecreasingD() {
        expected = copyHabitDecrement(testHabitDecrementD);

        testHabitDecrementD.setCurrentAmount(9);
        expected.setCurrentAmount(9);
        expected.setProgressPercentage(20);
        assertTrue(testHabitDecrementD.equals(expected));

        testHabitDecrementD.setCurrentAmount(6);
        expected.setCurrentAmount(6);
        expected.setProgressPercentage(80);
        assertTrue(testHabitDecrementD.equals(expected));

        testHabitDecrementD.setCurrentAmount(5);
        expected.setCurrentAmount(5);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.DONE);
        assertTrue(testHabitDecrementD.equals(expected));

        testHabitDecrementD.setCurrentAmount(4);
        expected.setCurrentAmount(4);
        expected.setOverloadAmount(1);
        expected.setProgressType(ProgressType.OVERLOADED);
        assertTrue(testHabitDecrementD.equals(expected));

        testHabitDecrementD.setCurrentAmount(3);
        expected.setCurrentAmount(3);
        expected.setOverloadAmount(2);
        assertTrue(testHabitDecrementD.equals(expected));
    }

    @Test
    void testSetCurrentAmountDecreasingE() {
        expected = copyHabitDecrement(testHabitDecrementE);

        testHabitDecrementE.setCurrentAmount(2);
        expected.setCurrentAmount(2);
        expected.setProgressPercentage(17);
        assertTrue(testHabitDecrementE.equals(expected));

        testHabitDecrementE.setCurrentAmount(-2);
        expected.setCurrentAmount(-2);
        expected.setProgressPercentage(83);
        assertTrue(testHabitDecrementE.equals(expected));

        testHabitDecrementE.setCurrentAmount(-3);
        expected.setCurrentAmount(-3);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.DONE);
        assertTrue(testHabitDecrementE.equals(expected));

        testHabitDecrementE.setCurrentAmount(-4);
        expected.setCurrentAmount(-4);
        expected.setOverloadAmount(1);
        expected.setProgressType(ProgressType.OVERLOADED);
        assertTrue(testHabitDecrementE.equals(expected));

        testHabitDecrementE.setCurrentAmount(-5);
        expected.setCurrentAmount(-5);
        expected.setOverloadAmount(2);
        assertTrue(testHabitDecrementE.equals(expected));
    }

    @Test
    void testSetCurrentAmountIncreasingB() {
        expected = copyHabitDecrement(testHabitDecrementB);

        testHabitDecrementB.setCurrentAmount(-2);
        expected.setCurrentAmount(-2);
        expected.setOverloadAmount(2);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.OVERLOADED);
        assertTrue(testHabitDecrementB.equals(expected));

        testHabitDecrementB.setCurrentAmount(-1);
        expected.setCurrentAmount(-1);
        expected.setOverloadAmount(1);
        assertTrue(testHabitDecrementB.equals(expected));

        testHabitDecrementB.setCurrentAmount(0);
        expected.setCurrentAmount(0);
        expected.setOverloadAmount(0);
        expected.setProgressType(ProgressType.DONE);
        assertTrue(testHabitDecrementB.equals(expected));

        testHabitDecrementB.setCurrentAmount(1);
        expected.setCurrentAmount(1);
        expected.setProgressPercentage(80);
        expected.setProgressType(ProgressType.UNDERDONE);
        assertTrue(testHabitDecrementB.equals(expected));

        testHabitDecrementB.setCurrentAmount(4);
        expected.setCurrentAmount(4);
        expected.setProgressPercentage(20);
        assertTrue(testHabitDecrementB.equals(expected));
    }

    @Test
    void testSetCurrentAmountIncreasingD() {
        expected = copyHabitDecrement(testHabitDecrementD);

        testHabitDecrementD.setCurrentAmount(3);
        expected.setCurrentAmount(3);
        expected.setOverloadAmount(2);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.OVERLOADED);
        assertTrue(testHabitDecrementD.equals(expected));

        testHabitDecrementD.setCurrentAmount(4);
        expected.setCurrentAmount(4);
        expected.setOverloadAmount(1);
        assertTrue(testHabitDecrementD.equals(expected));

        testHabitDecrementD.setCurrentAmount(5);
        expected.setCurrentAmount(5);
        expected.setOverloadAmount(0);
        expected.setProgressType(ProgressType.DONE);
        assertTrue(testHabitDecrementD.equals(expected));

        testHabitDecrementD.setCurrentAmount(6);
        expected.setCurrentAmount(6);
        expected.setProgressPercentage(80);
        expected.setProgressType(ProgressType.UNDERDONE);
        assertTrue(testHabitDecrementD.equals(expected));

        testHabitDecrementD.setCurrentAmount(9);
        expected.setCurrentAmount(9);
        expected.setProgressPercentage(20);
        assertTrue(testHabitDecrementD.equals(expected));
    }

    @Test
    void testSetCurrentAmountIncreasingE() {
        expected = copyHabitDecrement(testHabitDecrementE);

        testHabitDecrementE.setCurrentAmount(-5);
        expected.setCurrentAmount(-5);
        expected.setOverloadAmount(2);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.OVERLOADED);
        assertTrue(testHabitDecrementE.equals(expected));
        
        testHabitDecrementE.setCurrentAmount(-4);
        expected.setCurrentAmount(-4);
        expected.setOverloadAmount(1);
        assertTrue(testHabitDecrementE.equals(expected));

        testHabitDecrementE.setCurrentAmount(-3);
        expected.setCurrentAmount(-3);
        expected.setOverloadAmount(0);
        expected.setProgressType(ProgressType.DONE);
        assertTrue(testHabitDecrementE.equals(expected));

        testHabitDecrementE.setCurrentAmount(-2);
        expected.setCurrentAmount(-2);
        expected.setProgressPercentage(83);
        expected.setProgressType(ProgressType.UNDERDONE);
        assertTrue(testHabitDecrementE.equals(expected));

        testHabitDecrementE.setCurrentAmount(2);
        expected.setCurrentAmount(2);
        expected.setProgressPercentage(17);
        assertTrue(testHabitDecrementE.equals(expected));
    }

    // EFFECTS: Creates a copy of habitDecrement
    public Habit copyHabitDecrement (Habit habitDecrement) {
        return new HabitDecrement(habitDecrement.getGoal(), habitDecrement.getStartingAmount(), 
            habitDecrement.getStepAmount(), habitDecrement.getTitle(), habitDecrement.getUnit(), 
            habitDecrement.getCycleTime(), habitDecrement.getCurrentDay(), testAllHabitsPage, testHabitCycleManager);
    }
}
