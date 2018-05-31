package by.fyodorov.musicstore.specification.track.custom;

import by.fyodorov.musicstore.specification.track.TrackLimitSelectSpecification;

import static by.fyodorov.musicstore.specification.performer.PerformerRepositoryType.*;
import static by.fyodorov.musicstore.specification.track.TrackRepositoryType.*;
import static by.fyodorov.musicstore.specification.user.UserRepositoryType.*;

public class TrackLimitCustomSelectSpecification extends TrackLimitSelectSpecification {
    private static final String SELECT_TRACK_INFO_FOR_USERNAME = String.format(
            "SELECT %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, CAST(%s.%s*(1 - (%s.%s / 100)) AS UNSIGNED) AS %s " +
                    "FROM %s LEFT JOIN %s ON %s.%s = ? " +
                    "JOIN %s ON %s.%s = %s.%s " +
                    "LIMIT ?, ? ;",

            TRACK_BD_TABLE, TRACK_NAME,
            TRACK_BD_TABLE, TRACK_GENRE,
            PERFORMER_BD_TABLE, PERFORMER_NAME,
            TRACK_BD_TABLE, TRACK_DATE,
            TRACK_BD_TABLE, TRACK_PRICE,
            TRACK_BD_TABLE, TRACK_PRICE,
            USER_BD_TABLE, USER_DISCOUNT,
            SUMMARY_COLUMN,
            TRACK_BD_TABLE, USER_BD_TABLE,
            USER_BD_TABLE, USER_USERNAME,
            PERFORMER_BD_TABLE,
            PERFORMER_BD_TABLE, PERFORMER_ID,
            TRACK_BD_TABLE, TRACK_PERFORMER_FK);

    public static final String TRACK_NAME_KEY = "trackName";
    public static final String TRACK_GENRE_KEY = "trackGenre";
    public static final String PERFORMER_KEY = "performerName";
    public static final String TRACK_DATE_KEY = "trackDate";
    public static final String TRACK_PRICE_KEY = "trackPrice";
    public static final String TRACK_PRICE_SUMMARY_KEY = "trackSummaryPrice";

    private String name;
    private int skip;
    private int limit;

    public TrackLimitCustomSelectSpecification(String name, int skip, int limit) {
        this.name = name;
        this.skip = skip;
        this.limit = limit;
    }

    public String toSqlClauses() {
        return SELECT_TRACK_INFO_FOR_USERNAME;
    }

    @Override
    public String[] getArguments() {
        String[] result = new String[1];
        result[0] = name;
        return result;
    }

    @Override
    public Integer[] getLimits() {
        Integer[] result = new Integer[2];
        result[0] = skip;
        result[1] = limit;
        return result;
    }
}
