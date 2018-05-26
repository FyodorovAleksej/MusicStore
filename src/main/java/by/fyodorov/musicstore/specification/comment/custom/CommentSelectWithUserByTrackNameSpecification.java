package by.fyodorov.musicstore.specification.comment.custom;

import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.specification.track.TrackRepositoryType;
import by.fyodorov.musicstore.specification.user.UserRepositoryType;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

import static by.fyodorov.musicstore.specification.comment.CommentRepositoryType.*;
import static by.fyodorov.musicstore.specification.user.UserRepositoryType.USER_USERNAME;
import static by.fyodorov.musicstore.specification.user.UserRepositoryType.USER_ID;
import static by.fyodorov.musicstore.specification.track.TrackRepositoryType.TRACK_NAME;
import static by.fyodorov.musicstore.specification.track.TrackRepositoryType.TRACK_ID;

public class CommentSelectWithUserByTrackNameSpecification implements CommentCustomSelectSpecification {
    private static final String SELECT_BY_NAME =
            "SELECT " + UserRepositoryType.USER_BD_TABLE + "." + USER_USERNAME + ", "
            + COMMENT_BD_TABLE + "." + COMMENT_TEXT + ", "
            + COMMENT_BD_TABLE + "." + COMMENT_DATE
            + " FROM " + COMMENT_BD_SCHEME + "." + COMMENT_BD_TABLE
            + " JOIN " + UserRepositoryType.USER_BD_SCHEME + "." + UserRepositoryType.USER_BD_TABLE
            + " ON " + UserRepositoryType.USER_BD_TABLE + "." + USER_ID
            + " = " + COMMENT_BD_TABLE + "." + COMMENT_USER_ID + " "
            + " JOIN " + TrackRepositoryType.TRACK_BD_SCHEME + "." + TrackRepositoryType.TRACK_BD_TABLE
            + " ON " + TrackRepositoryType.TRACK_BD_TABLE + "." + TRACK_ID
            + " = " + COMMENT_BD_TABLE + "." + COMMENT_TRACK_ID
            + " WHERE " + TrackRepositoryType.TRACK_BD_TABLE + "." + TRACK_NAME + " = ?;";

    private String trackName;

    public static final String USER_USERNAME_KEY = "userName";
    public static final String COMMENT_TEXT_KEY = "text";
    public static final String COMMENT_DATE_KEY = "date";

    public CommentSelectWithUserByTrackNameSpecification(String trackName) {
        this.trackName = trackName;
    }

    @Override
    public String[] getArguments() {
        String[] result = new String[1];
        result[0] = trackName;
        return result;
    }

    @Override
    public String toSqlClauses() {
        return SELECT_BY_NAME;
    }

    @Override
    public LinkedList<HashMap<String, String>> fromSet(ResultSet set) throws ConnectorException {
        LinkedList<HashMap<String, String>> result = new LinkedList<>();
        try {
            while (set.next()) {
                HashMap<String, String> arguments = new HashMap<>();
                String userName = set.getString(USER_USERNAME.toString());
                String text = set.getString(COMMENT_TEXT.toString());
                Date date = set.getDate(COMMENT_DATE.toString());
                arguments.put(USER_USERNAME_KEY, userName);
                arguments.put(COMMENT_TEXT_KEY, text);
                arguments.put(COMMENT_DATE_KEY, date.toString());
                result.add(arguments);
            }
        }
        catch (SQLException e) {
            throw new ConnectorException("can't read result set from Comment DB", e);
        }
        return result;
    }
}
