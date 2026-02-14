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

import model.habit.Habit;
import model.habit.HabitCycleManager;
import model.habit.HabitIncrement;
import model.organization.Tag;
import model.organization.centralization.AllHabitsPage;
import model.organization.centralization.AllTagPagesPage;

public class TestHomePage {
    private HomePage homePage;

    private LocalDate localDate;
    private LocalTime localTime;
    private LocalDateTime localDateTime;

    private Tag tagHome;
    private Habit habitA;
    private Habit habitB;

    private AllHabitsPage allHabitsPage;
    private HabitCycleManager habitCycleManager;
    private AllTagPagesPage allTagPagesPage;

    private List<Habit> whatShouldBeHabit;
    
    @BeforeEach
    void runBefore() {
        homePage = new HomePage();

        localDate = LocalDate.of(2026, 2, 13);
        localTime = LocalTime.of(23, 30);
        localDateTime = LocalDateTime.of(localDate, localTime);

        tagHome = new Tag("Home");
        habitA = new HabitIncrement(1, 0, 1, "Workout", localTime, localDate, allHabitsPage, habitCycleManager);
        habitB = new HabitIncrement(1, 0, 1, "Workout some more", localTime, localDate, allHabitsPage, habitCycleManager);

        allHabitsPage = new AllHabitsPage();
        habitCycleManager = new HabitCycleManager(allHabitsPage, localDateTime);

        habitA.addTagAndSortTags(tagHome, homePage, null, allTagPagesPage);
        habitB.addTagAndSortTags(tagHome, homePage, null, allTagPagesPage);

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
