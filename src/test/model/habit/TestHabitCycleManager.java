package model.habit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.habit.Habit.ProgressType;
import model.organization.centralization.AllHabitsPage;

public class TestHabitCycleManager {
    private List<Habit> whatShouldBeHabits;
    private List<HabitSnapshot> whatShouldBeHabitSnapshots;
    private LocalDate whatShouldBeDate;
    private Duration whatShouldBeDuration;

    private AllHabitsPage testAllHabitsPageRecent;
    private AllHabitsPage testAllHabitsPageLate;

    private LocalTime cycleTimeMidnight; // 00:00
    private LocalTime cycleTime2330; // 23:30
    private LocalTime marker1300; // 13:00
    private LocalTime marker1400; // 14:00
    private LocalDate today; // Today
    private LocalDate yesterday; // Yesterday
    private LocalDate tomorrow; // Tomorrow
    private LocalDate twoDaysAgo; // 2 days ago

    private LocalDateTime lastTimeRecent;  // Today at 13:00
    private LocalDate inDaMiddle; // Yesterday
    private LocalDateTime lastTimeLate; // Today - 2 days at 14:00
    private LocalDateTime rightNow; // Right now
    private LocalDateTime targetForMidnight; // Tomorrow at 00:00
    private LocalDateTime targetFor2330; // Today at 23:30
    private LocalDate stepOverDate; // Tomorrow

    private Habit testHabitMidnightRecent;
    private Habit testHabit2330Recent;
    private Habit testHabitMidnightLate;
    private Habit testHabit2330Late;

    private HabitSnapshot testHabitSnapshotRecentNoChange;
    private HabitSnapshot testHabitSnapshotRecentChange;
    private HabitSnapshot testHabitSnapshotLateFeb12;
    private HabitSnapshot testHabitSnapshotLateFeb11NoChange;
    private HabitSnapshot testHabitSnapshotLateFeb11Change;

    private HabitCycleManager testHabitCycleManagerRecent;
    private HabitCycleManager testHabitCycleManagerLate;

    @BeforeEach
    void beforeEach() {
        whatShouldBeHabits = new ArrayList<>();
        whatShouldBeHabitSnapshots = new ArrayList<>();

        testAllHabitsPageRecent = new AllHabitsPage();
        testAllHabitsPageLate = new AllHabitsPage();

        cycleTimeMidnight = LocalTime.of(0, 0);
        cycleTime2330 = LocalTime.of(23, 30);
        marker1300 = LocalTime.of(13, 0);
        marker1400 = LocalTime.of(14, 0);
        today = LocalDate.now();
        yesterday = today.minusDays(1);
        tomorrow = today.plusDays(1);
        twoDaysAgo = yesterday.minusDays(1);

        lastTimeRecent = LocalDateTime.of(today, marker1300);
        inDaMiddle = yesterday;
        lastTimeLate = LocalDateTime.of(twoDaysAgo, marker1400);
        rightNow = LocalDateTime.now();
        targetForMidnight = LocalDateTime.of(tomorrow, cycleTimeMidnight);
        targetFor2330 = LocalDateTime.of(today, cycleTime2330);
        stepOverDate = tomorrow;

        testHabitCycleManagerRecent = new HabitCycleManager(testAllHabitsPageRecent, lastTimeRecent);
        testHabitCycleManagerLate = new HabitCycleManager(testAllHabitsPageLate, lastTimeLate);

        testHabitMidnightRecent = new HabitIncrement(1, 0, 1, "Workout", cycleTimeMidnight,
            lastTimeRecent.toLocalDate(), testHabitCycleManagerRecent);
        testHabit2330Recent = new HabitIncrement(1, 0, 1, "Workout", cycleTime2330, 
            lastTimeRecent.toLocalDate(), testHabitCycleManagerRecent);
        testHabitMidnightLate = new HabitIncrement(1, 0, 1, "Workout", cycleTimeMidnight, 
            lastTimeLate.toLocalDate(), testHabitCycleManagerLate);
        testHabit2330Late = new HabitIncrement(1, 0, 1, "Workout", cycleTime2330, 
            lastTimeLate.toLocalDate(), testHabitCycleManagerLate);

        testAllHabitsPageRecent.addToAllHabitsPage(testHabitMidnightRecent);
        testAllHabitsPageRecent.addToAllHabitsPage(testHabit2330Recent);

        testAllHabitsPageLate.addToAllHabitsPage(testHabitMidnightLate);
        testAllHabitsPageLate.addToAllHabitsPage(testHabit2330Late);

        testHabitSnapshotRecentNoChange = new HabitSnapshot(0, 1, 0, 0, 0, 1, ProgressType.UNDERDONE, 
            lastTimeRecent.toLocalDate(), "");
        testHabitSnapshotRecentChange = new HabitSnapshot(1, 1, 0, 100, 0, 1, ProgressType.DONE, 
            lastTimeRecent.toLocalDate(), "");
        testHabitSnapshotLateFeb12 = new HabitSnapshot(0, 1, 0, 0, 0, 1, ProgressType.UNDERDONE, inDaMiddle, "");
        testHabitSnapshotLateFeb11NoChange = new HabitSnapshot(0, 1, 0, 0, 0, 1, ProgressType.UNDERDONE, 
            lastTimeLate.toLocalDate(), "");
        testHabitSnapshotLateFeb11Change = new HabitSnapshot(1, 1, 0, 100, 0, 1, ProgressType.DONE, 
            lastTimeLate.toLocalDate(), "");
    }

