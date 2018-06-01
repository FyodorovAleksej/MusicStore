package by.fyodorov.musicstore.connector;

import java.sql.SQLClientInfoException;

/**
 * class of connector client info exception
 */
public class ConnectorInfoException extends SQLClientInfoException {

    /**
     * create exception with own message and with top exception for track trace
     * @param message - own message of exception
     * @param e - top exception for track trace
     */
    public ConnectorInfoException(String message, SQLClientInfoException e) {
        super(message, e.getFailedProperties(), e);
    }
}
