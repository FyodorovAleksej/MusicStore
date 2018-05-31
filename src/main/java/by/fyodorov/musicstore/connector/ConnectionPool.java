package by.fyodorov.musicstore.connector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * singleton for getting connections with DataBase
 */
public class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private static ConnectionPool instance;
    private static Lock instanceLock = new ReentrantLock();
    private static AtomicBoolean isCreated = new AtomicBoolean(false);
    private ConnectionCreator connectionCreator;

    private ArrayDeque<Connection> connections;
    private Lock lock = new ReentrantLock();

    /**
     * creating connection pool with properties file
     * @param path - path to properties file with DB properties
     * @throws ConnectorException - when can't register DB driver
     */
    private ConnectionPool(String path) throws ConnectorException {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            throw new ConnectorException("can't register driver", e);
        }
        try {
            connectionCreator = new ConnectionCreator(path);
            connections = connectionCreator.createDeque();
        } catch (CreatorException e) {
            LOGGER.catching(e);
            throw new RuntimeException(e);
        }
    }

    /**
     * singleton
     * @param path - path to DB properties file. Used only once
     * @return - singleton of pool
     * @throws ConnectorException - when can't create connection pool
     */
    public static ConnectionPool getInstance(String path) throws ConnectorException {
        if (!isCreated.get()) {
            instanceLock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool(path);
                    isCreated.set(true);
                }
            } finally {
                instanceLock.unlock();
            }
        }
        return instance;
    }

    /**
     * release used connection. Package private
     * @param connection - used connection
     * @throws ConnectorException - when can't close connection
     */
    void releaseConnection(ProxyConnection connection) throws ConnectorException {
        LOGGER.debug("RELEASE CONNECTION");
        lock.lock();
        try {
            if (!connection.isClosed()) {
                connection.closeConnection();
            }
            ProxyConnection proxyConnection = new ProxyConnection(connectionCreator.create());
            connections.push(proxyConnection);
        } finally {
            lock.unlock();
        }
        LOGGER.debug("COUNT OF CONNECTIONS = " + connections.size());
    }

    /**
     * destroy connection pool
     * @throws ConnectorException - when can't close connections
     */
    public void destroy() throws ConnectorException {
        lock.lock();
        try {
            for (Connection connection : connections) {
                ((ProxyConnection) connection).closeConnection();
            }
        } finally {
            lock.unlock();
        }
        try {
            DriverManager.deregisterDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            throw new ConnectorException("can't deregister driver", e);
        }
    }

    /**
     * getting connection from connection pool for using
     * @return - connection for using
     */
    public Connection getConnection() {
        LOGGER.debug("GET CONNECTION");
        lock.lock();
        Connection connection = connections.pop();
        lock.unlock();
        LOGGER.debug("COUNT OF CONNECTIONS = " + connections.size());
        return connection;
    }

}
