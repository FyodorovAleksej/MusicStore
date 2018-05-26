package by.fyodorov.musicstore.repository;

import by.fyodorov.musicstore.application.InitParameter;
import by.fyodorov.musicstore.connector.ConnectionPool;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.connector.SqlUtil;
import by.fyodorov.musicstore.controller.ContextParameter;
import by.fyodorov.musicstore.model.PerformerEntity;
import by.fyodorov.musicstore.specification.performer.custom.PerformerCustomSelectSpecification;
import by.fyodorov.musicstore.specification.performer.PerformerRepositorySpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

import static by.fyodorov.musicstore.specification.performer.PerformerRepositoryType.*;

public class PerformerRepository {
    private static Logger LOGGER = LogManager.getLogger(PerformerRepository.class);

    private static final String ADD_PERFORMER_SQL =
            "INSERT INTO " + PERFORMER_BD_SCHEME + "." + PERFORMER_BD_TABLE + " ("
                    + PERFORMER_NAME + ") " +
                    "VALUES (\'%1$s\');";

    private static final String REMOVE_PERFORMER_SQL =
            "DELETE FROM " + PERFORMER_BD_SCHEME + "." + PERFORMER_BD_TABLE + " WHERE " +
                    PERFORMER_ID + " = \'%1$s\';";

    private SqlUtil util;

    public PerformerRepository() throws ConnectorException {
        ContextParameter parameter = ContextParameter.getInstance();
        util = new SqlUtil(ConnectionPool.getInstance(parameter.getContextParam(InitParameter.DATA_BASE_INIT.toString())).getConnection());
    }

    public void add(PerformerEntity performer) throws ConnectorException {
        LOGGER.debug("adding new performer");
        util.execUpdate(String.format(ADD_PERFORMER_SQL, performer.getName()));
    }

    public void remove(PerformerEntity performer) throws ConnectorException {
        LOGGER.debug("remove performer");
        util.execUpdate(String.format(REMOVE_PERFORMER_SQL, performer.getId()));
    }
    public void update(PerformerEntity performer) {
        LOGGER.debug("update performer");
        //util.execUpdate();
    }

    /*
    public List<UserEntity> query(UserRepositorySpecification specification) throws ConnectorException {
        ResultSet set = util.exec(specification.toSqlClauses());
        LinkedList<UserEntity> list = new LinkedList<>();
        return list;
    }*/

    public LinkedList<PerformerEntity> prepareQuery(PerformerRepositorySpecification specification) throws ConnectorException {
        LOGGER.debug("custom performer query");
        ResultSet set = util.execPrepare(specification.toSqlClauses(), specification.getArguments());
        LinkedList<PerformerEntity> list = new LinkedList<>();

        try {
            while (set.next()) {
                int id = set.getInt(PERFORMER_ID.toString());
                String name = set.getString(PERFORMER_NAME.toString());
                list.add(new PerformerEntity(id, name));
            }
        }
        catch (SQLException e) {
            throw new ConnectorException("can't read result set from Track DB", e);
        }
        list.forEach(LOGGER::debug);

        return list;
    }

    public LinkedList<HashMap<String, String>> customQuery(PerformerCustomSelectSpecification specification) throws ConnectorException {
        LOGGER.debug("custom query");
        ResultSet set = util.execPrepare(specification.toSqlClauses(), specification.getArguments());
        return specification.fromSet(set);
    }


    public void close() throws ConnectorException {
        util.closeConnection();
    }
}
