package by.fyodorov.musicstore.specification.track.custom;

import by.fyodorov.musicstore.connector.ConnectorException;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;

import static by.fyodorov.musicstore.specification.performer.PerformerRepositoryType.*;
import static by.fyodorov.musicstore.specification.track.TrackRepositoryType.*;

public class TrackAddCustomSpecification implements TrackCustomSelectSpecification {
    private static final String ADD_TRACK_SQL =
            String.format("INSERT INTO %s.%s (%s, %s, %s, %s, %s) " +
                            "VALUES (?, ?, ?, curdate(), " +
                            "SELECT %s FROM %s.%s WHERE %s =  ?));",
                    TRACK_BD_SCHEME, TRACK_BD_TABLE,
                    TRACK_NAME, TRACK_GENRE, TRACK_PRICE, TRACK_DATE, TRACK_PERFORMER_FK,
                    PERFORMER_ID,
                    PERFORMER_BD_SCHEME, PERFORMER_BD_TABLE,
                    PERFORMER_NAME);

    private String trackName;
    private String genre;
    private int price;
    private String performerName;

    public TrackAddCustomSpecification(String trackName, String genre, int price, String performerName) {
        this.trackName = trackName;
        this.genre = genre;
        this.price = price;
        this.performerName = performerName;
    }

    @Override
    public LinkedList<HashMap<String, String>> fromSet(ResultSet set) throws ConnectorException {
        return null;
    }

    @Override
    public String toSqlClauses() {
        return ADD_TRACK_SQL;
    }

    @Override
    public String[] getArguments() {
        String[] result = new String[4];
        result[0] = trackName;
        result[1] = genre;
        result[2] = Integer.toString(price);
        result[3] = performerName;
        return result;
    }
}
