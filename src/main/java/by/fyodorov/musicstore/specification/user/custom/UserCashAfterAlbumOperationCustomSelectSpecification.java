package by.fyodorov.musicstore.specification.user.custom;

import by.fyodorov.musicstore.specification.user.UserCustomSelectSpecification;

import static by.fyodorov.musicstore.model.UserBonusType.USER_BONUS_ALBUM;
import static by.fyodorov.musicstore.specification.album.AlbumRepositoryType.*;
import static by.fyodorov.musicstore.specification.user.UserRepositoryType.*;

public class UserCashAfterAlbumOperationCustomSelectSpecification extends UserCustomSelectSpecification {
    private static final String USER_CASH_AFTER_SELECT = String.format(
            "SELECT (%s.%s - IF(%s.%s & %s, 0, (1 - %s.%s) * " +
                    "(SELECT %s.%s FROM %s WHERE %s.%s = ?))) AS %s " +
                    "FROM %s WHERE %s.%s = ? ;\n",

            USER_BD_TABLE, USER_CASH,
            USER_BD_TABLE, USER_BONUS,
            USER_BONUS_ALBUM,
            USER_BD_TABLE, USER_DISCOUNT,
            ALBUM_BD_TABLE, ALBUM_PRICE,
            ALBUM_BD_TABLE,
            ALBUM_BD_TABLE, ALBUM_NAME,
            SUMMARY_TABLE,
            USER_BD_TABLE,
            USER_BD_TABLE, USER_USERNAME);

    private String userName;
    private String albumName;

    public UserCashAfterAlbumOperationCustomSelectSpecification(String userName, String albumName) {
        this.userName = userName;
        this.albumName = albumName;
    }

    @Override
    public String toSqlClauses() {
        return USER_CASH_AFTER_SELECT;
    }

    @Override
    public String[] getArguments() {
        String[] result = new String[2];
        result[0] = albumName;
        result[1] = userName;
        return result;
    }
}

