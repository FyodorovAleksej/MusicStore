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

    private ConnectionPool(String path) throws ConnectorException {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        }
        catch (SQLException e) {
            throw new ConnectorException("can't register driver", e);
        }
        try {
            connectionCreator = new ConnectionCreator(path);
            connections = connectionCreator.createDeque();
        }
        catch (CreatorException e) {
            LOGGER.catching(e);
            throw new RuntimeException(e);
        }
    }

    public static ConnectionPool getInstance(String path) throws ConnectorException {
        if (!isCreated.get()) {
            instanceLock.lock();
            if (instance == null) {
                instance = new ConnectionPool(path);
                isCreated.set(true);
            }
            instanceLock.unlock();
        }
        return instance;
    }

    void releaseConnection(ProxyConnection connection) throws ConnectorException {
        LOGGER.debug("RELEASE CONNECTION");
        lock.lock();
        if (!connection.isClosed()) {
            connection.closeConnection();
        }
        ProxyConnection proxyConnection = new ProxyConnection(connectionCreator.create());
        connections.push(proxyConnection);
        lock.unlock();
        LOGGER.debug("COUNT OF CONNECTIONS = " + connections.size());
    }

    public void destroy() throws ConnectorException {
        lock.lock();
        for (Connection connection : connections) {
            ((ProxyConnection)connection).closeConnection();
        }
        lock.unlock();
        try {
            DriverManager.deregisterDriver(new com.mysql.cj.jdbc.Driver());
        }
        catch (SQLException e) {
            throw new ConnectorException("can't deregister driver", e);
        }
    }


    public Connection getConnection() {
        LOGGER.debug("GET CONNECTION");
        lock.lock();
        Connection connection = connections.pop();
        lock.unlock();
        LOGGER.debug("COUNT OF CONNECTIONS = " + connections.size());
        return connection;
    }

}
