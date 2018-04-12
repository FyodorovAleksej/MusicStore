package by.fyodorov.musicstore.repository;

import by.fyodorov.musicstore.application.InitParameter;
import by.fyodorov.musicstore.connector.ConnectionPool;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.connector.SqlUtil;
import by.fyodorov.musicstore.controller.ContextParameter;
import by.fyodorov.musicstore.model.TrackEntity;
import by.fyodorov.musicstore.specification.track.custom.TrackCustomSelectSpecification;
import by.fyodorov.musicstore.specification.track.TrackRepositorySpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

import static by.fyodorov.musicstore.specification.track.TrackRepositoryConstant.*;

public class TrackRepository {
    private static Logger LOGGER = LogManager.getLogger(TrackRepository.class);

    private static final String ADD_TRACK_SQL =
            "INSERT INTO " + BD_SCHEME + "." + BD_TABLE + " ("
                    + TRACK_NAME + ", "
                    + TRACK_GENRE + ", "
                    + TRACK_PRICE + ", "
                    + TRACK_DATE + ", "
                    + TRACK_PERFORMER_FK + ") " +
                    "VALUES (\'%1$s\', \'%2$s\', \'%3$s\', \'%4$s\', %5$s);";

    private static final String REMOVE_TRACK_SQL =
            "DELETE FROM " + BD_SCHEME + "." + BD_TABLE + " WHERE " +
                    TRACK_ID + " = \'%1$s\';";

    private SqlUtil util;

    public TrackRepository() throws ConnectorException {
        ContextParameter parameter = ContextParameter.getInstance();
        util = new SqlUtil(ConnectionPool.getInstance(parameter.getContextParam(InitParameter.DATA_BASE_INIT.toString())).getConnection());
    }

    public void add(TrackEntity track) throws ConnectorException {
        LOGGER.debug("adding new track");
        util.execUpdate(String.format(ADD_TRACK_SQL,
                track.getName(),
                track.getGenre(),
                track.getPrice(),
                track.getDate(),
                track.getPerformerId()));
    }

    public void remove(TrackEntity track) throws ConnectorException {
        LOGGER.debug("remove track");
        util.execUpdate(String.format(REMOVE_TRACK_SQL, track.getId()));
    }
    public void update(TrackEntity track) {
        LOGGER.debug("update track");
        //util.execUpdate();
    }

    /*
    public List<UserEntity> query(UserRepositorySpecification specification) throws ConnectorException {
        ResultSet set = util.exec(specification.toSqlClauses());
        LinkedList<UserEntity> list = new LinkedList<>();
        return list;
    }*/

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
        }
        catch (SQLException e) {
            throw new ConnectorException("can't read result set from Track DB", e);
        }
        list.forEach(LOGGER::debug);

        return list;
    }

    public LinkedList<HashMap<String, String>> customSelect(TrackCustomSelectSpecification specification) throws ConnectorException {
        LOGGER.debug("custom select query");
        ResultSet set = util.execPrepare(specification.toSqlClauses(), specification.getArguments());
        return specification.fromSet(set);
    }


    public void close() throws ConnectorException {
        util.closeConnection();
    }
}
