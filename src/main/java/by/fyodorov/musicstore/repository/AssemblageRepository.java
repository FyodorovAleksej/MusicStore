package by.fyodorov.musicstore.repository;

import by.fyodorov.musicstore.application.InitParameter;
import by.fyodorov.musicstore.connector.ConnectionPool;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.connector.SqlUtil;
import by.fyodorov.musicstore.controller.ContextParameter;
import by.fyodorov.musicstore.model.AssemblageEntity;
import by.fyodorov.musicstore.specification.assemblage.AssemblageCustomUpdateSpecification;
import by.fyodorov.musicstore.specification.assemblage.AssemblageRepositorySpecification;
import by.fyodorov.musicstore.specification.assemblage.AssemblageCustomSelectSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static by.fyodorov.musicstore.specification.assemblage.AssemblageRepositoryType.*;

/**
 * class for working with assemblage DB
 */
public class AssemblageRepository {
    private static final Logger LOGGER = LogManager.getLogger(AssemblageRepository.class);
    private static final Lock MODIFY_LOCK = new ReentrantLock();
    private SqlUtil util;

    /**
     * create assemblage repository with connection from connection pool
     * @throws ConnectorException - when can't getting connection from connection pool
     */
    public AssemblageRepository() throws ConnectorException {
        ContextParameter parameter = ContextParameter.getInstance();
        util = new SqlUtil(ConnectionPool.getInstance(parameter.getContextParam(InitParameter.DATA_BASE_INIT.toString())).getConnection());
    }

    /**
     * executing custom query with using prepare statement
     * @param specification - specification for repository
     * @return - list of arguments (result of query)
     * @throws ConnectorException - when can't perform query
     */
    public LinkedList<HashMap<String, String>> customQuery(AssemblageCustomSelectSpecification specification) throws ConnectorException {
        LOGGER.debug("custom query");
        ResultSet set = util.execPrepare(specification.toSqlClauses(), specification.getArguments());
        return specification.fromSet(set);
    }

    /**
     * executing custom update query with using prepare statement
     * @param specification - specification for repository
     * @return - count of updated rows
     * @throws ConnectorException - when can't perform query
     */
    public int prepareUpdate(AssemblageCustomUpdateSpecification specification) throws ConnectorException {
        LOGGER.debug("custom update");
        return util.execUpdatePrepare(specification.toSqlClauses(), specification.getArguments());
    }

    /**
     * close repository
     * @throws ConnectorException - when can't close connection
     */
    public void close() throws ConnectorException {
        util.closeConnection();
    }

    /**
     * locking mutex for some threads
     */
    public static void modifyLock() {
        MODIFY_LOCK.lock();
    }

    /**
     * unlocking mutex for some threads
     */
    public static void modifyUnlock() {
        MODIFY_LOCK.unlock();
    }
}
