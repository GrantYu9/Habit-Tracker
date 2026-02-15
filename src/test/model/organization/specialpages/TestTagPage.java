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
import model.organization.Tag;
import model.organization.centralization.AllHabitsPage;
import model.organization.centralization.AllTagPagesPage;

public class TestTagPage {
    private TagPage tagPageA;

    private LocalDate localDate;
    private LocalTime localTime;
    private LocalDateTime localDateTime;

    private Tag tagA;

    private Habit habitA;
    private Habit habitB;

    private AllTagPagesPage allTagPages;
    private AllHabitsPage allHabitsPage;
    private HabitCycleManager habitCycleManager;

    private List<TagPage> whatShouldBeTagPage;
    private List<Habit> whatShouldBeHabit;

    @BeforeEach
    void runBeforeEach() {
        tagA = new Tag("tagA");

        localDate = LocalDate.of(2026, 2, 13);
        localTime = LocalTime.of(23, 30);
        localDateTime = LocalDateTime.of(localDate, localTime);

        habitA = new HabitIncrement(1, 0, 1, "Workout", localTime, localDate, allHabitsPage, habitCycleManager);
        habitB = new HabitIncrement(1, 0, 1, "More working out", localTime, localDate, allHabitsPage, habitCycleManager);

        allTagPages = new AllTagPagesPage();
        allHabitsPage = new AllHabitsPage();
        habitCycleManager = new HabitCycleManager(allHabitsPage, localDateTime);

        whatShouldBeTagPage = new ArrayList<>();
        whatShouldBeHabit = new ArrayList<>();

        tagPageA = new TagPage(tagA, allTagPages);
    }

    @Test
    void testConstructor() {
        whatShouldBeTagPage.add(tagPageA);

        assertTrue(tagPageA.getTitle().equals(tagA.getTitle()));
        assertTrue(tagPageA.getTag().equals(tagA));
        assertTrue(tagPageA.getHabits().isEmpty());
        assertEquals(whatShouldBeTagPage, allTagPages.getTagPages());
    }

    @Test
    void testAddToTagPage() {
        tagPageA.addToTagPage(habitA);
        whatShouldBeHabit.add(habitA);
        assertEquals(whatShouldBeHabit, tagPageA.getHabits());

        tagPageA.addToTagPage(habitB);
        whatShouldBeHabit.add(habitB);
        assertEquals(whatShouldBeHabit, tagPageA.getHabits());
    }
}
