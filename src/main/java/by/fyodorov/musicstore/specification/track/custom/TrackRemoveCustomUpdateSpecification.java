package by.fyodorov.musicstore.specification.track.custom;

import by.fyodorov.musicstore.specification.track.TrackCustomUpdateSpecification;

import static by.fyodorov.musicstore.specification.track.TrackRepositoryType.*;

public class TrackRemoveCustomUpdateSpecification implements TrackCustomUpdateSpecification {
    private static final String DELETE_SQL =
            String.format("DELETE FROM %s.%s " +
                            "WHERE %s = ? ;",
                    TRACK_BD_SCHEME, TRACK_BD_TABLE,
                    TRACK_NAME);

    private String trackName;

    public TrackRemoveCustomUpdateSpecification(String trackName) {
        this.trackName = trackName;
    }

    @Override
    public String toSqlClauses() {
        return DELETE_SQL;
    }

    @Override
    public String[] getArguments() {
        String[] res = new String[1];
        res[0] = trackName;
        return res;
    }
}
