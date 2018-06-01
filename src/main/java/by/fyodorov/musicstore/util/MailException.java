package by.fyodorov.musicstore.util;

/**
 * class of mail exception
 */
public class MailException extends Exception {

    /**
     * creating mail exception with own message and top exception
     * @param message - own message
     * @param e - top exception
     */
    public MailException(String message, Exception e) {
        super(message, e);
    }
}
