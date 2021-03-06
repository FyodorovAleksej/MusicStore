package by.fyodorov.musicstore.specification.album.custom;

import by.fyodorov.musicstore.specification.album.AlbumCustomUpdateSpecification;

import static by.fyodorov.musicstore.specification.album.AlbumRepositoryType.*;
import static by.fyodorov.musicstore.specification.track.TrackRepositoryType.*;

public class AlbumInsertTrackCustomUpdateSpecification implements AlbumCustomUpdateSpecification {
    private static final String ADD_TRACK_SQL =
            String.format("INSERT INTO %s.%s (%s, %s, %s, %s) " +
                            "SELECT %s.%s, %s.%s, %s.%s, %s.%s FROM %s, %s " +
                            "WHERE %s.%s = ? " +
                            "AND %s.%s = ? ;",
                    ALBUM_HAS_TRACKS_BD_SCHEME, ALBUM_HAS_TRACKS_BD_TABLE,
                    ALBUM_HAS_TRACKS_ALBUM_ID,
                    ALBUM_HAS_TRACKS_ALBUM_PERFORMER_ID,
                    ALBUM_HAS_TRACKS_TRACK_ID,
                    ALBUM_HAS_TRACKS_TRACK_PERFORMER_ID,
                    ALBUM_BD_TABLE, ALBUM_ID,
                    ALBUM_BD_TABLE, ALBUM_PERFORMER_ID,
                    TRACK_BD_TABLE, TRACK_ID,
                    TRACK_BD_TABLE, TRACK_PERFORMER_FK,
                    ALBUM_BD_TABLE, TRACK_BD_TABLE,
                    ALBUM_BD_TABLE, ALBUM_NAME,
                    TRACK_BD_TABLE, TRACK_NAME);

    private String albumName;
    private String trackName;

    public AlbumInsertTrackCustomUpdateSpecification(String albumName, String trackName) {
        this.albumName = albumName;
        this.trackName = trackName;
    }

    @Override
    public String toSqlClauses() {
        return ADD_TRACK_SQL;
    }

    @Override
    public String[] getArguments() {
        String[] result = new String[2];
        result[0] = albumName;
        result[1] = trackName;
        return result;
    }
}
