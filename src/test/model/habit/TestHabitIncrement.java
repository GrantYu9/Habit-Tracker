package model.habit;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.habit.Habit.ProgressType;
import model.organization.specialpages.AllHabitsPage;

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
    
    @BeforeEach
    void runBefore() {
        testAllHabitsPage = new AllHabitsPage();
        testHabitCycleManager = new HabitCycleManager(testAllHabitsPage, localDateTime);

        localDate = LocalDate.of(2026, 2, 13);
        localTime = LocalTime.of(23, 30);
        localDateTime = LocalDateTime.of(localDate, localTime);

        testHabitIncrementA = new HabitIncrement(1, 0, 1, "Workout", null, localTime, localDate, testAllHabitsPage, testHabitCycleManager);
        testHabitIncrementB = new HabitIncrement(5, 0, 1, "Study", "study sessions", localTime, localDate, testAllHabitsPage, testHabitCycleManager);
        testHabitIncrementC = new HabitIncrement(10, 0, 2, "Hydration", "sips", localTime, localDate, testAllHabitsPage, testHabitCycleManager);
        testHabitIncrementD = new HabitIncrement(5, 1, 1, "Back stength", "pullups", localTime, localDate, testAllHabitsPage, testHabitCycleManager);
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
    void testProgressByStepAmountUnderBCoherence() {
        Habit expected = copyHabitIncrement(testHabitIncrementB);

        assertEquals(0, testHabitIncrementB.getCurrentAmount());

        testHabitIncrementB.progressByStepAmount();
        expected.setCurrentAmount(1);
        expected.setProgressPercentage(20);
        assertEquals(expected, testHabitIncrementB.getCurrentAmount());

        testHabitIncrementB.progressByStepAmount();
        expected.setCurrentAmount(2);
        expected.setProgressPercentage(40);
        assertEquals(expected, testHabitIncrementB.getCurrentAmount());
    }

    @Test
    void testProgressByStepAmountUnderC() {
        Habit expected = copyHabitIncrement(testHabitIncrementC);

        assertEquals(0, testHabitIncrementC.getCurrentAmount());

        testHabitIncrementC.progressByStepAmount();
        expected.setCurrentAmount(2);
        expected.setProgressPercentage(20);
        assertEquals(expected, testHabitIncrementC.getCurrentAmount());

        testHabitIncrementC.progressByStepAmount();
        expected.setCurrentAmount(4);
        expected.setProgressPercentage(40);
        assertEquals(expected, testHabitIncrementC.getCurrentAmount());
    }

    @Test
    void testProgressByStepAmountUnderD() {
        Habit expected = copyHabitIncrement(testHabitIncrementD);

        assertEquals(1, testHabitIncrementD.getCurrentAmount());

        testHabitIncrementD.progressByStepAmount();
        expected.setCurrentAmount(2);
        expected.setProgressPercentage(25);
        assertEquals(expected, testHabitIncrementD.getCurrentAmount());

        testHabitIncrementD.progressByStepAmount();
        expected.setCurrentAmount(3);
        expected.setProgressPercentage(50);
        assertEquals(expected, testHabitIncrementD.getCurrentAmount());
    }

    @Test
    void testProgressByStepAmountAtA() {
        Habit expected = copyHabitIncrement(testHabitIncrementA);

        assertEquals(0, testHabitIncrementA.getCurrentAmount());

        testHabitIncrementA.progressByStepAmount();
        expected.setCurrentAmount(1);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.DONE);
        assertEquals(expected, testHabitIncrementA.getCurrentAmount());
    }

    @Test
    void testProgressByStepAmountAtB() {
        Habit expected = copyHabitIncrement(testHabitIncrementB);

        testHabitIncrementB.progressByStepAmount(); // 1
        testHabitIncrementB.progressByStepAmount(); // 2
        testHabitIncrementB.progressByStepAmount(); // 3
        testHabitIncrementB.progressByStepAmount(); // 4

        expected.setCurrentAmount(4);
        expected.setProgressPercentage(80);
        assertEquals(expected, testHabitIncrementB.getCurrentAmount());

        testHabitIncrementB.progressByStepAmount();
        expected.setCurrentAmount(5);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.DONE);
        assertEquals(expected, testHabitIncrementB.getCurrentAmount());
    }

    @Test
    void testProgressByStepAmountOverA() {
        Habit expected = copyHabitIncrement(testHabitIncrementA);

        testHabitIncrementA.progressByStepAmount();
        testHabitIncrementA.progressByStepAmount();

        expected.setCurrentAmount(2);
        expected.setOverloadAmount(1);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.OVERLOADED);
        assertEquals(expected, testHabitIncrementA.getCurrentAmount());
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
        assertEquals(expected, testHabitIncrementB.getCurrentAmount());
    }
    
    @Test
    void testProgressByStepAmountOverD() {
        Habit expected = copyHabitIncrement(testHabitIncrementD);

        testHabitIncrementD.progressByStepAmount(); // 2
        testHabitIncrementD.progressByStepAmount(); // 3
        testHabitIncrementD.progressByStepAmount(); // 4
        testHabitIncrementD.progressByStepAmount(); // 5
        testHabitIncrementD.progressByStepAmount(); // 5 + 1

        expected.setCurrentAmount(6);
        expected.setOverloadAmount(1);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.OVERLOADED);
        assertEquals(expected, testHabitIncrementD.getCurrentAmount());
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
    void testSetCurrentAmountIncreasingB() {
        Habit expected = copyHabitIncrement(testHabitIncrementB);
        
        testHabitIncrementB.setCurrentAmount(4);
        expected.setCurrentAmount(4);
        expected.setProgressPercentage(80);
        assertEquals(expected, testHabitIncrementB);

        testHabitIncrementB.setCurrentAmount(5);
        expected.setCurrentAmount(5);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.DONE);
        assertEquals(expected, testHabitIncrementB);

        testHabitIncrementB.setCurrentAmount(6);
        expected.setCurrentAmount(6);
        expected.setOverloadAmount(1);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.OVERLOADED);
        assertEquals(expected, testHabitIncrementB);
    }

    @Test
    void testSetCurrentAmountIncreasingD() {
        Habit expected = copyHabitIncrement(testHabitIncrementD);
        
        testHabitIncrementD.setCurrentAmount(4);
        expected.setCurrentAmount(4);
        expected.setProgressPercentage(75);
        assertEquals(expected, testHabitIncrementD);
        
        testHabitIncrementD.setCurrentAmount(5);
        expected.setCurrentAmount(5);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.DONE);
        assertEquals(expected, testHabitIncrementD);

        testHabitIncrementD.setCurrentAmount(6);
        expected.setCurrentAmount(6);
        expected.setOverloadAmount(1);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.OVERLOADED);
        assertEquals(expected, testHabitIncrementD);
    }

    @Test
    void testSetCurrentAmountDecreasingB() {
        Habit expected = copyHabitIncrement(testHabitIncrementB);

        testHabitIncrementB.setCurrentAmount(6);
        expected.setCurrentAmount(6);
        expected.setOverloadAmount(1);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.OVERLOADED);
        assertEquals(expected, testHabitIncrementB);

        testHabitIncrementB.setCurrentAmount(5);
        expected.setCurrentAmount(5);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.DONE);
        assertEquals(expected, testHabitIncrementB);

        testHabitIncrementB.setCurrentAmount(4);
        expected.setCurrentAmount(4);
        expected.setProgressPercentage(80);
        expected.setProgressType(ProgressType.UNDERDONE);
        assertEquals(expected, testHabitIncrementB);
    }

    @Test
    void testSetCurrentAmountDecreasingD() {
        Habit expected = copyHabitIncrement(testHabitIncrementD);

        testHabitIncrementD.setCurrentAmount(6);
        expected.setCurrentAmount(6);
        expected.setOverloadAmount(1);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.OVERLOADED);
        assertEquals(expected, testHabitIncrementD);

        testHabitIncrementD.setCurrentAmount(5);
        expected.setCurrentAmount(5);
        expected.setProgressPercentage(100);
        expected.setProgressType(ProgressType.DONE);
        assertEquals(expected, testHabitIncrementD);

        testHabitIncrementD.setCurrentAmount(4);
        testHabitIncrementD.setCurrentAmount(4);
        expected.setCurrentAmount(4);
        expected.setProgressPercentage(80);
        expected.setProgressType(ProgressType.UNDERDONE);
        assertEquals(expected, testHabitIncrementD);
    }

    // EFFECTS: Creates a copy of habitIncrement
    public Habit copyHabitIncrement (Habit habitIncrement) {
        return new HabitIncrement(habitIncrement.getGoal(), habitIncrement.getStartingAmount(), 
            habitIncrement.getStepAmount(), habitIncrement.getTitle(), habitIncrement.getUnit(), 
            habitIncrement.getCycleTime(), habitIncrement.getCurrentDay(), testAllHabitsPage, testHabitCycleManager);
    }
}
