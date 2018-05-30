package by.fyodorov.musicstore.repository;


import by.fyodorov.musicstore.application.InitParameter;
import by.fyodorov.musicstore.connector.ConnectionPool;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.connector.SqlUtil;
import by.fyodorov.musicstore.controller.ContextParameter;
import by.fyodorov.musicstore.model.AlbumEntity;
import by.fyodorov.musicstore.specification.album.AlbumRepositorySpecification;
import by.fyodorov.musicstore.specification.album.custom.AlbumCustomSelectSpecification;
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
    private static final String ADD_ALBUM_SQL =
            "INSERT INTO " + ALBUM_BD_SCHEME + "." + ALBUM_BD_TABLE + " ("
                    + ALBUM_NAME + ", "
                    + ALBUM_GENRE + ", "
                    + ALBUM_PRICE + ", "
                    + ALBUM_DATE + ", "
                    + ALBUM_PERFORMER_ID + ") " +
                    "VALUES (\'%1$s\', \'%2$s\', \'%3$s\', \'%4$s\', %5$s);";

    private static final String REMOVE_ALBUM_SQL =
            "DELETE FROM " + ALBUM_BD_SCHEME + "." + ALBUM_BD_TABLE + " WHERE " +
                    ALBUM_ID + " = %1$s;";

    private SqlUtil util;

    public AlbumRepository() throws ConnectorException {
        ContextParameter parameter = ContextParameter.getInstance();
        util = new SqlUtil(ConnectionPool.getInstance(parameter.getContextParam(InitParameter.DATA_BASE_INIT.toString())).getConnection());
    }

    public void add(AlbumEntity album) throws ConnectorException {
        LOGGER.debug("adding new album");
        util.execUpdate(String.format(ADD_ALBUM_SQL,
                album.getName(),
                album.getGenre(),
                album.getPrice(),
                album.getDate(),
                album.getPerformerId()));
    }

    public void remove(AlbumEntity album) throws ConnectorException {
        LOGGER.debug("remove album");
        util.execUpdate(String.format(REMOVE_ALBUM_SQL, album.getId()));
    }
    public void update(AlbumEntity album) {
        LOGGER.debug("update album");
        //util.execUpdate();
    }

    /*
    public List<UserEntity> query(UserRepositorySpecification specification) throws ConnectorException {
        ResultSet set = util.exec(specification.toSqlClauses());
        LinkedList<UserEntity> list = new LinkedList<>();
        return list;
    }*/

    public LinkedList<AlbumEntity> prepareQuery(AlbumRepositorySpecification specification) throws ConnectorException {
        LOGGER.debug("custom user query");
        ResultSet set = util.execPrepare(specification.toSqlClauses(), specification.getArguments());
        LinkedList<AlbumEntity> list = new LinkedList<>();
        try {
            while (set.next()) {
                int id = set.getInt(ALBUM_ID.toString());
                String name = set.getString(ALBUM_NAME.toString());
                String genre = set.getString(ALBUM_GENRE.toString());
                int price = set.getInt(ALBUM_PRICE.toString());
                Date date = set.getDate(ALBUM_DATE.toString());
                int performersId = set.getInt(ALBUM_PERFORMER_ID.toString());

                list.add(new AlbumEntity(id, name, genre, price, date, performersId));
            }
        }
        catch (SQLException e) {
            throw new ConnectorException("can't read result set from Album DB", e);
        }
        list.forEach(LOGGER::debug);
        return list;
    }

    public LinkedList<HashMap<String, String>> customQuery(AlbumCustomSelectSpecification specification) throws ConnectorException {
        LOGGER.debug("custom query");
        ResultSet set = util.execPrepare(specification.toSqlClauses(), specification.getArguments());
        return specification.fromSet(set);
    }

    public int prepareUpdate(AlbumCustomSelectSpecification specification) throws ConnectorException {
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
