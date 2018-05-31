package by.fyodorov.musicstore.specification.track.custom;

import by.fyodorov.musicstore.specification.track.TrackCustomSelectSpecification;

import static by.fyodorov.musicstore.specification.album.AlbumRepositoryType.*;
import static by.fyodorov.musicstore.specification.performer.PerformerRepositoryType.*;
import static by.fyodorov.musicstore.specification.track.TrackRepositoryType.*;
import static by.fyodorov.musicstore.specification.user.UserRepositoryType.*;

public class TrackInAlbumForUserCustomSelectSpecification extends TrackCustomSelectSpecification {
    private static final String SELECT_TRACK_INFO_FOR_USERNAME = String.format(
            "SELECT %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, CAST(%s.%s*(1 - (%s.%s / 100)) AS UNSIGNED) AS %s " +
                    "FROM %s JOIN %s ON %s.%s = %s.%s " +
                    "JOIN %s ON %s.%s = ? " +
                    "JOIN %s ON %s.%s = %s.%s " +
                    "JOIN %s ON %s.%s = %s.%s " +
                    "WHERE %s.%s = ? ;",

            TRACK_BD_TABLE, TRACK_NAME,
            TRACK_BD_TABLE, TRACK_GENRE,
            PERFORMER_BD_TABLE, PERFORMER_NAME,
            TRACK_BD_TABLE, TRACK_DATE,
            TRACK_BD_TABLE, TRACK_PRICE,
            TRACK_BD_TABLE, TRACK_PRICE,
            USER_BD_TABLE, USER_DISCOUNT,
            SUMMARY_COLUMN,
            ALBUM_BD_TABLE,
            ALBUM_HAS_TRACKS_BD_TABLE,
            ALBUM_BD_TABLE, ALBUM_ID,
            ALBUM_HAS_TRACKS_BD_TABLE, ALBUM_HAS_TRACKS_ALBUM_ID,
            USER_BD_TABLE,
            USER_BD_TABLE, USER_USERNAME,
            TRACK_BD_TABLE,
            ALBUM_HAS_TRACKS_BD_TABLE, ALBUM_HAS_TRACKS_TRACK_ID,
            TRACK_BD_TABLE, TRACK_ID,
            PERFORMER_BD_TABLE,
            PERFORMER_BD_TABLE, PERFORMER_ID,
            TRACK_BD_TABLE, TRACK_PERFORMER_FK,
            ALBUM_BD_TABLE, ALBUM_NAME);

    private String albumName;
    private String userName;

    public TrackInAlbumForUserCustomSelectSpecification(String albumName, String userName) {
        this.albumName = albumName;
        this.userName = userName;
    }

    @Override
    public String toSqlClauses() {
        return SELECT_TRACK_INFO_FOR_USERNAME;
    }

    @Override
    public String[] getArguments() {
        String[] result = new String[2];
        result[0] = userName;
        result[1] = albumName;
        return result;
    }
}
