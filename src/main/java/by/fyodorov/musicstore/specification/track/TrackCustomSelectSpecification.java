package by.fyodorov.musicstore.specification.track;

import by.fyodorov.musicstore.connector.ConnectorException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

import static by.fyodorov.musicstore.specification.performer.PerformerRepositoryType.PERFORMER_NAME;
import static by.fyodorov.musicstore.specification.track.TrackRepositoryType.*;

public abstract class TrackCustomSelectSpecification implements TrackRepositorySpecification {
    public static final String TRACK_NAME_KEY = "trackName";
    public static final String TRACK_GENRE_KEY = "trackGenre";
    public static final String PERFORMER_KEY = "performerName";
    public static final String TRACK_DATE_KEY = "trackDate";
    public static final String TRACK_PRICE_KEY = "trackPrice";
    public static final String TRACK_PRICE_SUMMARY_KEY = "trackSummaryPrice";
    public static final String SUMMARY_COLUMN = "summary";

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
        } catch (SQLException e) {
            throw new ConnectorException("can't read result set from Track DB", e);
        }
        return result;
    }
}
