package by.fyodorov.musicstore.connector;

/**
 * class of creator exception
 */
public class CreatorException extends Exception {

    /**
     * create exception with own message and top exception
     * @param message - own message of exception
     * @param e - top exception for track trace
     */
    public CreatorException(String message, Exception e) {
        super(message, e);
    }
}
