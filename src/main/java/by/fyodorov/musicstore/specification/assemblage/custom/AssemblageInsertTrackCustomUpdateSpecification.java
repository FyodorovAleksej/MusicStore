package by.fyodorov.musicstore.specification.assemblage.custom;

import by.fyodorov.musicstore.specification.assemblage.AssemblageCustomUpdateSpecification;

import static by.fyodorov.musicstore.specification.assemblage.AssemblageRepositoryType.*;
import static by.fyodorov.musicstore.specification.track.TrackRepositoryType.*;

public class AssemblageInsertTrackCustomUpdateSpecification implements AssemblageCustomUpdateSpecification {
    private static final String ADD_TRACK_SQL =
            String.format("INSERT INTO %s.%s (%s, %s, %s, %s) " +
                            "SELECT %s.%s, %s.%s, %s.%s, %s.%s FROM %s, %s " +
                            "WHERE %s.%s = ? " +
                            "AND %s.%s = ? ;",
                    ASSEMBLAGE_HAS_TRACKS_BD_SCHEME, ASSEMBLAGE_HAS_TRACKS_BD_TABLE,
                    ASSEMBLAGE_HAS_TRACKS_ASSEMBLAGE_ID,
                    ASSEMBLAGE_HAS_TRACKS_ASSEMBLAGE_OWNER_ID,
                    ASSEMBLAGE_HAS_TRACKS_TRACK_ID,
                    ASSEMBLAGE_HAS_TRACKS_TRACK_PERFORMER_ID,
                    ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_ID,
                    ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_OWNER_ID,
                    TRACK_BD_TABLE, TRACK_ID,
                    TRACK_BD_TABLE, TRACK_PERFORMER_FK,
                    ASSEMBLAGE_BD_TABLE, TRACK_BD_TABLE,
                    ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_NAME,
                    TRACK_BD_TABLE, TRACK_NAME);

    private String assemblageName;
    private String trackName;

    public AssemblageInsertTrackCustomUpdateSpecification(String assemblageName, String trackName) {
        this.assemblageName = assemblageName;
        this.trackName = trackName;
    }

    @Override
    public String toSqlClauses() {
        return ADD_TRACK_SQL;
    }

    @Override
    public String[] getArguments() {
        String[] result = new String[2];
        result[0] = assemblageName;
        result[1] = trackName;
        return result;
    }
}
