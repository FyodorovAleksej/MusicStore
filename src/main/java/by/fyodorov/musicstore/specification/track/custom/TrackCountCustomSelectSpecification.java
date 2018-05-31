package by.fyodorov.musicstore.specification.track.custom;

import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.specification.track.TrackCustomSelectSpecification;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

import static by.fyodorov.musicstore.specification.track.TrackRepositoryType.TRACK_BD_SCHEME;
import static by.fyodorov.musicstore.specification.track.TrackRepositoryType.TRACK_BD_TABLE;

public class TrackCountCustomSelectSpecification extends TrackCustomSelectSpecification {
    private static final String COUNT_TEMP = "count";

    private static final String SELECT_COUNT = String.format(
            "SELECT COUNT(*) AS %s FROM %s.%s ;",
            COUNT_TEMP,
            TRACK_BD_SCHEME, TRACK_BD_TABLE);

    public static final String TRACK_COUNT_KEY = "trackCount";

    @Override
    public String toSqlClauses() {
        return SELECT_COUNT;
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
                Integer count = set.getInt(COUNT_TEMP);
                arguments.put(TRACK_COUNT_KEY, count.toString());
                result.add(arguments);
            }
        } catch (SQLException e) {
            throw new ConnectorException("can't read result set from Track DB", e);
        }
        return result;
    }
}