    @Test
    void testConstructorRecent() {
        whatShouldBeHabits.add(testHabitMidnightRecent);
        whatShouldBeHabits.add(testHabit2330Recent);
        assertEquals(whatShouldBeHabits, testHabitCycleManagerRecent.getHabits());
        assertTrue(testHabitCycleManagerRecent.getLastTime().isEqual(lastTimeRecent));
    }

    @Test
    void testConstructorLate() {
        whatShouldBeHabits.add(testHabitMidnightLate);
        whatShouldBeHabits.add(testHabit2330Late);
        assertEquals(whatShouldBeHabits, testHabitCycleManagerLate.getHabits());
        assertTrue(testHabitCycleManagerLate.getLastTime().isEqual(lastTimeLate));
    }

    @Test
    void testScheduleAllHabits() {
        SpyHabitCycleManager spy = new SpyHabitCycleManager(testAllHabitsPageRecent, lastTimeRecent);

        spy.scheduleAllHabits(rightNow);

        assertEquals(2, spy.scheduleHabitCallCount);
    }

    @Test
    void testCycleAllHabitsAtStartupRecentNoChange() {
        Habit actualMidnight = copyHabit(testHabitMidnightRecent);
        Habit actual2330 = copyHabit(testHabit2330Recent);

        testHabitCycleManagerRecent.cycleAllHabitsAtStartup(rightNow);
        testHabitCycleManagerRecent.cycleHabitAtStartup(actualMidnight, rightNow);
        testHabitCycleManagerRecent.cycleHabitAtStartup(actual2330, rightNow);

        assertTrue(testHabitMidnightRecent.equals(actualMidnight));
        assertTrue(testHabit2330Recent.equals(actual2330));
    }

    @Test
    void testCycleAllHabitsAtStartupRecentMix() {
        testHabit2330Recent.progressByStepAmount();

        Habit actualMidnight = copyHabit(testHabitMidnightRecent);
        Habit actual2330 = copyHabit(testHabit2330Recent);

        testHabitCycleManagerRecent.cycleAllHabitsAtStartup(rightNow);
        testHabitCycleManagerRecent.cycleHabitAtStartup(actualMidnight, rightNow);
        testHabitCycleManagerRecent.cycleHabitAtStartup(actual2330, rightNow);

        assertTrue(testHabitMidnightRecent.equals(actualMidnight));
        assertTrue(testHabit2330Recent.equals(actual2330));
    }

    @Test
    void testCycleAllHabitsAtStartupRecentChange() {
        testHabitMidnightRecent.progressByStepAmount();
        testHabit2330Recent.progressByStepAmount();
        
        Habit actualMidnight = copyHabit(testHabitMidnightRecent);
        Habit actual2330 = copyHabit(testHabit2330Recent);

        testHabitCycleManagerRecent.cycleAllHabitsAtStartup(rightNow);
        testHabitCycleManagerRecent.cycleHabitAtStartup(actualMidnight, rightNow);
        testHabitCycleManagerRecent.cycleHabitAtStartup(actual2330, rightNow);

        assertTrue(testHabitMidnightRecent.equals(actualMidnight));
        assertTrue(testHabit2330Recent.equals(actual2330));
    }

    @Test
    void testCycleAllHabitsAtStartupLateNoChange() {
        Habit actualMidnight = copyHabit(testHabitMidnightLate);
        Habit actual2330 = copyHabit(testHabit2330Late);

        testHabitCycleManagerLate.cycleAllHabitsAtStartup(rightNow);
        testHabitCycleManagerLate.cycleHabitAtStartup(actualMidnight, rightNow);
        testHabitCycleManagerLate.cycleHabitAtStartup(actual2330, rightNow);

        assertTrue(testHabitMidnightLate.equals(actualMidnight));
        assertTrue(testHabit2330Late.equals(actual2330));
    }

