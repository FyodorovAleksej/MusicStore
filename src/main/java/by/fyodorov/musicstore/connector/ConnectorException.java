package by.fyodorov.musicstore.connector;

import java.sql.SQLException;

public class ConnectorException extends SQLException {

    public ConnectorException(String message) {
        super(message);
    }

    public ConnectorException(String message, SQLException e) {
        super(message, e);
    }
}
