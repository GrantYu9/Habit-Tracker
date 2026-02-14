package model.habit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import model.habit.Habit.ProgressType;

public class TestHabitSnaphot {
    private HabitSnapshot testHabitSnapshotA;
    private HabitSnapshot testHabitSnapshotB;

    private LocalDate today;

    @BeforeEach
    void beforeEach() {
        today = LocalDate.now();
    }

    @Test
    void testConstructorA() {
        testHabitSnapshotA = new HabitSnapshot(1, 2, 0, 50, 0, 1, ProgressType.UNDERDONE, today, "breaks");

        assertEquals(1, testHabitSnapshotA.getCurrentAmount());
        assertEquals(2, testHabitSnapshotA.getGoal());
        assertEquals(0, testHabitSnapshotA.getOverloadAmount());
        assertEquals(50, testHabitSnapshotA.getProgressPercentage());
        assertEquals(0, testHabitSnapshotA.getStartingAmount());
        assertEquals(1, testHabitSnapshotA.getStepAmount());
        assertEquals(ProgressType.UNDERDONE, testHabitSnapshotA.getProgressType());
        assertTrue(testHabitSnapshotA.getUnit().equals("breaks"));
    }

    @Test
    void testConstructorB() {
        testHabitSnapshotB = new HabitSnapshot(6, 4, 2, 100, 2, 2, ProgressType.OVERLOADED, today, "study sessions");

        assertEquals(6, testHabitSnapshotB.getCurrentAmount());
        assertEquals(4, testHabitSnapshotB.getGoal());
        assertEquals(2, testHabitSnapshotB.getOverloadAmount());
        assertEquals(100, testHabitSnapshotB.getProgressPercentage());
        assertEquals(2, testHabitSnapshotB.getStartingAmount());
        assertEquals(2, testHabitSnapshotB.getStepAmount());
        assertEquals(ProgressType.OVERLOADED, testHabitSnapshotB.getProgressType());
        assertTrue(testHabitSnapshotB.getUnit().equals("study sessions"));
    }
}
