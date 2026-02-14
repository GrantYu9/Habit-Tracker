package model.habit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
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
import model.organization.specialpages.AllHabitsPage;

public class TestHabitCycleManager {
    private List<Habit> whatShouldBeHabits;
    private List<HabitSnapshot> whatShouldBeHabitSnapshots;
    private LocalDate whatShouldBeDate;
    private Duration whatShouldBeDuration;

    private AllHabitsPage testAllHabitsPageRecent;
    private AllHabitsPage testAllHabitsPageLate;

    private LocalTime cycleTimeMidnight; // 00:00
    private LocalTime cycleTime2330; // 23:30
    private LocalDateTime lastTimeRecent;  // Fr Feb 13, 2026 at 13:00
    private LocalDate inDaMiddle; // Th Feb 12
    private LocalDateTime lastTimeLate; // We Feb 11, 2026 at 14:00
    private LocalDateTime sortOfRightNow; // Fr Feb 13, 2026 at 14:00
    private LocalDateTime targetForMidnight; // Sa Feb 14, 2026 at 00:00
    private LocalDateTime targetFor2330; // Fr Feb 13, 2026 at 23:00
    private LocalDate stepOverDate; // Sa Feb 14, 2026
    private LocalDateTime nextCycleTimeMidnight; // Su Feb 15, 2026, at 00:00

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
        lastTimeRecent = LocalDateTime.of(2026, 2, 13, 13, 0);
        inDaMiddle = LocalDate.of(2026, 2, 12);
        lastTimeLate = LocalDateTime.of(2026, 2, 11, 14, 0);
        sortOfRightNow = LocalDateTime.of(2026, 02, 13, 13, 0);
        targetForMidnight = LocalDateTime.of(2026, 2, 14, 0, 0);
        targetFor2330 = LocalDateTime.of(2026, 2, 13, 23, 0);
        stepOverDate = LocalDate.of(2026, 2, 14);

        testHabitMidnightRecent = new HabitIncrement(1, 0, 1, "Workout", null, cycleTimeMidnight,
            lastTimeRecent.toLocalDate(), testAllHabitsPageRecent, testHabitCycleManagerRecent);
        testHabit2330Recent = new HabitIncrement(1, 0, 1, "Workout", null, cycleTime2330, 
            lastTimeRecent.toLocalDate(), testAllHabitsPageRecent, testHabitCycleManagerRecent);
        testHabitMidnightLate = new HabitIncrement(1, 0, 1, "Workout", null, cycleTimeMidnight, 
            lastTimeLate.toLocalDate(), testAllHabitsPageLate, testHabitCycleManagerLate);
        testHabit2330Late = new HabitIncrement(1, 0, 1, "Workout", null, cycleTime2330, 
            lastTimeLate.toLocalDate(), testAllHabitsPageLate, testHabitCycleManagerLate);

        testHabitSnapshotRecentNoChange = new HabitSnapshot(0, 1, 0, 0, 0, 1, ProgressType.UNDERDONE, 
            lastTimeRecent.toLocalDate(), null);
        testHabitSnapshotRecentChange = new HabitSnapshot(1, 1, 0, 100, 0, 1, ProgressType.DONE, 
            lastTimeRecent.toLocalDate(), null);
        testHabitSnapshotLateFeb12 = new HabitSnapshot(0, 1, 0, 0, 0, 1, ProgressType.UNDERDONE, inDaMiddle, null);
        testHabitSnapshotLateFeb11NoChange = new HabitSnapshot(0, 1, 0, 0, 0, 1, ProgressType.UNDERDONE, 
            lastTimeLate.toLocalDate(), null);
        testHabitSnapshotLateFeb11Change = new HabitSnapshot(1, 1, 0, 100, 0, 1, ProgressType.DONE, 
            lastTimeLate.toLocalDate(), null);

        testAllHabitsPageRecent.getHabits().remove(testHabitMidnightLate);
        testAllHabitsPageRecent.getHabits().remove(testHabit2330Late);
        testHabitCycleManagerRecent = new HabitCycleManager(testAllHabitsPageRecent, lastTimeRecent);

