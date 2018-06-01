package by.fyodorov.musicstore.specification.album;

import by.fyodorov.musicstore.connector.ConnectorException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

import static by.fyodorov.musicstore.specification.album.AlbumRepositoryType.*;
import static by.fyodorov.musicstore.specification.performer.PerformerRepositoryType.PERFORMER_NAME;

public interface AlbumCustomSelectSpecification extends AlbumRepositorySpecification {
    String ALBUM_NAME_KEY = "trackName";
    String ALBUM_GENRE_KEY = "trackGenre";
    String ALBUM_PERFORMER_KEY = "performerName";
    String ALBUM_DATE_KEY = "trackDate";
    String ALBUM_PRICE_KEY = "trackPrice";
    String ALBUM_PRICE_SUMMARY_KEY = "trackSummaryPrice";
    String SUMMARY_COLUMN = "summary";

    default LinkedList<HashMap<String, String>> fromSet(ResultSet set) throws ConnectorException {
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
