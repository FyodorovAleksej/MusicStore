package by.fyodorov.musicstore.command;

/**
 * class of Exception of command
 */
public class CommandException extends Exception {

    /**
     * create exception with own message
     * @param message - message of exception
     */
    public CommandException(String message) {
        super(message);
    }

    /**
     * create exception with message and top exception for stackTrace
     * @param message - message of exception
     * @param e - top exception for getting stackTrace
     */
    public CommandException(String message, Exception e) {
        super(message, e);
    }
}
