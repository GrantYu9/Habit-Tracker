package model.organization.specialpages;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
public class TestHomePage {
    private HomePage homePage;

    private LocalDate localDate;
    private LocalTime localTime;
    private LocalDateTime localDateTime;

    private Habit habitA;
    private Habit habitB;

    private AllHabitsPage allHabitsPage;
    private HabitCycleManager habitCycleManager;

    private List<Habit> whatShouldBeHabit;
    
    @BeforeEach
    void runBefore() {
        homePage = new HomePage();

        localDate = LocalDate.of(2026, 2, 13);
        localTime = LocalTime.of(23, 30);
        localDateTime = LocalDateTime.of(localDate, localTime);

        allHabitsPage = new AllHabitsPage();
        habitCycleManager = new HabitCycleManager(allHabitsPage, localDateTime);

        habitA = new HabitIncrement(1, 0, 1, "Workout", localTime, localDate, localDateTime, habitCycleManager);
        habitB = new HabitIncrement(1, 0, 1, "Workout some more", localTime, localDate, localDateTime, habitCycleManager);
        
        whatShouldBeHabit = new ArrayList<>();
    }

    @Test
    void testConstructor() {
        assertTrue(homePage.getTitle().equals("Home"));
        assertTrue(homePage.getHabits().isEmpty());
    }

    @Test
    void testAddToHomePage() {
        homePage.addToHomePage(habitA);
        whatShouldBeHabit.add(habitA);
        assertEquals(whatShouldBeHabit, homePage.getHabits());

        homePage.addToHomePage(habitB);
        whatShouldBeHabit.add(habitB);
        assertEquals(whatShouldBeHabit, homePage.getHabits());
    }
}
