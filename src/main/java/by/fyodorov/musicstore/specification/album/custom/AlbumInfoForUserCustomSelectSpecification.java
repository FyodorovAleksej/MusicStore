package by.fyodorov.musicstore.specification.album.custom;

import by.fyodorov.musicstore.specification.album.AlbumCustomSelectSpecification;

import static by.fyodorov.musicstore.specification.album.AlbumRepositoryType.*;
import static by.fyodorov.musicstore.specification.performer.PerformerRepositoryType.*;
import static by.fyodorov.musicstore.specification.user.UserRepositoryType.*;


public class AlbumInfoForUserCustomSelectSpecification implements AlbumCustomSelectSpecification {
    private static final String SELECT_ALBUM_INFO_FOR_USERNAME = String.format(
            "SELECT %s.%s, %s.%s, %s.%s, %s.%s, %s.%s, " +
                    "CAST(%s.%s*(1 - (%s.%s / 100)) AS UNSIGNED) AS %s " +
                    "FROM %s LEFT JOIN %s ON %s.%s = ? " +
                    "JOIN %s ON %s.%s = %s.%s " +
                    "WHERE %s.%s = ? ;",

            ALBUM_BD_TABLE, ALBUM_NAME,
            ALBUM_BD_TABLE, ALBUM_GENRE,
            PERFORMER_BD_TABLE, PERFORMER_NAME,
            ALBUM_BD_TABLE, ALBUM_DATE,
            ALBUM_BD_TABLE, ALBUM_PRICE,
            ALBUM_BD_TABLE, ALBUM_PRICE,
            USER_BD_TABLE, USER_DISCOUNT,
            SUMMARY_COLUMN,
            ALBUM_BD_TABLE, USER_BD_TABLE,
            USER_BD_TABLE, USER_USERNAME,
            PERFORMER_BD_TABLE,
            PERFORMER_BD_TABLE, PERFORMER_ID,
            ALBUM_BD_TABLE, ALBUM_PERFORMER_ID,
            ALBUM_BD_TABLE, ALBUM_NAME);

    private String albumName;
    private String userName;

    public AlbumInfoForUserCustomSelectSpecification(String albumName, String userName) {
        this.userName = userName;
        this.albumName = albumName;
    }

    @Override
    public String toSqlClauses() {
        return SELECT_ALBUM_INFO_FOR_USERNAME;
    }

    @Override
    public String[] getArguments() {
        String[] result = new String[2];
        result[0] = userName;
        result[1] = albumName;
        return result;
    }
}
