package model.exceptions;

public class EmptyLastTimeFileException extends Exception {
    
    // EFFECTS: Instantiates the exception without a message
    public EmptyLastTimeFileException() {

    }

    // EFFECTS: Instantiates the exception with a message
    public EmptyLastTimeFileException(String message) {
        super(message);
    }
}
