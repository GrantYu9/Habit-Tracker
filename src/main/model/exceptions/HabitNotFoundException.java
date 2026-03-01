package model.exceptions;

public class HabitNotFoundException extends Exception {
    // EFFECTS: Instantiates the exception without a message
    public HabitNotFoundException() {

    }

    // EFFECTS: Instantiates the exception with a message
    public HabitNotFoundException(String message) {
        super(message);
    }
}