    @Test
    void testCycleAllHabitsAtStartupLateMix() {
        testHabit2330Late.progressByStepAmount();

        Habit actualMidnight = copyHabit(testHabitMidnightLate);
        Habit actual2330 = copyHabit(testHabit2330Late);

        testHabitCycleManagerLate.cycleAllHabitsAtStartup(rightNow);
        testHabitCycleManagerLate.cycleHabitAtStartup(actualMidnight, rightNow);
        testHabitCycleManagerLate.cycleHabitAtStartup(actual2330, rightNow);

        assertTrue(testHabitMidnightLate.equals(actualMidnight));
        assertTrue(testHabit2330Late.equals(actual2330));
    }

    @Test
    void testCycleAllHabitsAtStartupLateChange() {
        testHabitMidnightLate.progressByStepAmount();
        testHabit2330Late.progressByStepAmount();

        Habit actualMidnight = copyHabit(testHabitMidnightLate);
        Habit actual2330 = copyHabit(testHabit2330Late);

        testHabitCycleManagerLate.cycleAllHabitsAtStartup(rightNow);
        testHabitCycleManagerLate.cycleHabitAtStartup(actualMidnight, rightNow);
        testHabitCycleManagerLate.cycleHabitAtStartup(actual2330, rightNow);

        assertTrue(testHabitMidnightLate.equals(actualMidnight));
        assertTrue(testHabit2330Late.equals(actual2330));
    }

