package by.fyodorov.musicstore.connector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Arrays;

/**
 * class for working with DB
 */
public class SqlUtil {
    private static Logger LOGGER = LogManager.getLogger(SqlUtil.class);
    private Connection connection;

    /**
     * created sql util with connection
     * @param connection - connection with DB
     */
    public SqlUtil(Connection connection) {
        this.connection = connection;
    }

    /**
     * execute SQL request
     * @param sqlRequest - SQL request for executing
     * @return - result set of executed request
     * @throws ConnectorException - when can't execute request
     */
    public ResultSet exec(String sqlRequest) throws ConnectorException {
        try {
            if (connection.isClosed()) {
                throw new ConnectorException("closed connection");
            }
            Statement statement = connection.createStatement();
            statement.closeOnCompletion();
            LOGGER.debug("executing query = \"" + sqlRequest + "\"");
            return statement.executeQuery(sqlRequest);
        } catch (SQLException e) {
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
        } catch (SQLException e) {
            throw new ConnectorException("can't execute update query = \"" + sqlRequest + "\"", e);
        }
    }

    /**
     * execute update sql request by prepare statement
     * @param sqlRequest - sql request for update executing
     * @param strings - arguments of sql request. Adding as strings into compiled request
     * @return - count of rows, that was updated
     * @throws ConnectorException - when can't execute request
     */
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
        } catch (SQLException e) {
            throw new ConnectorException("can't execute prepare statement = \"" + sqlRequest + "\" with args = [" + String.join("; ", strings) + "]", e);
        }
    }


    /**
     * execute select sql request by prepare statement
     * @param sqlRequest - sql request for executing
     * @param strings - first arguments of sql request. Adding as strings into compiled request
     * @param integers - last arguments of sql request. Adding as integers into compiled request
     * @return - result set of select request
     * @throws ConnectorException - when can't execute request
     */
    public ResultSet execSelectPrepare(String sqlRequest, String[] strings, Integer[] integers) throws ConnectorException {
        try {
            if (connection.isClosed()) {
                throw new ConnectorException("closed connection");
            }
            LOGGER.debug("exec prepare = \"" + sqlRequest + "\" with args = [" + String.join("; ", strings) + "] ; [" + Arrays.toString(integers) + "]");
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(sqlRequest);
            statement.closeOnCompletion();
            int index = 1;
            for (String string : strings) {
                statement.setString(index++, string);
            }
            for (Integer integer : integers) {
                statement.setInt(index++, integer);
            }
            connection.commit();
            connection.setAutoCommit(true);
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new ConnectorException("can't execute prepare statement = \"" + sqlRequest + "\" with args = [" + String.join("; ", strings) + "]", e);
        }
    }


    /**
     * execute sql request by prepare statement
     * @param sqlRequest - sql request for executing
     * @param strings - arguments of sql request. Adding as strings into compiled request
     * @return - resultset of request
     * @throws ConnectorException - when can't execute request
     */
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
        } catch (SQLException e) {
            throw new ConnectorException("can't execute prepare statement = \"" + sqlRequest + "\" with args = [" + String.join("; ", strings) + "]", e);
        }
    }

    /**
     * close connection with DB
     * @throws ConnectorException - when can't close connection
     */
    public void closeConnection() throws ConnectorException {
        try {
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new ConnectorException("can't closing connection", e);
        }
    }
}
