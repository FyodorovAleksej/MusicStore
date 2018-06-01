package by.fyodorov.musicstore.connector;

import java.sql.SQLException;

/**
 * class of connection exception
 */
public class ConnectorException extends SQLException {

    /**
     * create exception with own message
     * @param message - message of exception
     */
    public ConnectorException(String message) {
        super(message);
    }

    /**
     * create exception with top exception for track trace
     * @param message - message of exception
     * @param e - top exception for track trace
     */
    public ConnectorException(String message, SQLException e) {
        super(message, e);
    }
}