    @Test
    void testScheduleHabit() {
        CountDownLatch latch = new CountDownLatch(1);

        // Spy class to look into the background thread; we put this here because latch has to be in scope
        HabitCycleManager spyTemp = new HabitCycleManager(testAllHabitsPageRecent, lastTimeRecent) {
            @Override
            public void cycleHabitWhileRunning(Habit habit, LocalDateTime marker) {
                super.cycleHabitWhileRunning(habit, marker);
                latch.countDown();
            }
        };

        spyTemp.scheduleHabit(testHabitMidnightRecent, Duration.ofSeconds(2), rightNow);

        assertEquals(1, latch.getCount());

        try {
            assertFalse(latch.await(1, TimeUnit.SECONDS));
            assertTrue(latch.await(3, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            System.out.println("Suddenly interrupted");
            e.printStackTrace();
        }

        assertTrue(testHabitMidnightRecent.getNextCycleTime().isEqual(targetForMidnight));
    }

    @Test
    void testCycleHabitAtStartupRecentNoChange() {
        Habit expected = copyHabit(testHabitMidnightRecent);
        Habit actual = copyHabit(testHabitMidnightRecent);
        
        testHabitCycleManagerRecent.cycleHabitAtStartup(actual, rightNow);
        testHabitCycleManagerRecent.resetHabit(expected);
        testHabitCycleManagerRecent.updateHabitTimes(expected, rightNow);

        assertTrue(expected.equals(actual));
    }

    @Test
    void testCycleHabitAtStartupRecentChange() {
        testHabitMidnightRecent.progressByStepAmount();

        Habit expected = copyHabit(testHabitMidnightRecent);
        Habit actual = copyHabit(testHabitMidnightRecent);

        testHabitCycleManagerRecent.cycleHabitAtStartup(actual, rightNow);
        testHabitCycleManagerRecent.resetHabit(expected);
        testHabitCycleManagerRecent.updateHabitTimes(expected, rightNow);

        assertTrue(expected.equals(actual));
    }

    @Test
    void testCycleHabitAtStartupLateNoChange() {
        Habit expected = copyHabit(testHabitMidnightLate);
        Habit actual = copyHabit(testHabitMidnightLate);

        testHabitCycleManagerLate.cycleHabitAtStartup(actual, rightNow);
        testHabitCycleManagerLate.updateHabit(expected, rightNow);
        testHabitCycleManagerRecent.updateHabitTimes(expected, rightNow);

        assertTrue(expected.equals(actual));
    }

    @Test
    void testCycleHabitAtStartupLateChange() {
        testHabitMidnightLate.progressByStepAmount();

        Habit expected = copyHabit(testHabitMidnightLate);
        Habit actual = copyHabit(testHabitMidnightLate);

        testHabitCycleManagerLate.cycleHabitAtStartup(actual, rightNow);
        testHabitCycleManagerLate.updateHabit(expected, rightNow);
        testHabitCycleManagerRecent.updateHabitTimes(expected, rightNow);

        assertTrue(expected.equals(actual));
    }

    @Test
    void testCycleHabitWhileRunningNoChange() {
        SpyHabitCycleManager spy = new SpyHabitCycleManager(testAllHabitsPageRecent, lastTimeRecent);
        
        Habit expected = copyHabit(testHabitMidnightRecent);
        Habit actual = copyHabit(testHabitMidnightRecent);
        
        spy.resetHabit(expected);
        spy.updateHabitTimes(expected, rightNow);
        spy.cycleHabitWhileRunning(actual, rightNow);

        assertTrue(expected.equals(actual));
        assertEquals(1, spy.scheduleHabitCallCount);
    }

    @Test
    void testCycleHabitWhileRunningChange() {
        SpyHabitCycleManager spy = new SpyHabitCycleManager(testAllHabitsPageRecent, lastTimeRecent);

        testHabitMidnightRecent.progressByStepAmount();

        Habit expected = copyHabit(testHabitMidnightRecent);
        Habit actual = copyHabit(testHabitMidnightRecent);

        spy.resetHabit(expected);
        spy.updateHabitTimes(expected, rightNow);
        spy.cycleHabitWhileRunning(actual, rightNow);

        assertTrue(expected.equals(actual));
        assertEquals(1, spy.scheduleHabitCallCount);
    }

    @Test
    void testResetHabitNoChange() {
        whatShouldBeHabitSnapshots.add(testHabitSnapshotRecentNoChange);
        testHabitCycleManagerRecent.resetHabit(testHabitMidnightRecent);
        assertEquals(whatShouldBeHabitSnapshots, testHabitMidnightRecent.getHistory());

        assertEquals(stepOverDate, testHabitMidnightRecent.getCurrentDay());
        assertEquals(testHabitMidnightRecent.getStartingAmount(), testHabitMidnightRecent.getCurrentAmount());
        assertEquals(0, testHabitMidnightRecent.getOverloadAmount());
        assertEquals(0, testHabitMidnightRecent.getProgressPercentage());
        assertEquals(ProgressType.UNDERDONE, testHabitMidnightRecent.getProgressType());

        assertEquals(1, testHabitMidnightRecent.getGoal());
        assertEquals(1, testHabitMidnightRecent.getStepAmount());
        assertEquals("Workout", testHabitMidnightRecent.getTitle());
        assertTrue(testHabitMidnightRecent.getUnit().equals(""));
    }

    @Test
    void testResetHabitChange() {
        whatShouldBeHabitSnapshots.add(testHabitSnapshotRecentChange);
        testHabitMidnightRecent.progressByStepAmount();
        testHabitCycleManagerRecent.resetHabit(testHabitMidnightRecent);
        assertEquals(whatShouldBeHabitSnapshots, testHabitMidnightRecent.getHistory());

        assertEquals(stepOverDate, testHabitMidnightRecent.getCurrentDay());
        assertEquals(testHabitMidnightRecent.getStartingAmount(), testHabitMidnightRecent.getCurrentAmount());
        assertEquals(0, testHabitMidnightRecent.getOverloadAmount());
        assertEquals(0, testHabitMidnightRecent.getProgressPercentage());
        assertEquals(ProgressType.UNDERDONE, testHabitMidnightRecent.getProgressType());

        assertEquals(1, testHabitMidnightRecent.getGoal());
        assertEquals(1, testHabitMidnightRecent.getStepAmount());
        assertEquals("Workout", testHabitMidnightRecent.getTitle());
        assertTrue(testHabitMidnightRecent.getUnit().equals(""));
    }

    @Test
    void testUpdateHabitNoChange() {
        whatShouldBeHabitSnapshots.add(testHabitSnapshotLateFeb11NoChange);
        whatShouldBeHabitSnapshots.add(testHabitSnapshotLateFeb12);
        testHabitCycleManagerLate.updateHabit(testHabitMidnightLate, rightNow);
        assertEquals(whatShouldBeHabitSnapshots, testHabitMidnightLate.getHistory());

        whatShouldBeDate = rightNow.toLocalDate();
        assertEquals(whatShouldBeDate, testHabitMidnightLate.getCurrentDay());
        assertEquals(testHabitMidnightLate.getStartingAmount(), testHabitMidnightLate.getCurrentAmount());
        assertEquals(0, testHabitMidnightLate.getOverloadAmount());
        assertEquals(0, testHabitMidnightLate.getProgressPercentage());
        assertEquals(ProgressType.UNDERDONE, testHabitMidnightLate.getProgressType());

        assertEquals(1, testHabitMidnightLate.getGoal());
        assertEquals(1, testHabitMidnightLate.getStepAmount());
        assertEquals("Workout", testHabitMidnightLate.getTitle());
        assertTrue(testHabitMidnightLate.getUnit().equals(""));
    }

    @Test
    void testUpdateHabitChange() {
        testHabitMidnightLate.progressByStepAmount();

        whatShouldBeHabitSnapshots.add(testHabitSnapshotLateFeb11Change);
        whatShouldBeHabitSnapshots.add(testHabitSnapshotLateFeb12);
        testHabitCycleManagerLate.updateHabit(testHabitMidnightLate, rightNow);
        assertEquals(whatShouldBeHabitSnapshots, testHabitMidnightLate.getHistory());

        whatShouldBeDate = rightNow.toLocalDate();
        assertEquals(whatShouldBeDate, testHabitMidnightLate.getCurrentDay());
        assertEquals(testHabitMidnightLate.getStartingAmount(), testHabitMidnightLate.getCurrentAmount());
        assertEquals(0, testHabitMidnightLate.getOverloadAmount());
        assertEquals(0, testHabitMidnightLate.getProgressPercentage());
        assertEquals(ProgressType.UNDERDONE, testHabitMidnightLate.getProgressType());

        assertEquals(1, testHabitMidnightLate.getGoal());
        assertEquals(1, testHabitMidnightLate.getStepAmount());
        assertEquals("Workout", testHabitMidnightLate.getTitle());
        assertTrue(testHabitMidnightLate.getUnit().equals(""));
    }

    @Test
    void testUpdateHabitTimesMidnight() {
        whatShouldBeDate = rightNow.toLocalDate();

        testHabitCycleManagerRecent.updateHabitTimes(testHabitMidnightRecent, rightNow);
        assertTrue(testHabitMidnightRecent.getNextCycleTime().isEqual(targetForMidnight));
        assertEquals(whatShouldBeDate, testHabitMidnightRecent.getCurrentDay());
    }

    @Test
    void testUpdateHabitTimes2330() {
        whatShouldBeDate = rightNow.toLocalDate();
        
        testHabitCycleManagerRecent.updateHabitTimes(testHabit2330Recent, rightNow);
        assertTrue(testHabit2330Recent.getNextCycleTime().isEqual(targetFor2330));
        assertEquals(whatShouldBeDate, testHabit2330Recent.getCurrentDay());
    }

    @Test
    void testCalculateDelayMidnight() {
        testHabitCycleManagerRecent.updateHabitTimes(testHabitMidnightRecent, rightNow);
        whatShouldBeDuration = Duration.between(rightNow, targetForMidnight);

        assertEquals(whatShouldBeDuration, testHabitCycleManagerRecent.calculateDelay(testHabitMidnightRecent, rightNow));
    }

    @Test
    void testCalculateDelay2330() {
        testHabitCycleManagerRecent.updateHabitTimes(testHabit2330Recent, rightNow);

        whatShouldBeDuration = Duration.between(rightNow, targetFor2330);

        assertEquals(whatShouldBeDuration, testHabitCycleManagerRecent.calculateDelay(testHabit2330Recent, rightNow));
    }

    @Test
    void testCalculateNextCycleTimeMidnight() {
        assertTrue(testHabitCycleManagerRecent.calculateNextCycleTime(testHabitMidnightRecent, rightNow)
            .isEqual(targetForMidnight));
    }

    @Test
    void testCalculateNextCycleTime2330() {
        assertTrue(testHabitCycleManagerRecent.calculateNextCycleTime(testHabit2330Recent, rightNow)
            .isEqual(targetFor2330));
    }

    // EFFECTS: Outputs a copy of the habit
    public Habit copyHabit(Habit habit) {
        return new HabitIncrement(habit.getGoal(), habit.getStartingAmount(), habit.getStepAmount(), habit.getTitle(),
            habit.getCycleTime(), habit.getCurrentDay(), 
            testHabitCycleManagerRecent);
    }

    // Spy class to check if scheduleHabit is called
    public class SpyHabitCycleManager extends HabitCycleManager {
        private int scheduleHabitCallCount = 0;

        public SpyHabitCycleManager(AllHabitsPage allHabitsPage, LocalDateTime lastTime) {
            super(allHabitsPage, lastTime);
        }

        @Override
        public void scheduleHabit(Habit habit, Duration duration, LocalDateTime marker) {
            scheduleHabitCallCount++;
        }
    };
}
