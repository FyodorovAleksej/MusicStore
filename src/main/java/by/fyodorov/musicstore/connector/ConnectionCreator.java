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

/**
 * class for creating connections for connection pool
 */
public class ConnectionCreator {
    private static final String URL = "jdbc:mysql://localhost:3306/musicstore";
    private static final Logger LOGGER = LogManager.getLogger(ConnectionCreator.class);
    private static final String COUNT_KEY = "count";

    private Properties properties;

    /**
     * creating creator with path to properties file
     * @param path - path to properties file for DB
     * @throws CreatorException - when can't find or read properties file
     */
    ConnectionCreator(String path) throws CreatorException {
        properties = new Properties();
        try {
            LOGGER.debug("loading properties from = \"" + path + "\"");
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(path));
        } catch (FileNotFoundException e) {
            throw new CreatorException("can't find file = \"" + path + "\"", e);
        } catch (IOException e) {
            throw new CreatorException("can't read file = \"" + path + "\"", e);
        }
    }

    /**
     * create new connection
     * @return - new connection
     * @throws ConnectorException - when can't create connection
     */
    Connection create() throws ConnectorException {
        try {
            return DriverManager.getConnection(URL, properties);
        } catch (SQLException e) {
            throw new ConnectorException("can't getting connector", e);
        }
    }

    /**
     * creating deque of connections for connection pool
     * @return - created deque
     * @throws CreatorException - when can't create new connection
     */
    ArrayDeque<Connection> createDeque() throws CreatorException {
        int count = Integer.valueOf(properties.getProperty(COUNT_KEY));
        ArrayDeque<Connection> connections = new ArrayDeque<>(count);
        try {
            for (int i = 0; i < count; i++) {
                connections.push(new ProxyConnection(create()));
            }
        } catch (ConnectorException e) {
            throw new CreatorException("can't create connections deque", e);
        }
        return connections;
    }
}

