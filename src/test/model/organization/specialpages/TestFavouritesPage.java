package model.organization.specialpages;

import static org.junit.Assert.assertEquals;
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
import model.organization.centralization.AllHabitsPage;

public class TestFavouritesPage {
    private FavouritesPage favouritesPage;

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
        favouritesPage = new FavouritesPage();

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
        assertTrue(favouritesPage.getTitle().equals("Favourites"));
        assertTrue(favouritesPage.getHabits().isEmpty());
    }

    @Test
    void testAddToFavouritesPage() {
        favouritesPage.addToFavouritesPage(habitA);
        whatShouldBeHabit.add(habitA);
        assertEquals(whatShouldBeHabit, favouritesPage.getHabits());

        favouritesPage.addToFavouritesPage(habitB);
        whatShouldBeHabit.add(habitB);
        assertEquals(whatShouldBeHabit, favouritesPage.getHabits());
    }
}
