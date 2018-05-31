package by.fyodorov.musicstore.repository;


import by.fyodorov.musicstore.application.InitParameter;
import by.fyodorov.musicstore.connector.ConnectionPool;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.connector.SqlUtil;
import by.fyodorov.musicstore.controller.ContextParameter;
import by.fyodorov.musicstore.model.AlbumEntity;
import by.fyodorov.musicstore.specification.album.AlbumCustomUpdateSpecification;
import by.fyodorov.musicstore.specification.album.AlbumRepositorySpecification;
import by.fyodorov.musicstore.specification.album.AlbumCustomSelectSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static by.fyodorov.musicstore.specification.album.AlbumRepositoryType.*;

public class AlbumRepository {
    private static final Logger LOGGER = LogManager.getLogger(AlbumRepository.class);
    private static final Lock MODIFY_LOCK = new ReentrantLock();
    private SqlUtil util;

    public AlbumRepository() throws ConnectorException {
        ContextParameter parameter = ContextParameter.getInstance();
        util = new SqlUtil(ConnectionPool.getInstance(parameter.getContextParam(InitParameter.DATA_BASE_INIT.toString())).getConnection());
    }

    public LinkedList<HashMap<String, String>> customQuery(AlbumCustomSelectSpecification specification) throws ConnectorException {
        LOGGER.debug("custom query");
        ResultSet set = util.execPrepare(specification.toSqlClauses(), specification.getArguments());
        return specification.fromSet(set);
    }

    public int prepareUpdate(AlbumCustomUpdateSpecification specification) throws ConnectorException {
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
