package by.fyodorov.musicstore.connector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class SqlUtil {
    private static Logger LOGGER = LogManager.getLogger(SqlUtil.class);
    private Connection connection;

    public SqlUtil(Connection connection) {
        this.connection = connection;
    }

    public ResultSet exec(String sqlRequest) throws ConnectorException {
        try {
            if (connection.isClosed()) {
                throw new ConnectorException("closed connection");
            }
            Statement statement = connection.createStatement();
            statement.closeOnCompletion();
            LOGGER.debug("executing query = \"" + sqlRequest + "\"");
            return statement.executeQuery(sqlRequest);
        }
        catch (SQLException e) {
            throw new ConnectorException("can't execute query \"" + sqlRequest + "\"", e);
        }
    }

    /**
     * executing SQL request for setting and appending data
     * @param sqlRequest - SQL request for executing
     */
    public void execUpdate(String sqlRequest) throws ConnectorException {
        try {
            if (connection.isClosed()) {
                throw new ConnectorException("closed connection");
            }
            Statement statement = connection.createStatement();
            statement.closeOnCompletion();
            LOGGER.debug("executing update query \"" + sqlRequest + "\"");
            statement.executeUpdate(sqlRequest);
        }
        catch (SQLException e) {
            throw new ConnectorException("can't execute update query = \"" + sqlRequest + "\"", e);
        }
    }

    public long execUpdateLargePrepare(String sqlRequest, String... strings) throws ConnectorException {
        try {
            if (connection.isClosed()) {
                throw new ConnectorException("closed connection");
            }
            LOGGER.debug("exec large update prepare = \"" + sqlRequest + "\" with args = [" + String.join("; ", strings) + "]");
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(sqlRequest);
            statement.closeOnCompletion();
            for (int i = 0; i < strings.length; i++) {
                statement.setString(i + 1, strings[i]);
            }
            connection.commit();
            connection.setAutoCommit(true);
            return statement.executeLargeUpdate();
        }
        catch (SQLException e) {
            throw new ConnectorException("can't execute large update prepare statement = \"" + sqlRequest + "\" with args = [" + String.join("; ", strings) + "]", e);
        }
    }


    public int execUpdatePrepare(String sqlRequest, String... strings) throws ConnectorException {
        try {
            if (connection.isClosed()) {
                throw new ConnectorException("closed connection");
            }
            LOGGER.debug("exec prepare = \"" + sqlRequest + "\" with args = [" + String.join("; ", strings) + "]");
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(sqlRequest);
            statement.closeOnCompletion();
            for (int i = 0; i < strings.length; i++) {
                statement.setString(i + 1, strings[i]);
            }
            connection.commit();
            connection.setAutoCommit(true);
            return statement.executeUpdate();
        }
        catch (SQLException e) {
            throw new ConnectorException("can't execute prepare statement = \"" + sqlRequest + "\" with args = [" + String.join("; ", strings) + "]", e);
        }
    }

    public ResultSet execPrepare(String sqlRequest, String... strings) throws ConnectorException {
        try {
            if (connection.isClosed()) {
                throw new ConnectorException("closed connection");
            }
            LOGGER.debug("exec prepare = \"" + sqlRequest + "\" with args = [" + String.join("; ", strings) + "]");
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(sqlRequest);
            statement.closeOnCompletion();
            for (int i = 0; i < strings.length; i++) {
                statement.setString(i + 1, strings[i]);
            }
            connection.commit();
            connection.setAutoCommit(true);
            return statement.executeQuery();
        }
        catch (SQLException e) {
            throw new ConnectorException("can't execute prepare statement = \"" + sqlRequest + "\" with args = [" + String.join("; ", strings) + "]", e);
        }
    }

    public void closeConnection() throws ConnectorException {
        try {
            if (!connection.isClosed()) {
                connection.close();
            }
        }
        catch (SQLException e) {
            throw new ConnectorException("can't closing connection", e);
        }
    }
}
