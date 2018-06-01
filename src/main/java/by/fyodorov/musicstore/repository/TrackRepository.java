package by.fyodorov.musicstore.repository;

import by.fyodorov.musicstore.application.InitParameter;
import by.fyodorov.musicstore.connector.ConnectionPool;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.connector.SqlUtil;
import by.fyodorov.musicstore.controller.ContextParameter;
import by.fyodorov.musicstore.model.TrackEntity;
import by.fyodorov.musicstore.specification.track.TrackCustomSelectSpecification;
import by.fyodorov.musicstore.specification.track.TrackCustomUpdateSpecification;
import by.fyodorov.musicstore.specification.track.TrackLimitSelectSpecification;
import by.fyodorov.musicstore.specification.track.TrackRepositorySpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static by.fyodorov.musicstore.specification.track.TrackRepositoryType.*;

/**
 * repository for working with track DB
 */
public class TrackRepository {
    private static final Logger LOGGER = LogManager.getLogger(TrackRepository.class);
    private static final Lock MODIFY_LOCK = new ReentrantLock();
    private SqlUtil util;

    /**
     * creating repository with connection from connection pool
     * @throws ConnectorException - when can't getting connection
     */
    public TrackRepository() throws ConnectorException {
        ContextParameter parameter = ContextParameter.getInstance();
        util = new SqlUtil(ConnectionPool.getInstance(parameter.getContextParam(InitParameter.DATA_BASE_INIT.toString())).getConnection());
    }

    /**
     * executing select query by using prepare statement
     * @param specification - specification for select
     * @return - list of track entities
     * @throws ConnectorException - when can't executing query
     */
    public LinkedList<TrackEntity> prepareQuery(TrackRepositorySpecification specification) throws ConnectorException {
        LOGGER.debug("custom track query");
        ResultSet set = util.execPrepare(specification.toSqlClauses(), specification.getArguments());
        LinkedList<TrackEntity> list = new LinkedList<>();

        try {
            while (set.next()) {
                int id = set.getInt(TRACK_ID.toString());
                String name = set.getString(TRACK_NAME.toString());
                String genre = set.getString(TRACK_GENRE.toString());
                int price = set.getInt(TRACK_PRICE.toString());
                Date date = set.getDate(TRACK_DATE.toString());
                int performerId = set.getInt(TRACK_PERFORMER_FK.toString());

                list.add(new TrackEntity(id, name, genre, price, date, performerId));
            }
        } catch (SQLException e) {
            throw new ConnectorException("can't read result set from Track DB", e);
        }
        list.forEach(LOGGER::debug);

        return list;
    }

    /**
     * executing custom query with using prepare statement
     * @param specification - specification for repository
     * @return - list of parameters (result of executing query)
     * @throws ConnectorException - when can't perform query
     */
    public LinkedList<HashMap<String, String>> customQuery(TrackCustomSelectSpecification specification) throws ConnectorException {
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
    public int prepareUpdate(TrackCustomUpdateSpecification specification) throws ConnectorException {
        LOGGER.debug("custom update");
        return util.execUpdatePrepare(specification.toSqlClauses(), specification.getArguments());
    }

    /**
     * executing custom query with using prepare statement
     * @param specification - specification for repository
     * @return - list of parameters (result of executing query)
     * @throws ConnectorException - when can't perform query
     */
    public LinkedList<HashMap<String, String>> prepareSelectWithLimit(TrackLimitSelectSpecification specification) throws ConnectorException {
        LOGGER.debug("custom update");
        return specification.fromSet(util.execSelectPrepare(specification.toSqlClauses(), specification.getArguments(), specification.getLimits()));
    }

    /**
     * close repository
     * @throws ConnectorException - when cant close connection
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
