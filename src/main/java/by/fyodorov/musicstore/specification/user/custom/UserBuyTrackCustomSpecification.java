package by.fyodorov.musicstore.specification.user.custom;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;

import static by.fyodorov.musicstore.specification.track.TrackRepositoryType.*;
import static by.fyodorov.musicstore.specification.user.UserRepositoryType.*;

public class UserBuyTrackCustomSpecification implements UserCustomSelectSpecification {
    private static final String BUY_TRANSACTION = String.format(
            "INSERT INTO %s (%s.%s, %s.%s, %s.%s) " +
            "SELECT %s.%s, %s.%s, %s.%s " +
            "FROM %s, %s WHERE %s.%s = ? " +
            "AND %s.%s = ? ;",

            USER_HAS_TRACKS_BD_TABLE,
            USER_HAS_TRACKS_BD_TABLE, USER_HAS_TRACKS_USER_ID,
            USER_HAS_TRACKS_BD_TABLE, USER_HAS_TRACKS_TRACK_ID,
            USER_HAS_TRACKS_BD_TABLE, USER_HAS_TRACKS_PERFORMER_ID,
            USER_BD_TABLE, USER_ID,
            TRACK_BD_TABLE, TRACK_ID,
            TRACK_BD_TABLE, TRACK_PERFORMER_FK,
            USER_BD_TABLE,
            TRACK_BD_TABLE,
            USER_BD_TABLE, USER_USERNAME,
            TRACK_BD_TABLE, TRACK_NAME);

    private String userName;
    private String trackName;

    public  UserBuyTrackCustomSpecification(String userName, String trackName) {
        this.userName = userName;
        this.trackName = trackName;
    }

    @Override
    public String toSqlClauses() {
        return BUY_TRANSACTION;
    }

    @Override
    public String[] getArguments() {
        String[] result = new String[2];
        result[0] = userName;
        result[1] = trackName;
        return result;
    }

    @Deprecated
    @Override
    public LinkedList<HashMap<String, String>> fromSet(ResultSet set) {
        return null;
    }
}
