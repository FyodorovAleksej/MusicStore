package by.fyodorov.musicstore.specification.track.custom;

import by.fyodorov.musicstore.connector.ConnectorException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

import static by.fyodorov.musicstore.specification.album.AlbumRepositoryType.*;
import static by.fyodorov.musicstore.specification.performer.PerformerRepositoryType.*;
import static by.fyodorov.musicstore.specification.track.TrackRepositoryType.*;
import static by.fyodorov.musicstore.specification.user.UserRepositoryType.*;

public class TrackInAlbumForUserCustomSelectSpecification implements TrackCustomSelectSpecification {

    private static final String SUMMARY_COLUMN = "summary";

    private static final String SELECT_TRACK_INFO_FOR_USERNAME = String.format(
            "SELECT %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, CAST(%s.%s*(1 - (%s.%s / 100)) AS UNSIGNED) AS %s " +
                    "FROM %s JOIN %s ON %s.%s = %s.%s " +
                    "JOIN %s ON %s.%s = ? " +
                    "JOIN %s ON %s.%s = %s.%s " +
                    "JOIN %s ON %s.%s = %s.%s " +
                    "WHERE %s.%s = ? ;",

            TRACK_BD_TABLE, TRACK_NAME,
            TRACK_BD_TABLE, TRACK_GENRE,
            PERFORMER_BD_TABLE, PERFORMER_NAME,
            TRACK_BD_TABLE, TRACK_DATE,
            TRACK_BD_TABLE, TRACK_PRICE,
            TRACK_BD_TABLE, TRACK_PRICE,
            USER_BD_TABLE, USER_DISCOUNT,
            SUMMARY_COLUMN,
            ALBUM_BD_TABLE,
            ALBUM_HAS_TRACKS_BD_TABLE,
            ALBUM_BD_TABLE, ALBUM_ID,
            ALBUM_HAS_TRACKS_BD_TABLE, ALBUM_HAS_TRACKS_ALBUM_ID,
            USER_BD_TABLE,
            USER_BD_TABLE, USER_USERNAME,
            TRACK_BD_TABLE,
            ALBUM_HAS_TRACKS_BD_TABLE, ALBUM_HAS_TRACKS_TRACK_ID,
            TRACK_BD_TABLE, TRACK_ID,
            PERFORMER_BD_TABLE,
            PERFORMER_BD_TABLE, PERFORMER_ID,
            TRACK_BD_TABLE, TRACK_PERFORMER_FK,
            ALBUM_BD_TABLE, ALBUM_NAME);

    public static final String TRACK_NAME_KEY = "trackName";
    public static final String TRACK_GENRE_KEY = "trackGenre";
    public static final String PERFORMER_KEY = "performerName";
    public static final String TRACK_DATE_KEY = "trackDate";
    public static final String TRACK_PRICE_KEY = "trackPrice";
    public static final String TRACK_PRICE_SUMMARY_KEY = "trackSummaryPrice";

    private String albumName;
    private String userName;

    public TrackInAlbumForUserCustomSelectSpecification(String albumName, String userName) {
        this.albumName = albumName;
        this.userName = userName;
    }

    @Override
    public String toSqlClauses() {
        return SELECT_TRACK_INFO_FOR_USERNAME;
    }

    @Override
    public String[] getArguments() {
        String[] result = new String[2];
        result[0] = userName;
        result[1] = albumName;
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
                String performer = set.getString(PERFORMER_NAME.toString());
                String date = set.getDate(TRACK_DATE.toString()).toString();
                String price = Integer.toString(set.getInt(TRACK_PRICE.toString()));
                String summary = Integer.toString(set.getInt(SUMMARY_COLUMN));

                arguments.put(TRACK_NAME_KEY, trackName);
                arguments.put(TRACK_GENRE_KEY, genre);
                arguments.put(PERFORMER_KEY, performer);
                arguments.put(TRACK_DATE_KEY, date);
                arguments.put(TRACK_PRICE_KEY, price);
                arguments.put(TRACK_PRICE_SUMMARY_KEY, summary);

                result.add(arguments);
            }
        }
        catch (SQLException e) {
            throw new ConnectorException("can't read result set from Track DB", e);
        }
        return result;
    }
}
