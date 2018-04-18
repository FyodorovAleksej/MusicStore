package by.fyodorov.musicstore.specification.track.custom;

import by.fyodorov.musicstore.connector.ConnectorException;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

import static by.fyodorov.musicstore.specification.performer.PerformerRepositoryConstant.*;
import static by.fyodorov.musicstore.specification.track.TrackRepositoryConstant.*;
import static by.fyodorov.musicstore.specification.user.UserRepositoryConstant.*;

public class TrackOfUserByNameCustomSelect implements TrackCustomSelectSpecification {
    public static final String TRACK_NAME_KEY = "trackName";
    public static final String TRACK_GENRE_KEY = "trackGenre";
    public static final String TRACK_DATE_KEY = "trackDate";
    public static final String TRACK_PERFORMER_KEY = "trackPerformer";

    private static final String SELECT_USERS_TRACKS = String.format(
            "SELECT %s.%s, %s.%s, %s.%s, %s.%s FROM %s " +
            "JOIN %s ON %s.%s = %s.%s " +
            "JOIN %s ON %s.%s = %s.%s " +
            "JOIN %s ON %s.%s = %s.%s " +
            "WHERE %s.%s = ? ;\n",
            TRACK_BD_TABLE, TRACK_NAME,
            TRACK_BD_TABLE, TRACK_GENRE,
            TRACK_BD_TABLE, TRACK_DATE,
            PERFORMER_BD_TABLE, PERFORMER_NAME,
            USER_HAS_TRACKS_BD_TABLE,
            TRACK_BD_TABLE,
            TRACK_BD_TABLE, TRACK_ID,
            USER_HAS_TRACKS_BD_TABLE, USER_HAS_TRACKS_TRACK_ID,
            PERFORMER_BD_TABLE,
            PERFORMER_BD_TABLE, PERFORMER_ID,
            USER_HAS_TRACKS_BD_TABLE, USER_HAS_TRACKS_PERFORMER_ID,
            USER_BD_TABLE,
            USER_BD_TABLE, USER_ID,
            USER_HAS_TRACKS_BD_TABLE, USER_HAS_TRACKS_USER_ID,
            USER_BD_TABLE, USER_USERNAME);

    private String userName;

    public TrackOfUserByNameCustomSelect(String userName) {
        this.userName = userName;
    }

    @Override
    public String toSqlClauses() {
        return SELECT_USERS_TRACKS;
    }

    @Override
    public String[] getArguments() {
        String[] result = new String[1];
        result[0] = userName;
        return result;
    }

    @Override
    public LinkedList<HashMap<String, String>> fromSet(ResultSet set) throws ConnectorException {
        LinkedList<HashMap<String, String>> result = new LinkedList<>();
        try {
            while (set.next()) {
                HashMap<String, String> arguments = new HashMap<>();
                String trackName = set.getString(TRACK_NAME.toString());
                String genre = set.getString(TRACK_GENRE.toString());
                Date date = set.getDate(TRACK_DATE.toString());
                String performer = set.getString(PERFORMER_NAME.toString());

                arguments.put(TRACK_NAME_KEY, trackName);
                arguments.put(TRACK_GENRE_KEY, genre);
                arguments.put(TRACK_DATE_KEY, date.toString());
                arguments.put(TRACK_PERFORMER_KEY, performer);
                result.add(arguments);
            }
        }
        catch (SQLException e) {
            throw new ConnectorException("can't read result set from Track DB", e);
        }
        return result;
    }
}
