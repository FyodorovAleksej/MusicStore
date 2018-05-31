package by.fyodorov.musicstore.specification.user.custom;

import by.fyodorov.musicstore.specification.user.UserCustomUpdateSpecification;

import static by.fyodorov.musicstore.specification.album.AlbumRepositoryType.*;
import static by.fyodorov.musicstore.specification.user.UserRepositoryType.*;

public class UserInsertTracksFromAlbumCustomUpdateSpecification implements UserCustomUpdateSpecification {
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
            ALBUM_HAS_TRACKS_BD_TABLE, ALBUM_HAS_TRACKS_TRACK_ID,
            ALBUM_HAS_TRACKS_BD_TABLE, ALBUM_HAS_TRACKS_TRACK_PERFORMER_ID,
            USER_BD_TABLE,
            ALBUM_BD_TABLE,
            ALBUM_HAS_TRACKS_BD_TABLE,
            ALBUM_BD_TABLE, ALBUM_ID,
            ALBUM_HAS_TRACKS_BD_TABLE, ALBUM_HAS_TRACKS_ALBUM_ID,
            USER_BD_TABLE, USER_USERNAME,
            ALBUM_BD_TABLE, ALBUM_NAME,
            ALBUM_HAS_TRACKS_BD_TABLE, ALBUM_HAS_TRACKS_TRACK_ID,
            USER_HAS_TRACKS_BD_TABLE, USER_HAS_TRACKS_TRACK_ID,
            USER_HAS_TRACKS_BD_TABLE,
            USER_HAS_TRACKS_BD_TABLE, USER_HAS_TRACKS_USER_ID,
            USER_BD_TABLE, USER_ID
    );

    private String userName;
    private String albumName;

    public UserInsertTracksFromAlbumCustomUpdateSpecification(String userName, String albumName) {
        this.userName = userName;
        this.albumName = albumName;
    }

    @Override
    public String toSqlClauses() {
        return INSERT_TRACK;
    }

    @Override
    public String[] getArguments() {
        String[] result = new String[2];
        result[0] = userName;
        result[1] = albumName;
        return result;
    }
}
