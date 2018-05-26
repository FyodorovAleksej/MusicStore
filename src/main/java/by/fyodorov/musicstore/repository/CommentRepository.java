package by.fyodorov.musicstore.repository;

import by.fyodorov.musicstore.application.InitParameter;
import by.fyodorov.musicstore.connector.ConnectionPool;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.connector.SqlUtil;
import by.fyodorov.musicstore.controller.ContextParameter;
import by.fyodorov.musicstore.model.CommentEntity;
import by.fyodorov.musicstore.specification.comment.custom.CommentCustomSelectSpecification;
import by.fyodorov.musicstore.specification.comment.CommentRepositorySpecification;
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

    private static final String ADD_ASSEMBLAGE_SQL =
            "INSERT INTO " + COMMENT_BD_SCHEME + "." + COMMENT_BD_TABLE + " ("
                    + COMMENT_ID + ", "
                    + COMMENT_TEXT + ", "
                    + COMMENT_DATE + ", "
                    + COMMENT_USER_ID + ", "
                    + COMMENT_TRACK_ID + ", "
                    + COMMENT_PERFORMER_ID + ") " +
                    "VALUES (\'%1$s\', \'%2$s\', \'%3$s\', %4$s, %5$s, %6$s);";

    private static final String REMOVE_ASSEMBLAGE_SQL =
            "DELETE FROM " + COMMENT_BD_SCHEME + "." + COMMENT_BD_TABLE + " WHERE " +
                    COMMENT_ID + " = %1$s;";

    private SqlUtil util;

    public CommentRepository() throws ConnectorException {
        ContextParameter parameter = ContextParameter.getInstance();
        util = new SqlUtil(ConnectionPool.getInstance(parameter.getContextParam(InitParameter.DATA_BASE_INIT.toString())).getConnection());
    }

    public void add(CommentEntity comment) throws ConnectorException {
        LOGGER.debug("adding new comment");
        util.execUpdate(String.format(ADD_ASSEMBLAGE_SQL,
                comment.getText(),
                comment.getDate(),
                comment.getUserId(),
                comment.getTrackId(),
                comment.getPerformerId()));
    }

    public void remove(CommentEntity comment) throws ConnectorException {
        LOGGER.debug("remove comment");
        util.execUpdate(String.format(REMOVE_ASSEMBLAGE_SQL, comment.getCommentId()));
    }
    public void update(CommentEntity comment) {
        LOGGER.debug("update comment");
        //util.execUpdate();
    }

    /*
    public List<UserEntity> query(UserRepositorySpecification specification) throws ConnectorException {
        ResultSet set = util.exec(specification.toSqlClauses());
        LinkedList<UserEntity> list = new LinkedList<>();
        return list;
    }*/

    public LinkedList<CommentEntity> prepareQuery(CommentRepositorySpecification specification) throws ConnectorException {
        LOGGER.debug("custom comment query");
        ResultSet set = util.execPrepare(specification.toSqlClauses(), specification.getArguments());
        LinkedList<CommentEntity> list = new LinkedList<>();
        try {
            while (set.next()) {
                int id = set.getInt(COMMENT_ID.toString());
                String text = set.getString(COMMENT_TEXT.toString());
                Date date = set.getDate(COMMENT_DATE.toString());
                int userId = set.getInt(COMMENT_USER_ID.toString());
                int trackId = set.getInt(COMMENT_TRACK_ID.toString());
                int performerId = set.getInt(COMMENT_PERFORMER_ID.toString());


                list.add(new CommentEntity(id, text, date, userId, trackId, performerId));
            }
        }
        catch (SQLException e) {
            throw new ConnectorException("can't read result set from Comment DB", e);
        }
        list.forEach(LOGGER::debug);
        return list;
    }

    public LinkedList<HashMap<String, String>> customQuery(CommentCustomSelectSpecification specification) throws ConnectorException {
        LOGGER.debug("custom query");
        ResultSet set = util.execPrepare(specification.toSqlClauses(), specification.getArguments());
        return specification.fromSet(set);
    }

    public int prepareUpdate(CommentCustomSelectSpecification specification) throws ConnectorException {
        LOGGER.debug("custom update");
        return util.execUpdatePrepare(specification.toSqlClauses(), specification.getArguments());
    }


    public void close() throws ConnectorException {
        util.closeConnection();
    }
}
