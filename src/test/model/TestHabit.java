package model;

import model.Habit.Habit;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestHabit {
    Habit testHabitMain;
    
    @BeforeEach
    void runBefore() {
        testHabitMain = new Habit();
        // put in operands
    }

    @Test
    void sampleTest() {
        assertTrue(true);
    }
}
