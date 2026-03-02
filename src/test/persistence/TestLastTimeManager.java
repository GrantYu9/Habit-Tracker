package persistence;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.exceptions.EmptyLastTimeFileException;

@ExcludeFromJacocoGeneratedReport
public class TestLastTimeManager {
    private String destinationEmpty;
    private String destinationGeneralRead;
    private String destinationGeneralWrite;

    private LocalDateTime localDateTimeA;
    private LocalDateTime localDateTimeB;

    private LastTimeManager lastTimeManagerEmpty;
    private LastTimeManager lastTimeManagerGeneralRead;
    private LastTimeManager lastTimeManagerGeneralWrite;

    @BeforeEach
    public void runBeforeEach() {
        destinationEmpty = "./data/testing/TestLastTimeManagerEmpty.json";
        destinationGeneralRead = "./data/testing/TestLastTimeManagerGeneralRead.json";
        destinationGeneralWrite = "./data/testing/TestLastTimeManagerGeneralWrite.json";

        localDateTimeA = LocalDateTime.of(LocalDate.of(2026, 2, 28), LocalTime.MIN);
        localDateTimeB = LocalDateTime.of(LocalDate.of(2026, 2, 27), LocalTime.MIN);

        lastTimeManagerEmpty = new LastTimeManager(destinationEmpty);
        lastTimeManagerGeneralRead = new LastTimeManager(destinationGeneralRead);
        lastTimeManagerGeneralWrite = new LastTimeManager(destinationGeneralWrite);
    }

    @Test
    public void testConstructor() {
        assertTrue(lastTimeManagerEmpty.getDestination().equals(destinationEmpty));
    }

    @Test
    public void testBrokenDependencies() {
        LastTimeManager lastTimeManagerBroken = new LastTimeManager("./data/broken.json");

        try {
            lastTimeManagerBroken.readFromFile();
            lastTimeManagerBroken.writeToFile(localDateTimeA);
            fail("Did not throw IOException");
        } catch (IOException e) {
            // pass
        } catch (EmptyLastTimeFileException e) {
            fail("Unexpected EmptyLastTimeFileException");
        }
    }

    @Test
    public void testReadFromFileNothing() {
        try {
            lastTimeManagerEmpty.readFromFile();
            fail("Did not throw EmptyLastTimeFileException");
        } catch (EmptyLastTimeFileException e) {
            // pass
        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }

    @Test
    public void testReadFromFileGeneral() {
        try {
            assertTrue(lastTimeManagerGeneralRead.readFromFile().equals(localDateTimeA));
        } catch (IOException e) {
            fail("Unexpected IOException");
        } catch (EmptyLastTimeFileException e) {
            fail("Unexpected EmptyLastTimeFileException");
        }
    }

    @Test
    public void testWriteToFile() {
        try {
            lastTimeManagerGeneralWrite.writeToFile(localDateTimeB);
            assertTrue(lastTimeManagerGeneralWrite.readFromFile().equals(localDateTimeB));
        } catch (IOException e) {
            fail("Unexpected IOException");
        } catch (EmptyLastTimeFileException e) {
            fail("Unexpected EmptyLastTimeFileException");
        }
    }
}
