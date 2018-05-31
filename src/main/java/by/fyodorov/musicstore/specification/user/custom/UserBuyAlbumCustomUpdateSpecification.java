package by.fyodorov.musicstore.specification.user.custom;

import by.fyodorov.musicstore.specification.user.UserCustomUpdateSpecification;

import static by.fyodorov.musicstore.specification.album.AlbumRepositoryType.*;
import static by.fyodorov.musicstore.specification.user.UserRepositoryType.*;

public class UserBuyAlbumCustomUpdateSpecification implements UserCustomUpdateSpecification {
    private static final String BUY_TRANSACTION = String.format(
            "INSERT INTO %s (%s.%s, %s.%s, %s.%s) " +
                    "SELECT %s.%s, %s.%s, %s.%s " +
                    "FROM %s, %s WHERE %s.%s = ? " +
                    "AND %s.%s = ? ;",

            USER_HAS_ALBUMS_BD_TABLE,
            USER_HAS_ALBUMS_BD_TABLE, USER_HAS_ALBUMS_USER_ID,
            USER_HAS_ALBUMS_BD_TABLE, USER_HAS_ALBUMS_ALBUM_ID,
            USER_HAS_ALBUMS_BD_TABLE, USER_HAS_ALBUMS_PERFORMER_ID,
            USER_BD_TABLE, USER_ID,
            ALBUM_BD_TABLE, ALBUM_ID,
            ALBUM_BD_TABLE, ALBUM_PERFORMER_ID,
            USER_BD_TABLE,
            ALBUM_BD_TABLE,
            USER_BD_TABLE, USER_USERNAME,
            ALBUM_BD_TABLE, ALBUM_NAME);

    private String userName;
    private String albumName;

    public UserBuyAlbumCustomUpdateSpecification(String userName, String albumName) {
        this.userName = userName;
        this.albumName = albumName;
    }

    @Override
    public String toSqlClauses() {
        return BUY_TRANSACTION;
    }

    @Override
    public String[] getArguments() {
        String[] result = new String[2];
        result[0] = userName;
        result[1] = albumName;
        return result;
    }
}
