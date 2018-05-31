package by.fyodorov.musicstore.repository;

import by.fyodorov.musicstore.application.InitParameter;
import by.fyodorov.musicstore.connector.ConnectionPool;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.connector.SqlUtil;
import by.fyodorov.musicstore.controller.ContextParameter;
import by.fyodorov.musicstore.model.PerformerEntity;
import by.fyodorov.musicstore.specification.performer.PerformerRepositorySpecification;
import by.fyodorov.musicstore.specification.performer.PerformerCustomSelectSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

import static by.fyodorov.musicstore.specification.performer.PerformerRepositoryType.*;

public class PerformerRepository {
    private static Logger LOGGER = LogManager.getLogger(PerformerRepository.class);

    private SqlUtil util;

    public PerformerRepository() throws ConnectorException {
        ContextParameter parameter = ContextParameter.getInstance();
        util = new SqlUtil(ConnectionPool.getInstance(parameter.getContextParam(InitParameter.DATA_BASE_INIT.toString())).getConnection());
    }

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
        } catch (SQLException e) {
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
