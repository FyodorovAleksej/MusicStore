package by.fyodorov.musicstore.specification.track.custom;

import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.specification.track.TrackCustomSelectSpecification;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

import static by.fyodorov.musicstore.specification.performer.PerformerRepositoryType.*;
import static by.fyodorov.musicstore.specification.track.TrackRepositoryType.*;

public class TrackWithoutPriceCustomSelectSpecification extends TrackCustomSelectSpecification {
    private static final String SELECT_TRACK_INFO = String.format(
            "SELECT %s.%s, %s.%s, %s.%s, %s.%s " +
                    "FROM %s " +
                    "JOIN %s ON %s.%s = %s.%s ;",
            TRACK_BD_TABLE, TRACK_NAME,
            TRACK_BD_TABLE, TRACK_GENRE,
            PERFORMER_BD_TABLE, PERFORMER_NAME,
            TRACK_BD_TABLE, TRACK_DATE,
            TRACK_BD_TABLE,
            PERFORMER_BD_TABLE,
            PERFORMER_BD_TABLE, PERFORMER_ID,
            TRACK_BD_TABLE, TRACK_PERFORMER_FK);

    @Override
    public String toSqlClauses() {
        return SELECT_TRACK_INFO;
    }

    @Override
    public String[] getArguments() {
        return new String[0];
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

                arguments.put(TRACK_NAME_KEY, trackName);
                arguments.put(TRACK_GENRE_KEY, genre);
                arguments.put(PERFORMER_KEY, performer);
                arguments.put(TRACK_DATE_KEY, date);

                result.add(arguments);
            }
        } catch (SQLException e) {
            throw new ConnectorException("can't read result set from Track DB", e);
        }
        return result;
    }
}