        testAllHabitsPageLate.getHabits().remove(testHabitMidnightRecent);
        testAllHabitsPageLate.getHabits().remove(testHabit2330Recent);
        testHabitCycleManagerLate = new HabitCycleManager(testAllHabitsPageLate, lastTimeLate);
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

        spy.scheduleAllHabits();

        assertEquals(2, spy.scheduleHabitCallCount);
    }

    @Test
    void testCycleAllHabitsAtStartupRecentNoChange() {
        Habit actualMidnight = copyHabit(testHabitMidnightRecent);
        Habit actual2330 = copyHabit(testHabit2330Recent);

        testHabitCycleManagerRecent.cycleAllHabitsAtStartup();
        testHabitCycleManagerRecent.cycleHabitAtStartup(actualMidnight);
        testHabitCycleManagerRecent.cycleHabitAtStartup(actual2330);

        assertEquals(testHabitMidnightRecent, actualMidnight);
        assertEquals(testHabit2330Recent, actual2330);
    }

    @Test
    void testCycleAllHabitsAtStartupRecentMix() {
        testHabit2330Recent.progressByStepAmount();

        Habit actualMidnight = copyHabit(testHabitMidnightRecent);
        Habit actual2330 = copyHabit(testHabit2330Recent);

        testHabitCycleManagerRecent.cycleAllHabitsAtStartup();
        testHabitCycleManagerRecent.cycleHabitAtStartup(actualMidnight);
        testHabitCycleManagerRecent.cycleHabitAtStartup(actual2330);

        assertEquals(testHabitMidnightRecent, actualMidnight);
        assertEquals(testHabit2330Recent, actual2330);
    }

    @Test
    void testCycleAllHabitsAtStartupRecentChange() {
        testHabitMidnightRecent.progressByStepAmount();
        testHabit2330Recent.progressByStepAmount();
        
        Habit actualMidnight = copyHabit(testHabitMidnightRecent);
        Habit actual2330 = copyHabit(testHabit2330Recent);

        testHabitCycleManagerRecent.cycleAllHabitsAtStartup();
        testHabitCycleManagerRecent.cycleHabitAtStartup(actualMidnight);
        testHabitCycleManagerRecent.cycleHabitAtStartup(actual2330);

        assertEquals(testHabitMidnightRecent, actualMidnight);
        assertEquals(testHabit2330Recent, actual2330);
    }

    @Test
    void testCycleAllHabitsAtStartupLateNoChange() {
        Habit actualMidnight = copyHabit(testHabitMidnightLate);
        Habit actual2330 = copyHabit(testHabit2330Late);

        testHabitCycleManagerRecent.cycleAllHabitsAtStartup();
        testHabitCycleManagerRecent.cycleHabitAtStartup(actualMidnight);
        testHabitCycleManagerRecent.cycleHabitAtStartup(actual2330);

        assertEquals(testHabitMidnightLate, actualMidnight);
        assertEquals(testHabit2330Late, actual2330);
    }

    @Test
    void testCycleAllHabitsAtStartupLateMix() {
        testHabit2330Late.progressByStepAmount();

        Habit actualMidnight = copyHabit(testHabitMidnightLate);
        Habit actual2330 = copyHabit(testHabit2330Late);

        testHabitCycleManagerRecent.cycleAllHabitsAtStartup();
        testHabitCycleManagerRecent.cycleHabitAtStartup(actualMidnight);
        testHabitCycleManagerRecent.cycleHabitAtStartup(actual2330);

        assertEquals(testHabitMidnightLate, actualMidnight);
        assertEquals(testHabit2330Late, actual2330);
    }

    @Test
    void testCycleAllHabitsAtStartupLateChange() {
        testHabitMidnightLate.progressByStepAmount();
        testHabit2330Late.progressByStepAmount();

        Habit actualMidnight = copyHabit(testHabitMidnightLate);
        Habit actual2330 = copyHabit(testHabit2330Late);

        testHabitCycleManagerRecent.cycleAllHabitsAtStartup();
        testHabitCycleManagerRecent.cycleHabitAtStartup(actualMidnight);
        testHabitCycleManagerRecent.cycleHabitAtStartup(actual2330);

        assertEquals(testHabitMidnightLate, actualMidnight);
        assertEquals(testHabit2330Late, actual2330);
    }

    @Test
    void testScheduleHabit() {
        CountDownLatch latch = new CountDownLatch(1);

        // Spy class to look into the background thread; we put this here because latch has to be in scope
        HabitCycleManager spyTemp = new HabitCycleManager(testAllHabitsPageRecent, lastTimeRecent) {
            @Override
            public void cycleHabitWhileRunning(Habit habit) {
                super.cycleHabitWhileRunning(habit);
                latch.countDown();
            }
        };

        spyTemp.scheduleHabit(testHabitMidnightRecent, Duration.ofMillis(200));

        assertEquals(1, latch.getCount());

        try {
            assertFalse(latch.await(100, TimeUnit.MILLISECONDS));
            assertTrue(latch.await(300, TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            System.out.println("Suddenly interrupted");
            e.printStackTrace();
        }

        assertTrue(testHabitMidnightRecent.getNextCycleTime().isEqual(nextCycleTimeMidnight));
    }

    @Test
    void testCycleHabitAtStartupRecentNoChange() {
        Habit expected = copyHabit(testHabitMidnightRecent);
        Habit actual = copyHabit(testHabitMidnightRecent);
        
        testHabitCycleManagerRecent.cycleHabitAtStartup(actual);
        testHabitCycleManagerRecent.resetHabit(expected);
        testHabitCycleManagerRecent.updateHabitTimes(expected);

        assertEquals(expected, actual);
    }

    @Test
    void testCycleHabitAtStartupRecentChange() {
        testHabitMidnightRecent.progressByStepAmount();

        Habit expected = copyHabit(testHabitMidnightRecent);
        Habit actual = copyHabit(testHabitMidnightRecent);

        testHabitCycleManagerRecent.cycleHabitAtStartup(actual);
        testHabitCycleManagerRecent.resetHabit(expected);
        testHabitCycleManagerRecent.updateHabitTimes(expected);

        assertEquals(expected, actual);
    }

    @Test
    void testCycleHabitAtStartupLateNoChange() {
        Habit expected = copyHabit(testHabitMidnightLate);
        Habit actual = copyHabit(testHabitMidnightLate);

        testHabitCycleManagerLate.cycleHabitAtStartup(actual);
        testHabitCycleManagerLate.updateHabit(expected);
        testHabitCycleManagerRecent.updateHabitTimes(expected);

        assertEquals(expected, actual);
    }

    @Test
    void testCycleHabitAtStartupLateChange() {
        testHabitMidnightLate.progressByStepAmount();

        Habit expected = copyHabit(testHabitMidnightLate);
        Habit actual = copyHabit(testHabitMidnightLate);

        testHabitCycleManagerLate.cycleHabitAtStartup(actual);
        testHabitCycleManagerLate.updateHabit(expected);
        testHabitCycleManagerRecent.updateHabitTimes(expected);

        assertEquals(expected, actual);
    }

    @Test
    void testCycleHabitWhileRunningNoChange() {
        SpyHabitCycleManager spy = new SpyHabitCycleManager(testAllHabitsPageRecent, lastTimeRecent);
        
        Habit expected = copyHabit(testHabitMidnightRecent);
        Habit actual = copyHabit(testHabitMidnightRecent);
        
        spy.resetHabit(expected);
        spy.updateHabitTimes(expected);
        spy.cycleHabitWhileRunning(actual);
        assertEquals(expected, actual);
        assertEquals(1, spy.scheduleHabitCallCount);
    }

    @Test
    void testCycleHabitWhileRunningChange() {
        SpyHabitCycleManager spy = new SpyHabitCycleManager(testAllHabitsPageRecent, lastTimeRecent);

        testHabitMidnightRecent.progressByStepAmount();

        Habit expected = copyHabit(testHabitMidnightRecent);
        Habit actual = copyHabit(testHabitMidnightRecent);

        spy.resetHabit(expected);
        spy.updateHabitTimes(expected);
        spy.cycleHabitWhileRunning(actual);
        assertEquals(expected, actual);
        assertEquals(1, spy.scheduleHabitCallCount);
    }

    @Test
    void testResetHabitNoChange() {
        whatShouldBeHabitSnapshots.add(testHabitSnapshotRecentNoChange);
        testHabitCycleManagerRecent.resetHabit(testHabitMidnightRecent);
        assertEquals(whatShouldBeHabitSnapshots, testHabitMidnightRecent.getHistory());

        assertEquals(stepOverDate, testHabitMidnightLate.getCurrentDay());
        assertEquals(testHabitMidnightRecent.getStartingAmount(), testHabitMidnightRecent.getCurrentAmount());
        assertEquals(0, testHabitMidnightRecent.getOverloadAmount());
        assertEquals(0, testHabitMidnightRecent.getProgressPercentage());
        assertEquals(ProgressType.UNDERDONE, testHabitMidnightRecent.getProgressType());

        assertEquals(1, testHabitMidnightRecent.getGoal());
        assertEquals(1, testHabitMidnightRecent.getStepAmount());
        assertEquals("Workout", testHabitMidnightRecent.getTitle());
        assertNull(testHabitMidnightRecent.getUnit());
    }

    @Test
    void testResetHabitChange() {
        whatShouldBeHabitSnapshots.add(testHabitSnapshotRecentChange);
        testHabitMidnightRecent.progressByStepAmount();
        testHabitCycleManagerRecent.resetHabit(testHabitMidnightRecent);
        assertEquals(whatShouldBeHabitSnapshots, testHabitMidnightRecent.getHistory());

        assertEquals(stepOverDate, testHabitMidnightLate.getCurrentDay());
        assertEquals(testHabitMidnightRecent.getStartingAmount(), testHabitMidnightRecent.getCurrentAmount());
        assertEquals(0, testHabitMidnightRecent.getOverloadAmount());
        assertEquals(0, testHabitMidnightRecent.getProgressPercentage());
        assertEquals(ProgressType.UNDERDONE, testHabitMidnightRecent.getProgressType());

        assertEquals(1, testHabitMidnightRecent.getGoal());
        assertEquals(1, testHabitMidnightRecent.getStepAmount());
        assertEquals("Workout", testHabitMidnightRecent.getTitle());
        assertNull(testHabitMidnightRecent.getUnit());
    }

    @Test
    void testUpdateHabitNoChange() {
        whatShouldBeHabitSnapshots.add(testHabitSnapshotLateFeb11NoChange);
        whatShouldBeHabitSnapshots.add(testHabitSnapshotLateFeb12);
        testHabitCycleManagerLate.updateHabit(testHabitMidnightLate);
        assertEquals(whatShouldBeHabitSnapshots, testHabitMidnightLate.getHistory());

        whatShouldBeDate = sortOfRightNow.toLocalDate();
        assertEquals(whatShouldBeDate, testHabitMidnightLate.getCurrentDay());
        assertEquals(testHabitMidnightLate.getStartingAmount(), testHabitMidnightLate.getCurrentAmount());
        assertEquals(0, testHabitMidnightLate.getOverloadAmount());
        assertEquals(0, testHabitMidnightLate.getProgressPercentage());
        assertEquals(ProgressType.UNDERDONE, testHabitMidnightLate.getProgressType());

        assertEquals(1, testHabitMidnightLate.getGoal());
        assertEquals(1, testHabitMidnightLate.getStepAmount());
        assertEquals("Workout", testHabitMidnightLate.getTitle());
        assertNull(testHabitMidnightLate.getUnit());
    }

    @Test
    void testUpdateHabitChange() {
        testHabitMidnightLate.progressByStepAmount();

        whatShouldBeHabitSnapshots.add(testHabitSnapshotLateFeb11Change);
        whatShouldBeHabitSnapshots.add(testHabitSnapshotLateFeb12);
        testHabitCycleManagerLate.updateHabit(testHabitMidnightLate);
        assertEquals(whatShouldBeHabitSnapshots, testHabitMidnightLate.getHistory());

        whatShouldBeDate = sortOfRightNow.toLocalDate();
        assertEquals(whatShouldBeDate, testHabitMidnightLate.getCurrentDay());
        assertEquals(testHabitMidnightLate.getStartingAmount(), testHabitMidnightLate.getCurrentAmount());
        assertEquals(0, testHabitMidnightLate.getOverloadAmount());
        assertEquals(0, testHabitMidnightLate.getProgressPercentage());
        assertEquals(ProgressType.UNDERDONE, testHabitMidnightLate.getProgressType());

        assertEquals(1, testHabitMidnightLate.getGoal());
        assertEquals(1, testHabitMidnightLate.getStepAmount());
        assertEquals("Workout", testHabitMidnightLate.getTitle());
        assertNull(testHabitMidnightLate.getUnit());
    }

    @Test
    void testUpdateHabitTimesMidnight() {
        whatShouldBeDate = targetForMidnight.toLocalDate();

        testHabitCycleManagerRecent.updateHabitTimes(testHabitMidnightRecent);
        assertTrue(testHabitMidnightRecent.getNextCycleTime().isEqual(targetForMidnight));
        assertEquals(whatShouldBeDate, testHabitMidnightRecent.getCurrentDay());
    }

    @Test
    void testUpdateHabitTimes2330() {
        whatShouldBeDate = targetFor2330.toLocalDate();
        
        testHabitCycleManagerRecent.updateHabitTimes(testHabit2330Recent);
        assertTrue(testHabit2330Recent.getNextCycleTime().isEqual(targetFor2330));
        assertEquals(whatShouldBeDate, testHabit2330Recent.getCurrentDay());
    }

    @Test
    void testCalculateDelayMidnight() {
        whatShouldBeDuration = Duration.between(sortOfRightNow, targetForMidnight);

        assertEquals(whatShouldBeDuration, testHabitCycleManagerRecent.calculateDelay(testHabitMidnightRecent));
    }

    @Test
    void testCalculateDelay2330() {
        whatShouldBeDuration = Duration.between(sortOfRightNow, targetFor2330);

        assertEquals(whatShouldBeDuration, testHabitCycleManagerRecent.calculateDelay(testHabit2330Recent));
    }

    @Test
    void testCalculateNextCycleTimeMidnight() {
        assertTrue(testHabitCycleManagerRecent.calculateNextCycleTime(testHabitMidnightRecent)
            .isEqual(targetForMidnight));
    }

    @Test
    void testCalculateNextCycleTime2330() {
        assertTrue(testHabitCycleManagerRecent.calculateNextCycleTime(testHabit2330Recent)
            .isEqual(targetFor2330));
    }

    // EFFECTS: Outputs a copy of the habit
    public Habit copyHabit(Habit habit) {
        return new HabitIncrement(habit.getGoal(), habit.getStartingAmount(), habit.getStepAmount(), habit.getTitle(),
         habit.getUnit(), habit.getCycleTime(), habit.getCurrentDay(), testAllHabitsPageRecent, 
         testHabitCycleManagerRecent);
    }

    // Spy class to check if scheduleHabit is called
    public class SpyHabitCycleManager extends HabitCycleManager {
        private int scheduleHabitCallCount = 0;

        public SpyHabitCycleManager(AllHabitsPage allHabitsPage, LocalDateTime lastTime) {
            super(allHabitsPage, lastTime);
        }

        @Override
        public void scheduleHabit(Habit habit, Duration duration) {
            scheduleHabitCallCount++;
        }
    };
}
