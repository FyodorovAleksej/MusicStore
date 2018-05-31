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

public class AssemblageRepository {
    private static Logger LOGGER = LogManager.getLogger(AssemblageRepository.class);
    private static Lock MODIFY_LOCK = new ReentrantLock();

    private SqlUtil util;

    public AssemblageRepository() throws ConnectorException {
        ContextParameter parameter = ContextParameter.getInstance();
        util = new SqlUtil(ConnectionPool.getInstance(parameter.getContextParam(InitParameter.DATA_BASE_INIT.toString())).getConnection());
    }

    public LinkedList<HashMap<String, String>> customQuery(AssemblageCustomSelectSpecification specification) throws ConnectorException {
        LOGGER.debug("custom query");
        ResultSet set = util.execPrepare(specification.toSqlClauses(), specification.getArguments());
        return specification.fromSet(set);
    }
    public int prepareUpdate(AssemblageCustomUpdateSpecification specification) throws ConnectorException {
        LOGGER.debug("custom update");
        return util.execUpdatePrepare(specification.toSqlClauses(), specification.getArguments());
    }

    public void close() throws ConnectorException {
        util.closeConnection();
    }

    public static void modifyLock() {
        MODIFY_LOCK.lock();
    }

    public static void modifyUnlock() {
        MODIFY_LOCK.unlock();
    }
}
