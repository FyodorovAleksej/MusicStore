package by.fyodorov.musicstore.specification.track;

import static by.fyodorov.musicstore.specification.track.TrackRepositoryConstant.*;

public class TrackByNameSpecification implements TrackRepositorySpecification {
    private static final String SELECT_BY_NAME =
            "SELECT " + TRACK_ID + ", "
                    + TRACK_NAME + ", "
                    + TRACK_GENRE + ", "
                    + TRACK_PRICE + ", "
                    + TRACK_DATE + ", "
                    + TRACK_PERFORMER_FK + " " +
                    "FROM "   + BD_SCHEME + "." + BD_TABLE + " " +
                    "WHERE "  + TRACK_NAME + " LIKE ?;";

    private String name;

    public TrackByNameSpecification(String name) {
        this.name = name;
    }

    @Override
    public String toSqlClauses() {
        return SELECT_BY_NAME;
    }

    @Override
    public String[] getArguments() {
        String[] arguments = new String[1];
        arguments[0] = name;
        return arguments;

    }
}
