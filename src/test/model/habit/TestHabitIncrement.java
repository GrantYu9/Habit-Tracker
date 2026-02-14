package model.habit;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.habit.Habit.ProgressType;
import model.organization.specialpages.AllHabitsPage;

public class TestHabitIncrement {
    private Habit testHabitIncrementA; // Boolean
    private Habit testHabitIncrementB; // Step 1
    private Habit testHabitIncrementC; // Step 2
    private Habit testHabitIncrementD; // Head start

    private AllHabitsPage testAllHabitsPage;
    
    @BeforeEach
    void runBefore() {
        /* !!! remove
        testHabitIncrementA = new HabitIncrement(1, 0, 1, "Workout", null, testAllHabitsPage);
        testHabitIncrementB = new HabitIncrement(5, 0, 1, "Study", "study sessions", testAllHabitsPage);
        testHabitIncrementC = new HabitIncrement(10, 0, 2, "Hydration", "sips", testAllHabitsPage);
        testHabitIncrementD = new HabitIncrement(5, 1, 1, "Back stength", "pullups", testAllHabitsPage);
         */

        testAllHabitsPage = new AllHabitsPage();
    }

    @Test
    void testProgressByStepAmountUnderB() {
        // !!!
    }

    @Test
    void testProgressByStepAmountUnderC() {
        // !!!
    }

    @Test
    void testProgressByStepAmountUnderD() {
        // !!!
    }

    @Test
    void testProgressByStepAmountAtA() {
        // !!!
    }

    @Test
    void testProgressByStepAmountAtB() {
        // !!!
    }

    @Test
    void testProgressByStepAmountAtC() {
        // !!!
    }

    @Test
    void testProgressByStepAmountAtD() {
        // !!!
    }

    @Test
    void testProgressByStepAmountOverA() {
        // !!!
    }

    @Test
    void testProgressByStepAmountOverB() {
        // !!!
    }

    @Test
    void testProgressByStepAmountOverC() {
        // !!!
    }
    
    @Test
    void testProgressByStepAmountOverD() {
        // !!!
    }

    @Test
    void testCalculateOverloadAmountA() {
        // !!!
    }

    @Test
    void testCalculateOverloadAmountB() {
        // !!!
    }

    @Test
    void testCalculateOverloadAmountC() {
        // !!!
    }

    @Test
    void testCalculateOverloadAmountD() {
        // !!!
    }

    @Test
    void testCalculateProgressPercentageNoneA() {
        // !!!
    }

    @Test
    void testCalculateProgressPercentageNoneD() {
        // !!!
    }

    @Test
    void testCalculateProgressPercentageUnderB() {
        // !!!
    }

    @Test
    void testCalculateProgressPercentageUnderC() {
        // !!!
    }

    @Test
    void testCalculateProgressPercentageUnderD() {
        // !!!
    }

    @Test
    void testCalculateProgressPercentageAtB() {
        // !!!
    }

    @Test
    void testCalculateProgressPercentageAtD() {
        // !!!
    }

    @Test
    void testSetCurrentAmountBeginningA() {
        // !!!
    }

    @Test
    void testSetCurrentAmountBeginningB() {
        // !!!
    }

    @Test
    void testSetCurrentAmountBeginningD() {
        // !!!
    }

    @Test
    void testSetCurrentAmountUnderB() {
        // !!!
    }

    @Test
    void testSetCurrentAmountUnderC() {
        // !!!
    }

    @Test
    void testSetCurrentAmountUnderD() {
        // !!!
    }

    @Test
    void testSetCurrentAmountAtA() {
        // !!!
    }

    @Test
    void testSetCurrentAmountAtB() {
        // !!!
    }

    @Test
    void testSetCurrentAmountAtD() {
        // !!!
    }

    @Test
    void testSetCurrentAmountOverA() {
        // !!!
    }

    @Test
    void testSetCurrentAmountOverB() {
        // !!!
    }

    @Test
    void testSetCurrentAmountOverC() {
        // !!!
    }

    @Test
    void testSetCurrentAmountOverD() {
        // !!!
    }
}
