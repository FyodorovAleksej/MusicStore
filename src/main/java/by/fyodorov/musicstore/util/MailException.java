package by.fyodorov.musicstore.util;

public class MailException extends Exception {
    public MailException(String message) {
        super(message);
    }

    public MailException(String message, Exception e) {
        super(message, e);
    }
}
