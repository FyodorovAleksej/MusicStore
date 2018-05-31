package by.fyodorov.musicstore.specification.user.custom;

import by.fyodorov.musicstore.specification.user.UserCustomUpdateSpecification;

import static by.fyodorov.musicstore.specification.assemblage.AssemblageRepositoryType.*;
import static by.fyodorov.musicstore.specification.user.UserRepositoryType.*;

public class UserInsertTracksFromAssemblageCustomUpdateSpecification implements UserCustomUpdateSpecification {
    private static final String INSERT_TRACK = String.format(
            "INSERT INTO %s (%s.%s, %s.%s, %s.%s) " +
                    "SELECT %s.%s, %s.%s, %s.%s " +
                    "FROM %s, %s " +
                    "JOIN %s ON %s.%s = %s.%s " +
                    "WHERE %s.%s = ? " +
                    "AND %s.%s = ? " +
                    "AND %s.%s NOT IN " +
                    "( SELECT %s.%s " +
                    "FROM %s " +
                    "WHERE %s.%s = %s.%s);",

            USER_HAS_TRACKS_BD_TABLE,
            USER_HAS_TRACKS_BD_TABLE, USER_HAS_TRACKS_USER_ID,
            USER_HAS_TRACKS_BD_TABLE, USER_HAS_TRACKS_TRACK_ID,
            USER_HAS_TRACKS_BD_TABLE, USER_HAS_TRACKS_PERFORMER_ID,
            USER_BD_TABLE, USER_ID,
            ASSEMBLAGE_HAS_TRACKS_BD_TABLE, ASSEMBLAGE_HAS_TRACKS_TRACK_ID,
            ASSEMBLAGE_HAS_TRACKS_BD_TABLE, ASSEMBLAGE_HAS_TRACKS_TRACK_PERFORMER_ID,
            USER_BD_TABLE,
            ASSEMBLAGE_BD_TABLE,
            ASSEMBLAGE_HAS_TRACKS_BD_TABLE,
            ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_ID,
            ASSEMBLAGE_HAS_TRACKS_BD_TABLE, ASSEMBLAGE_HAS_TRACKS_ASSEMBLAGE_ID,
            USER_BD_TABLE, USER_USERNAME,
            ASSEMBLAGE_BD_TABLE, ASSEMBLAGE_NAME,
            ASSEMBLAGE_HAS_TRACKS_BD_TABLE, ASSEMBLAGE_HAS_TRACKS_TRACK_ID,
            USER_HAS_TRACKS_BD_TABLE, USER_HAS_TRACKS_TRACK_ID,
            USER_HAS_TRACKS_BD_TABLE,
            USER_HAS_TRACKS_BD_TABLE, USER_HAS_TRACKS_USER_ID,
            USER_BD_TABLE, USER_ID
    );

    private String userName;
    private String assemblageName;

    public UserInsertTracksFromAssemblageCustomUpdateSpecification(String userName, String assemblageName) {
        this.userName = userName;
        this.assemblageName = assemblageName;
    }

    @Override
    public String toSqlClauses() {
        return INSERT_TRACK;
    }

    @Override
    public String[] getArguments() {
        String[] result = new String[2];
        result[0] = userName;
        result[1] = assemblageName;
        return result;
    }
}
