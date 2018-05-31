package by.fyodorov.musicstore.repository;

import by.fyodorov.musicstore.application.InitParameter;
import by.fyodorov.musicstore.connector.ConnectionPool;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.connector.SqlUtil;
import by.fyodorov.musicstore.controller.ContextParameter;
import by.fyodorov.musicstore.model.CommentEntity;
import by.fyodorov.musicstore.specification.comment.CommentCustomUpdateSpecification;
import by.fyodorov.musicstore.specification.comment.CommentRepositorySpecification;
import by.fyodorov.musicstore.specification.comment.CommentCustomSelectSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

import static by.fyodorov.musicstore.specification.comment.CommentRepositoryType.*;

public class CommentRepository {
    private static Logger LOGGER = LogManager.getLogger(CommentRepository.class);

    private SqlUtil util;

    public CommentRepository() throws ConnectorException {
        ContextParameter parameter = ContextParameter.getInstance();
        util = new SqlUtil(ConnectionPool.getInstance(parameter.getContextParam(InitParameter.DATA_BASE_INIT.toString())).getConnection());
    }

    public LinkedList<HashMap<String, String>> customQuery(CommentCustomSelectSpecification specification) throws ConnectorException {
        LOGGER.debug("custom query");
        ResultSet set = util.execPrepare(specification.toSqlClauses(), specification.getArguments());
        return specification.fromSet(set);
    }

    public int prepareUpdate(CommentCustomUpdateSpecification specification) throws ConnectorException {
        LOGGER.debug("custom update");
        return util.execUpdatePrepare(specification.toSqlClauses(), specification.getArguments());
    }


    public void close() throws ConnectorException {
        util.closeConnection();
    }
}
