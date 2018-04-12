package by.fyodorov.musicstore.connector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Properties;

public class ConnectionCreator {
    private static final String URL = "jdbc:mysql://localhost:3306/musicstore";
    private static Logger LOGGER = LogManager.getLogger(ConnectionCreator.class);
    private static final String COUNT_KEY = "count";

    private Properties properties;

    ConnectionCreator(String path) throws CreatorException {
        properties = new Properties();
        try {
            LOGGER.debug("loading properties from = \"" + path + "\"");
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(path));
        }
        catch (FileNotFoundException e) {
            throw new CreatorException("can't find file = \"" + path + "\"", e);
        }
        catch (IOException e) {
            throw new CreatorException("can't read file = \"" + path + "\"", e);
        }
    }

    Connection create() throws ConnectorException {
        try {
            return DriverManager.getConnection(URL, properties);
        } catch (SQLException e) {
            throw new ConnectorException("can't getting connector", e);
        }
    }

    ArrayDeque<Connection> createDeque() throws CreatorException {
        int count = Integer.valueOf(properties.getProperty(COUNT_KEY));
        ArrayDeque<Connection> connections = new ArrayDeque<>(count);
        try {
            for (int i = 0; i < count; i++) {
                connections.push(new ProxyConnection(create()));
            }
        }
        catch (ConnectorException e) {
            throw new CreatorException("can't create connections deque", e);
        }
        return connections;
    }
}

