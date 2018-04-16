package by.fyodorov.musicstore.specification.comment.custom;

import by.fyodorov.musicstore.connector.ConnectorException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

import static by.fyodorov.musicstore.specification.comment.CommentRepositoryConstant.*;
import static by.fyodorov.musicstore.specification.track.TrackRepositoryConstant.*;
import static by.fyodorov.musicstore.specification.user.UserRepositoryConstant.*;

public class CommentAddByTextAndUserSpecification implements CommentCustomSelectSpecification {
    /*
    INSERT INTO `musicstore`.`comments` (`text`, `date`, `users_userId`, `tracks_trackId`, `tracks_performers_id`)
SELECT 'nice',                                             -- text
CURDATE(),	                                              -- date
users.userId,                                             -- users_userId
tracks.trackId,                                           -- tracks_trackId
tracks.performers_id                                      -- tracks_performers_id
FROM users, tracks WHERE users.nickName = 'Akela'          -- user nickName
AND tracks.name = 'outbreak';                             -- track name
            */
    private static final String ADD_SQL =
            String.format("INSERT INTO %s.%s (%s, %s, %s, %s, %s) " +
                    "SELECT ?, CURDATE(), %s.%s, %s.%s, %s.%s " +
                    "FROM %s, %s WHERE %s.%s = ? " +
                    "AND %s.%s = ? ;",
                    COMMENT_BD_SCHEME, COMMENT_BD_TABLE,
                    COMMENT_TEXT, COMMENT_DATE, COMMENT_USER_ID, COMMENT_TRACK_ID, COMMENT_PERFORMER_ID,
                    USER_BD_TABLE, USER_ID,
                    TRACK_BD_TABLE, TRACK_ID,
                    TRACK_BD_TABLE, TRACK_PERFORMER_FK,
                    USER_BD_TABLE, TRACK_BD_TABLE,
                    USER_BD_TABLE, USER_USERNAME,
                    TRACK_BD_TABLE, TRACK_NAME);

    private String text;
    private String userName;
    private String trackName;

    public CommentAddByTextAndUserSpecification(String text, String userName, String trackName) {
        this.text = text;
        this.userName = userName;
        this.trackName = trackName;
    }

    @Deprecated
    @Override
    public LinkedList<HashMap<String, String>> fromSet(ResultSet set) throws ConnectorException {
        return null;
    }

    @Override
    public String toSqlClauses() {
        return ADD_SQL;
    }

    @Override
    public String[] getArguments() {
        String[] result = new String[3];
        result[0] = text;
        result[1] = userName;
        result[2] = trackName;
        return result;
    }
}
