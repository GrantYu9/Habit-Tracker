package model.organization.centralization;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.habit.Habit;
import model.habit.HabitCycleManager;
import model.habit.HabitIncrement;
import model.organization.centralization.AllHabitsPage;

@ExcludeFromJacocoGeneratedReport
public class TestAllHabitsPage {
    private AllHabitsPage testAllHabitsPage;

    private LocalDate localDate;
    private LocalTime localTime;
    private LocalDateTime localDateTime;

    private Habit testHabitA;
    private Habit testHabitB;

    private HabitCycleManager testHabitCycleManager;

    private List<Habit> whatShouldBeHabit;
    
    @BeforeEach
    void runBefore() {
        testAllHabitsPage = new AllHabitsPage();

        localDate = LocalDate.of(2026, 2, 13);
        localTime = LocalTime.of(23, 30);
        localDateTime = LocalDateTime.of(localDate, localTime);

        testHabitCycleManager = new HabitCycleManager(testAllHabitsPage, localDateTime);

        testHabitA = new HabitIncrement(1, 0, 1, "Workout", localTime, localDate, localDateTime, testHabitCycleManager);
        testHabitB = new HabitIncrement(1, 0, 1, "More working out", localTime, localDate, localDateTime, testHabitCycleManager);

        

        whatShouldBeHabit = new ArrayList<>();
    }

    @Test
    void testConstructor() {
        assertTrue(testAllHabitsPage.getHabits().isEmpty());
    }

    @Test
    void testAddToAllHabitsPage() {
        testAllHabitsPage.addToAllHabitsPage(testHabitA);
        whatShouldBeHabit.add(testHabitA);
        assertEquals(whatShouldBeHabit, testAllHabitsPage.getHabits());

        testAllHabitsPage.addToAllHabitsPage(testHabitB);
        whatShouldBeHabit.add(testHabitB);
        assertEquals(whatShouldBeHabit, testAllHabitsPage.getHabits());
    }
}
