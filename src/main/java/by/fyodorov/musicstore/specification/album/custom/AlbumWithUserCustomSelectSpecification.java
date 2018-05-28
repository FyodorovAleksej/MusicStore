package by.fyodorov.musicstore.specification.album.custom;

import by.fyodorov.musicstore.connector.ConnectorException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

import static by.fyodorov.musicstore.specification.performer.PerformerRepositoryType.*;
import static by.fyodorov.musicstore.specification.album.AlbumRepositoryType.*;
import static by.fyodorov.musicstore.specification.user.UserRepositoryType.*;

public class AlbumWithUserCustomSelectSpecification implements AlbumCustomSelectSpecification {
    private static final String SUMMARY_COLUMN = "summary";

    private static final String SELECT_ALBUM_INFO_FOR_USERNAME = String.format(
            "SELECT %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, " +
                    "CAST(%s.%s*(1 - (%s.%s / 100)) AS UNSIGNED) AS %s " +
                    "FROM %s LEFT JOIN %s ON %s.%s = ? " +
                    "JOIN %s ON %s.%s = %s.%s ;",

            ALBUM_BD_TABLE, ALBUM_NAME,
            ALBUM_BD_TABLE, ALBUM_GENRE,
            PERFORMER_BD_TABLE, PERFORMER_NAME,
            ALBUM_BD_TABLE, ALBUM_DATE,
            ALBUM_BD_TABLE, ALBUM_PRICE,
            ALBUM_BD_TABLE, ALBUM_PRICE,
            USER_BD_TABLE, USER_DISCOUNT,
            SUMMARY_COLUMN,
            ALBUM_BD_TABLE, USER_BD_TABLE,
            USER_BD_TABLE, USER_USERNAME,
            PERFORMER_BD_TABLE,
            PERFORMER_BD_TABLE, PERFORMER_ID,
            ALBUM_BD_TABLE, ALBUM_PERFORMER_ID);

    public static final String ALBUM_NAME_KEY = "trackName";
    public static final String ALBUM_GENRE_KEY = "trackGenre";
    public static final String ALBUM_PERFORMER_KEY = "performerName";
    public static final String ALBUM_DATE_KEY = "trackDate";
    public static final String ALBUM_PRICE_KEY = "trackPrice";
    public static final String ALBUM_PRICE_SUMMARY_KEY = "trackSummaryPrice";

    private String name;

    public AlbumWithUserCustomSelectSpecification(String name) {
        this.name = name;
    }

    @Override
    public String toSqlClauses() {
        return SELECT_ALBUM_INFO_FOR_USERNAME;
    }

    @Override
    public String[] getArguments() {
        String[] result = new String[1];
        result[0] = name;
        return result;
    }

    @Override
    public LinkedList<HashMap<String, String>> fromSet(ResultSet set) throws ConnectorException {
        LinkedList<HashMap<String, String>> result = new LinkedList<>();
        try {
            while (set.next()) {
                HashMap<String, String> arguments = new HashMap<>();
                String trackName = set.getString(ALBUM_NAME.toString());
                String genre = set.getString(ALBUM_GENRE.toString());
                String performer = set.getString(PERFORMER_NAME.toString());
                String date = set.getDate(ALBUM_DATE.toString()).toString();
                String price = Integer.toString(set.getInt(ALBUM_PRICE.toString()));
                String summary = Integer.toString(set.getInt(SUMMARY_COLUMN));

                arguments.put(ALBUM_NAME_KEY, trackName);
                arguments.put(ALBUM_GENRE_KEY, genre);
                arguments.put(ALBUM_PERFORMER_KEY, performer);
                arguments.put(ALBUM_DATE_KEY, date);
                arguments.put(ALBUM_PRICE_KEY, price);
                arguments.put(ALBUM_PRICE_SUMMARY_KEY, summary);

                result.add(arguments);
            }
        } catch (SQLException e) {
            throw new ConnectorException("can't read result set from Album DB", e);
        }
        return result;
    }
}

