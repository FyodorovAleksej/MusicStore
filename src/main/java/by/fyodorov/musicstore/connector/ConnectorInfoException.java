package by.fyodorov.musicstore.connector;

import java.sql.SQLClientInfoException;

public class ConnectorInfoException extends SQLClientInfoException {

    public ConnectorInfoException(String message, SQLClientInfoException e) {
        super(message, e.getFailedProperties(), e);
    }
}
