package model.organization;

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
import model.organization.tree.Page;
import model.organization.tree.Page.Order;

public class TestPage {
    private LocalDate localDate;
    private LocalTime localTime;
    private LocalDateTime localDateTime;

    private Habit habitA;
    private Habit habitB;

    private AllHabitsPage allHabitsPage;
    private HabitCycleManager habitCycleManager;

    private Page page;

    private List<Habit> whatShouldBeHabit;

    @BeforeEach
    void runBefore() {
        localDate = LocalDate.of(2026, 2, 13);
        localTime = LocalTime.of(23, 30);
        localDateTime = LocalDateTime.of(localDate, localTime);

        allHabitsPage = new AllHabitsPage();
        habitCycleManager = new HabitCycleManager(allHabitsPage, localDateTime);

        habitA = new HabitIncrement(1, 0, 1, "Workout", localTime, localDate, localDateTime, habitCycleManager);
        habitB = new HabitIncrement(1, 0, 1, "Workout some more", localTime, localDate, localDateTime, habitCycleManager);

        page = new Page(" hmmm, Sure ");

        whatShouldBeHabit = new ArrayList<>();
    }

    @Test
    void testConstructor() {
        assertTrue(page.getTitle().equals("hmmm, Sure"));
        assertEquals(Order.ALPHABETICAL, page.getOrder());
        assertTrue(page.getHabits().isEmpty());

    }

    @Test
    void testAddHabitAlpha() {
        page.addHabit(habitB);
        whatShouldBeHabit.add(habitB);
        assertEquals(whatShouldBeHabit, page.getHabits());

        page.addHabit(habitA);
        whatShouldBeHabit.add(0, habitA);
        assertEquals(whatShouldBeHabit, page.getHabits());
    }

    @Test
    void testAddHabitManual() {
        page.setOrder(Order.MANUAL);
        
        page.addHabit(habitB);
        whatShouldBeHabit.add(habitB);
        assertEquals(whatShouldBeHabit, page.getHabits());

        page.addHabit(habitA);
        whatShouldBeHabit.add(habitA);
        assertEquals(whatShouldBeHabit, page.getHabits());
    }

    @Test
    void testSetOrder() {
        page.setOrder(Order.MANUAL);
        
        page.addHabit(habitB);
        whatShouldBeHabit.add(habitB);
        assertEquals(whatShouldBeHabit, page.getHabits());

        page.addHabit(habitA);
        whatShouldBeHabit.add(habitA);
        assertEquals(whatShouldBeHabit, page.getHabits());

        page.setOrder(Order.ALPHABETICAL);
        whatShouldBeHabit.remove(habitA);
        whatShouldBeHabit.add(0, habitA);
        assertEquals(whatShouldBeHabit, page.getHabits());

        page.setOrder(Order.MANUAL);
        assertEquals(whatShouldBeHabit, page.getHabits());
    }

    @Test
    void testSetTitle() {
        page.setTitle(" nuh  uh   uh ");
        assertTrue(page.getTitle().equals("nuh  uh   uh"));
    }
}
